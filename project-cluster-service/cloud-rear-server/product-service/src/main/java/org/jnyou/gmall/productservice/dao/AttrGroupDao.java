package org.jnyou.gmall.productservice.dao;

import org.apache.ibatis.annotations.Param;
import org.jnyou.gmall.productservice.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jnyou.gmall.productservice.vo.SkuItemVo;
import org.jnyou.gmall.productservice.vo.SpuItemAttrGroupVo;

import java.util.List;

/**
 * 属性分组
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);

}
