package org.jnyou.gmall.productservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.gmall.productservice.dao.CategoryDao;
import org.jnyou.gmall.productservice.entity.CategoryEntity;
import org.jnyou.gmall.productservice.service.CategoryBrandRelationService;
import org.jnyou.gmall.productservice.service.CategoryService;
import org.jnyou.gmall.productservice.vo.web.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author jnyou
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> all = baseMapper.selectList(null);

        // 找到一级分类
        List<CategoryEntity> level1List = all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((categoryEntity -> {
                    categoryEntity.setChildren(getChildrens(categoryEntity, all));
                    return categoryEntity;
                }))
                .sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())).collect(Collectors.toList());
        // 找到一级分类下的子分类
        return level1List;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        // TODO 检查当前删除的菜单，是否被别的地方引用

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatIdPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> resultPath = findParentsPath(catelogId, paths);
        // 将结果的数组倒叙
        Collections.reverse(resultPath);
        return resultPath.toArray(new Long[resultPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     * @return
     * @Author jnyou
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            // 维护关联冗余数据一致性
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    @Override
    public List<CategoryEntity> getLevel1Category() {
        return this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJson() {
        // 查出所有一级分类
        List<CategoryEntity> level1Categorys = getLevel1Category();
        // key : 每个一级分类的ID，value : List<Catelog2Vo>
        Map<String, List<Catelog2Vo>> listMap = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 根据一级分类查询二级分类集合
            List<Catelog2Vo> catelog2Vos = new ArrayList<>();
            List<CategoryEntity> category2EntityList = this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));

            List<Catelog2Vo> catelog2VoList = null;
            if (CollectionUtils.isNotEmpty(category2EntityList)) {
                // 封装好需要返回的数据 List<Catelog2Vo>
                catelog2VoList = category2EntityList.stream().map(c2 -> {
                    // 查找当前二级分类下的三级分类
                    List<CategoryEntity> category3Entities = this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", c2.getCatId()));
                    List<Catelog2Vo.Catelog3Vo> catelog3Vos = null;
                    if (CollectionUtils.isNotEmpty(category3Entities)) {
                        // 封装三级分类VO
                        catelog3Vos = category3Entities.stream().map(c3 -> {
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo().setCatalog2Id(c2.getCatId().toString()).setId(c3.getCatId().toString()).setName(c3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                    }
                    Catelog2Vo catelog2Vo = new Catelog2Vo().setCatalog1Id(v.getCatId().toString()).setId(c2.getCatId().toString()).setName(c2.getName()).setCatalog3List(catelog3Vos);
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2VoList;
        }));
        return listMap;
    }

    // 递归向上查询当前catelogId的父ID
    private List<Long> findParentsPath(Long catelogId, List<Long> paths) {
        CategoryEntity categoryEntity = this.baseMapper.selectById(catelogId);
        // 先加入当前ID
        paths.add(catelogId);
        if (categoryEntity.getParentCid() != 0) {
            // 还有父ID，将父ID传入方法继续查询
            findParentsPath(categoryEntity.getParentCid(), paths);
        }
        return paths;
    }

    // 递归查询分类树
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> child = all.stream()
                .filter(categoryEntity -> root.getCatId().equals(categoryEntity.getParentCid()))
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildrens(categoryEntity, all));
                    return categoryEntity;
                }).sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())).collect(Collectors.toList());
        return child;
    }

}