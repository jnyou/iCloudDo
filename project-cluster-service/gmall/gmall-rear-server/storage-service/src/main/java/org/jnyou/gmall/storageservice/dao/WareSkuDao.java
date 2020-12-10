package org.jnyou.gmall.storageservice.dao;

import org.apache.ibatis.annotations.Param;
import org.jnyou.gmall.storageservice.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    long getSkuStock(Long skuId);
}
