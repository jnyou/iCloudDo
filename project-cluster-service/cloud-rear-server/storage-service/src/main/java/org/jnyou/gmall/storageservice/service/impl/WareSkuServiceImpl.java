package org.jnyou.gmall.storageservice.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.jnyou.common.exception.NoStockException;
import org.jnyou.common.to.SkuHasStockVo;
import org.jnyou.common.to.mq.OrderTo;
import org.jnyou.common.to.mq.StockDetailTo;
import org.jnyou.common.to.mq.StockLockedTo;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.storageservice.dao.WareSkuDao;
import org.jnyou.gmall.storageservice.entity.WareOrderTaskDetailEntity;
import org.jnyou.gmall.storageservice.entity.WareOrderTaskEntity;
import org.jnyou.gmall.storageservice.entity.WareSkuEntity;
import org.jnyou.gmall.storageservice.feign.OrderFeignClient;
import org.jnyou.gmall.storageservice.feign.ProductFeignClient;
import org.jnyou.gmall.storageservice.service.WareOrderTaskDetailService;
import org.jnyou.gmall.storageservice.service.WareOrderTaskService;
import org.jnyou.gmall.storageservice.service.WareSkuService;
import org.jnyou.gmall.storageservice.vo.OrderItemVo;
import org.jnyou.gmall.storageservice.vo.WareSkuLockVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;
    @Autowired
    ProductFeignClient productFeignClient;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    WareOrderTaskDetailService wareOrderTaskDetailService;
    @Autowired
    WareOrderTaskService wareOrderTaskService;
    @Autowired
    OrderFeignClient orderFeignClient;

    private void unLockStock(Long skuId, Long wareId, Integer count, Long taskDetailId) {
        // 库存解锁
        this.baseMapper.unLockStock(skuId, wareId, count);
        // 更新工作单的状态
        WareOrderTaskDetailEntity orderTaskDetailEntity = new WareOrderTaskDetailEntity();
        orderTaskDetailEntity.setId(taskDetailId);
        // 已解锁
        orderTaskDetailEntity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(orderTaskDetailEntity);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        LambdaQueryWrapper<WareSkuEntity> lambdaQueryWrapper = Wrappers.<WareSkuEntity>lambdaQuery().eq(!StringUtils.isEmpty(params.get("skuId")), WareSkuEntity::getSkuId, params.get("skuId"))
                .eq(!StringUtils.isEmpty(params.get("wareId")), WareSkuEntity::getWareId, params.get("wareId"));

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                lambdaQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        // 查询是否有库存记录
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (CollectionUtils.isEmpty(wareSkuEntities)) {
            // 为空则添加库存
            WareSkuEntity wareSkuEntity = new WareSkuEntity().setWareId(wareId).setStock(skuNum).setSkuId(skuId).setStockLocked(0);
            // 远程获取商品名称
            try {
                R info = productFeignClient.info(skuId);
                if (info.getCode() == 0) {
                    Map<String, Object> skuInfo = (Map<String, Object>) info.get("skuInfo");
                    wareSkuEntity.setSkuName((String) skuInfo.get("skuName"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            wareSkuDao.insert(wareSkuEntity);
        } else {
            // 更新库存数信息
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }
    }

    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(skuId -> {
            Long count = this.baseMapper.getSkuStock(skuId);
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo().setSkuId(skuId).setHasStock(count == null ? false : count > 0);
            return skuHasStockVo;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 为某个订单锁库存
     * 1、解库存场景：下订单成功，订单过期没有支付被系统自动取消、被用户手动取消
     * 2、下订单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚，之前锁定的库存就要自动解锁
     *
     * @param vo
     * @Author JnYou
     */
    @Override
    @Transactional(rollbackFor = NoStockException.class)
    public Boolean orderLockStock(WareSkuLockVo vo) {
        // 保存库存工作单
        WareOrderTaskEntity taskEntity = new WareOrderTaskEntity();
        taskEntity.setOrderSn(vo.getOrderSn());
        wareOrderTaskService.save(taskEntity);

        // 找到每个商品在哪个仓库都有库存
        List<OrderItemVo> locks = vo.getLocks();
        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            SkuWareHasStock wareHasStock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            wareHasStock.setSkuId(skuId);
            wareHasStock.setCount(item.getCount());
            List<Long> wareId = this.baseMapper.listWareIdHasStock(skuId);
            wareHasStock.setWareId(wareId);
            return wareHasStock;
        }).collect(Collectors.toList());

        // 是否全部锁定成功
        Boolean allLocks = true;
        // 锁定库存
        for (SkuWareHasStock wareHasStock : collect) {
            Boolean skuLock = false;
            List<Long> wareIds = wareHasStock.getWareId();
            Long skuId = wareHasStock.getSkuId();
            if (CollectionUtils.isEmpty(wareIds)) {
                // 这个商品在任何仓库中都没有库存，直接快速失败
                throw new NoStockException(skuId);
            }
            for (Long wareId : wareIds) {
                // 锁定成功返回1，锁定失败返回0
                Long count = this.baseMapper.lockSkuStock(skuId, wareId, wareHasStock.getCount());
                if (count == 1) {
                    skuLock = true;
                    // 保存库存工作单详情
                    WareOrderTaskDetailEntity orderTaskDetailEntity = new WareOrderTaskDetailEntity();
                    orderTaskDetailEntity.setSkuId(skuId).setSkuName("").setSkuNum(wareHasStock.getCount()).setWareId(wareId).setTaskId(taskEntity.getId()).setLockStatus(1);
                    wareOrderTaskDetailService.save(orderTaskDetailEntity);
                    // 锁定成功给MQ发送消息库存锁定成功，让MQ处理后续的解库存逻辑
                    StockLockedTo stockLockedTo = new StockLockedTo();
                    stockLockedTo.setId(taskEntity.getId());
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(orderTaskDetailEntity, stockDetailTo);
                    stockLockedTo.setDetailTo(stockDetailTo);
                    rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", stockLockedTo);
                    break;
                } else {
                    // 当前仓库锁失败，重试下一个厂库
                }
            }
            if (skuLock == false) {
                // 当前商品所有仓库都没有锁住
                throw new NoStockException(skuId);
            }
        }
        // 全部成功
        return true;
    }

    @Override
    public void unLockStock(StockLockedTo stock) {
        // 库存解锁
        StockDetailTo detailTo = stock.getDetailTo();
        // 锁库存详情id
        Long detailId = detailTo.getId();
        // 先查询一次看看库存单详情是否存在
        WareOrderTaskDetailEntity detailEntity = wareOrderTaskDetailService.getById(detailId);
        if (Objects.nonNull(detailEntity)) {
            // 需解锁
            WareOrderTaskEntity taskEntity = wareOrderTaskService.getById(stock.getId());
            // 查询订单状态
            R r = orderFeignClient.getOrderStatus(taskEntity.getOrderSn());
            if (r.getCode() == 0) {
                OrderTo data = r.getData(new TypeReference<OrderTo>() {
                });
                if (Objects.isNull(data) || data.getStatus() == 4) {
                    // 订单不存在 || 订单被取消了，需要解库存
                    if (detailEntity.getLockStatus() == 1) {
                        // 当前工作单详情状态为1已锁定但未解锁
                        unLockStock(detailTo.getSkuId(), detailTo.getWareId(), detailTo.getSkuNum(), detailId);
                    }
                }
            } else {
                throw new RuntimeException("远程服务失败");
            }
        } else {
            // 无需解锁
        }
    }

    /**
     * 防止订单服务卡顿等等原因，导致订单状态消息一直改变不了，库存消息优先到期。查订单状态一直为新建状态。
     *
     * @param orderTo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unLockStock(OrderTo orderTo) {
        String orderSn = orderTo.getOrderSn();
        // 查询库存的最新状态，防止重复解锁库存
        WareOrderTaskEntity entity = wareOrderTaskService.getOrderTaskByOrderSn(orderSn);
        if (Objects.nonNull(entity)) {
            List<WareOrderTaskDetailEntity> list = wareOrderTaskDetailService.list(Wrappers.<WareOrderTaskDetailEntity>lambdaQuery().eq(WareOrderTaskDetailEntity::getTaskId, entity.getId()).eq(WareOrderTaskDetailEntity::getLockStatus, 1));
            for (WareOrderTaskDetailEntity detailEntity : list) {
                // 解锁库存
                this.unLockStock(detailEntity.getSkuId(), detailEntity.getWareId(), detailEntity.getSkuNum(), detailEntity.getTaskId());
            }
        }
    }

}

@Data
class SkuWareHasStock {
    private Long skuId;
    private Integer count;
    private List<Long> wareId;
}