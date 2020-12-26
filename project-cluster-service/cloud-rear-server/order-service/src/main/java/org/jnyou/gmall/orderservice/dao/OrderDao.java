package org.jnyou.gmall.orderservice.dao;

import org.jnyou.gmall.orderservice.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
