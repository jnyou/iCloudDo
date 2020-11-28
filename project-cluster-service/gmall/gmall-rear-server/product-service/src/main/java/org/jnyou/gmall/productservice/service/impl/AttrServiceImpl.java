package org.jnyou.gmall.productservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jnyou.gmall.productservice.dao.AttrAttrgroupRelationDao;
import org.jnyou.gmall.productservice.dao.AttrGroupDao;
import org.jnyou.gmall.productservice.dao.CategoryDao;
import org.jnyou.gmall.productservice.entity.AttrAttrgroupRelationEntity;
import org.jnyou.gmall.productservice.entity.AttrGroupEntity;
import org.jnyou.gmall.productservice.entity.CategoryEntity;
import org.jnyou.gmall.productservice.service.AttrAttrgroupRelationService;
import org.jnyou.gmall.productservice.vo.AttrRespVo;
import org.jnyou.gmall.productservice.vo.AttrVo;
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

import org.jnyou.gmall.productservice.dao.AttrDao;
import org.jnyou.gmall.productservice.entity.AttrEntity;
import org.jnyou.gmall.productservice.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params,Long catId) {
        LambdaQueryWrapper<AttrEntity> wrapper = Wrappers.<AttrEntity>lambdaQuery()
                .and(!StringUtils.isEmpty(params.get("key")), (obj) -> {
                    obj.eq(AttrEntity::getAttrId, params.get("key")).or().like(AttrEntity::getAttrName, params.get("key"));
                });
        IPage<AttrEntity> page;

        if(catId == 0){
            page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    wrapper
            );
        }else {
            page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    wrapper.eq(!StringUtils.isEmpty(catId), AttrEntity::getCatelogId, catId)
            );
        }

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> attrRespVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (null != attrAttrgroupRelationEntity) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (null != categoryEntity) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(attrRespVos);
        return pageUtils;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        // 保存基本数据
        this.save(attrEntity);
        // 保存关联关系数据
        attrAttrgroupRelationDao.insert(new AttrAttrgroupRelationEntity().setAttrGroupId(attr.getAttrGroupId()).setAttrId(attrEntity.getAttrId()));
    }

}