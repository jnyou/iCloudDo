package org.jnyou.gmall.productservice.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.productservice.dao.CategoryDao;
import org.jnyou.gmall.productservice.entity.CategoryEntity;
import org.jnyou.gmall.productservice.service.CategoryService;


/**
 * @author jnyou
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

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

    // 递归向上查询当前catelogId的父ID
    private List<Long> findParentsPath(Long catelogId, List<Long> paths) {
        CategoryEntity categoryEntity = this.baseMapper.selectById(catelogId);
        // 先加入当前ID
        paths.add(catelogId);
        if (categoryEntity.getParentCid() != 0) {
            // 还有父ID，将父ID传入方法继续查询
            findParentsPath(categoryEntity.getParentCid(),paths);
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