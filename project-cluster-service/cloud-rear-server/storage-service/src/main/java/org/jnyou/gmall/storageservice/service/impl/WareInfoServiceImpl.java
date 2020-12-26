package org.jnyou.gmall.storageservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.gmall.storageservice.dao.WareInfoDao;
import org.jnyou.gmall.storageservice.entity.WareInfoEntity;
import org.jnyou.gmall.storageservice.service.WareInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<WareInfoEntity> lambdaQueryWrapper = Wrappers.<WareInfoEntity>lambdaQuery();

        if (!StringUtils.isEmpty(params.get("key"))) {
            lambdaQueryWrapper.eq(WareInfoEntity::getId, params.get("key"))
                    .or()
                    .like(WareInfoEntity::getName, params.get("key"))
                    .or()
                    .like(WareInfoEntity::getAddress, params.get("key"))
                    .or()
                    .like(WareInfoEntity::getAreacode, params.get("key"));
        }

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                lambdaQueryWrapper
        );

        return new PageUtils(page);
    }

}