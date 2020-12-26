package org.jnyou.gmall.storageservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.constant.WareConstant;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.gmall.storageservice.dao.PurchaseDao;
import org.jnyou.gmall.storageservice.entity.PurchaseDetailEntity;
import org.jnyou.gmall.storageservice.entity.PurchaseEntity;
import org.jnyou.gmall.storageservice.service.PurchaseDetailService;
import org.jnyou.gmall.storageservice.service.PurchaseService;
import org.jnyou.gmall.storageservice.service.WareSkuService;
import org.jnyou.gmall.storageservice.vo.MergeVo;
import org.jnyou.gmall.storageservice.vo.PurchaseDoneVo;
import org.jnyou.gmall.storageservice.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1)
        );

        return new PageUtils(page);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        // 采购单为空的时候系统自动新建一个
        if (null == purchaseId) {
            PurchaseEntity purchaseEntity = new PurchaseEntity().setCreateTime(new Date()).setUpdateTime(new Date())
                    .setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            this.save(purchaseEntity);
            // 保存后获取到这个新建的采购单ID
            purchaseId = purchaseEntity.getId();
        }
        PurchaseEntity purchaseEntity = this.getById(purchaseId);
        if (purchaseEntity.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() || purchaseEntity.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
            List<Long> items = mergeVo.getItems();
            // 修改采购单的采购单ID和状态
            Long finalPurchaseId = purchaseId;

            List<PurchaseDetailEntity> collect = items.stream().map(item -> {
                return new PurchaseDetailEntity().setPurchaseId(finalPurchaseId).setId(item).setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            }).collect(Collectors.toList());

            purchaseDetailService.updateBatchById(collect);

            // 更新采购单的时间
            this.updateById(new PurchaseEntity().setUpdateTime(new Date()).setId(purchaseId));
        }
    }

    @Override
    public void received(List<Long> purchaseIds) {
        // 确定当前采购单是新建或者已分配状态
        List<PurchaseEntity> collect = purchaseIds.stream().map(item -> {
            return this.getById(item);
        }).filter(f -> {
            if (f.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() || f.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                return true;
            }
            return false;
        }).map(item -> {
            return new PurchaseEntity().setUpdateTime(new Date()).setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode()).setId(item.getId());
        }).collect(Collectors.toList());
        // 改变采购单的状态为已领取
        this.updateBatchById(collect);

        // 改变采购项的状态
        collect.forEach(item -> {
            List<PurchaseDetailEntity> detailEntities = purchaseDetailService.list(new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", item.getId()));
            List<PurchaseDetailEntity> purchaseDetailEntities = detailEntities.stream().map(detail -> {
                return new PurchaseDetailEntity().setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode()).setId(detail.getId());
            }).collect(Collectors.toList());
            purchaseDetailService.updatePurchaseDetails(purchaseDetailEntities);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void done(PurchaseDoneVo doneVo) {
        // 采编采购项状态
        boolean flag = true;
        List<PurchaseDetailEntity> purchaseDetailEntities = new ArrayList<>();
        for (PurchaseItemDoneVo item : doneVo.getItems()) {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity().setId(item.getItemId());
            if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()){
                flag = false;
                purchaseDetailEntity.setStatus(item.getStatus());
            } else {
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                // 将成功采购的进行入库存储  采购商品的ID，仓库ID，库存数
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(entity.getSkuId(),entity.getWareId(),entity.getSkuNum());
            }
            purchaseDetailEntities.add(purchaseDetailEntity);
        }
        purchaseDetailService.updateBatchById(purchaseDetailEntities);

        // 根据采购项采购完成的状态改变采购单的状态
        PurchaseEntity purchaseEntity = new PurchaseEntity().setUpdateTime(new Date()).setId(doneVo.getId());
        if(flag){
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.FINISH.getCode());
        } else purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        this.updateById(purchaseEntity);

    }

}