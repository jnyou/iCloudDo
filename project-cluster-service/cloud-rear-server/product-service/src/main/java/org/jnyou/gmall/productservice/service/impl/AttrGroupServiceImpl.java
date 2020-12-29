package org.jnyou.gmall.productservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jnyou.gmall.productservice.dao.AttrAttrgroupRelationDao;
import org.jnyou.gmall.productservice.entity.AttrEntity;
import org.jnyou.gmall.productservice.service.AttrService;
import org.jnyou.gmall.productservice.vo.AttrGroupWithAttrsVo;
import org.jnyou.gmall.productservice.vo.SkuItemVo;
import org.jnyou.gmall.productservice.vo.SpuItemAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrService attrService;

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

    /**
     * 根据分类查询所有的分组和分组所有的属性
     * @param catelogId
     * @return
     * @Author jnyou
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatId(Long catelogId) {
        // 根据分类查询所有分组信息
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        // 根据所有属性
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group -> {
            List<AttrEntity> relationAttr = attrService.getRelationAttr(group.getAttrGroupId());
            AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group, attrsVo);
            attrsVo.setAttrs(relationAttr);
            return attrsVo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
        // 查询出当前spu对应的所有属性的分组信息以及当前分组下的所有属性对应的值
        List<SpuItemAttrGroupVo> vos = this.baseMapper.getAttrGroupWithAttrsBySpuId(spuId,catalogId);
        return vos;
    }

}