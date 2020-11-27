package org.jnyou.gmall.productservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.productservice.dao.AttrGroupDao;
import org.jnyou.gmall.productservice.entity.AttrGroupEntity;
import org.jnyou.gmall.productservice.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params,Long catId) {
        LambdaQueryWrapper<AttrGroupEntity> wrapper = Wrappers.<AttrGroupEntity>lambdaQuery()
                .and(!StringUtils.isEmpty(params.get("key")), (obj) -> {
                    obj.eq(AttrGroupEntity::getAttrGroupId, params.get("key")).or().like(AttrGroupEntity::getAttrGroupName, params.get("key"));
                });
        IPage<AttrGroupEntity> page;
        if(catId == 0){
            page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
        }else {
            page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper.eq(!StringUtils.isEmpty(catId), AttrGroupEntity::getCatelogId, catId)
            );
        }
        return new PageUtils(page);

    }

}