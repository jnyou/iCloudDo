package org.jnyou.gmall.productservice.dao;

import org.apache.ibatis.annotations.Param;
import org.jnyou.gmall.productservice.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌分类关联
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
