package org.jnyou.gmall.couponservice.dao;

import org.jnyou.gmall.couponservice.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
