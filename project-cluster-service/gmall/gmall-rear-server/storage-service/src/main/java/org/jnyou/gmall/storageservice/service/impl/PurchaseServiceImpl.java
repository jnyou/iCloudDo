package org.jnyou.gmall.storageservice.service.impl;

import org.jnyou.common.constant.WareConstant;
import org.jnyou.gmall.storageservice.entity.PurchaseDetailEntity;
import org.jnyou.gmall.storageservice.service.PurchaseDetailService;
import org.jnyou.gmall.storageservice.vo.MergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.storageservice.dao.PurchaseDao;
import org.jnyou.gmall.storageservice.entity.PurchaseEntity;
import org.jnyou.gmall.storageservice.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

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
                new QueryWrapper<PurchaseEntity>().eq("status",0).or().eq("status",1)
        );

        return new PageUtils(page);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        // 采购单为空的时候系统自动新建一个
        if(null == purchaseId){
            PurchaseEntity purchaseEntity = new PurchaseEntity().setCreateTime(new Date()).setUpdateTime(new Date())
                    .setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            this.save(purchaseEntity);
            // 保存后获取到这个新建的采购单ID
            purchaseId = purchaseEntity.getId();
        }
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
            return new PurchaseEntity().setUpdateTime(new Date()).setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
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

}