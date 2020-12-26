package org.jnyou.gmall.productservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jnyou.gmall.productservice.entity.BrandEntity;
import org.jnyou.gmall.productservice.entity.CategoryEntity;
import org.jnyou.gmall.productservice.service.BrandService;
import org.jnyou.gmall.productservice.service.CategoryService;
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

import org.jnyou.gmall.productservice.dao.CategoryBrandRelationDao;
import org.jnyou.gmall.productservice.entity.CategoryBrandRelationEntity;
import org.jnyou.gmall.productservice.service.CategoryBrandRelationService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;


    @Transactional
    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandName(name);
        this.update(categoryBrandRelationEntity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
        // TODO  维护其他冗余字段一致性
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        // 查询品牌名称和分类名称
        CategoryEntity categoryEntity = categoryService.getById(categoryBrandRelation.getCatelogId());
        BrandEntity brandEntity = brandService.getById(categoryBrandRelation.getBrandId());
        categoryBrandRelation.setBrandName(brandEntity.getName()).setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<CategoryBrandRelationEntity> relationEntities = this.baseMapper.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<BrandEntity> collect = relationEntities.stream().map(item -> {
            BrandEntity brandEntity = brandService.getById(item.getBrandId());
            return brandEntity;
        }).collect(Collectors.toList());

        return collect;
    }

}