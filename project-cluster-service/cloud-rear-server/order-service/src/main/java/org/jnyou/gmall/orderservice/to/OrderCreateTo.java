package org.jnyou.gmall.orderservice.to;

import lombok.Data;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.jnyou.gmall.orderservice.entity.OrderItemEntity;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCreateTo {

    /** 订单 **/
    private OrderEntity order;

    /** 订单项 **/
    private List<OrderItemEntity> orderItems;

    /** 订单计算的应付价格 **/
    private BigDecimal payPrice;

    /** 运费 **/
    private BigDecimal fare;

}
