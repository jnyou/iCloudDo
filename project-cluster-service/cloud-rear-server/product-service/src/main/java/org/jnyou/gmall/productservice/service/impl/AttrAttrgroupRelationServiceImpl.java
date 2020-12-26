package org.jnyou.gmall.productservice.service.impl;

import org.jnyou.gmall.productservice.vo.AttrGroupRelationVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.productservice.dao.AttrAttrgroupRelationDao;
import org.jnyou.gmall.productservice.entity.AttrAttrgroupRelationEntity;
import org.jnyou.gmall.productservice.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveRelations(List<AttrGroupRelationVo> relationVos) {
        List<AttrAttrgroupRelationEntity> relationEntities = relationVos.stream().map(item -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity()
                    .setAttrGroupId(item.getAttrGroupId())
                    .setAttrId(item.getAttrId());
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        this.saveBatch(relationEntities);
    }

}