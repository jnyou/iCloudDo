package org.jnyou.gmall.orderservice.dao;

import org.jnyou.gmall.orderservice.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
