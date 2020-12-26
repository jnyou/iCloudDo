package org.jnyou.gmall.productservice.dao;

import org.apache.ibatis.annotations.Param;
import org.jnyou.gmall.productservice.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu信息
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    void updateSpuStastus(@Param("spuId") Long spuId, @Param("code") int code);
}
