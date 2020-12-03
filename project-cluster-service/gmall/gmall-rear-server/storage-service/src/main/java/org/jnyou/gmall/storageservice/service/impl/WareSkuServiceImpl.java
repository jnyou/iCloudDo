package org.jnyou.gmall.storageservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.storageservice.dao.WareSkuDao;
import org.jnyou.gmall.storageservice.entity.WareSkuEntity;
import org.jnyou.gmall.storageservice.feign.ProductFeignClient;
import org.jnyou.gmall.storageservice.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignClient productFeignClient;

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

}