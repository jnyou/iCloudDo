package org.jnyou.gmall.couponservice.dao;

import org.jnyou.gmall.couponservice.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
