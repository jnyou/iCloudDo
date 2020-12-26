package org.jnyou.gmall.productservice.dao;

import org.apache.ibatis.annotations.Param;
import org.jnyou.gmall.productservice.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品属性
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> selectSearchAttrs(@Param("attrIds") List<Long> attrIds);
}
