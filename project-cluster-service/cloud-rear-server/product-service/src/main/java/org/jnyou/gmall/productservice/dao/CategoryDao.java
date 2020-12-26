package org.jnyou.gmall.productservice.dao;

import org.jnyou.gmall.productservice.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
