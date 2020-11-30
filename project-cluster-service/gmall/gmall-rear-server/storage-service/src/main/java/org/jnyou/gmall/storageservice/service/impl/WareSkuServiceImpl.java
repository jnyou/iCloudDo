package org.jnyou.gmall.storageservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jnyou.gmall.storageservice.entity.WareInfoEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.storageservice.dao.WareSkuDao;
import org.jnyou.gmall.storageservice.entity.WareSkuEntity;
import org.jnyou.gmall.storageservice.service.WareSkuService;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

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

}