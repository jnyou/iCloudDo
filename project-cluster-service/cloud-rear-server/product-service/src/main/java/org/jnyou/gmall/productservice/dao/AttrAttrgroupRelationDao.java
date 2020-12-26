package org.jnyou.gmall.productservice.dao;

import org.apache.ibatis.annotations.Param;
import org.jnyou.gmall.productservice.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteBatchRelations(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
