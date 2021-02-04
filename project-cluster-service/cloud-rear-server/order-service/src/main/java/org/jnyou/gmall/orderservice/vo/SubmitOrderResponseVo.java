package org.jnyou.gmall.orderservice.vo;

import lombok.Data;
import org.jnyou.gmall.orderservice.entity.OrderEntity;

@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;

    /** 错误状态码 0:下单成功**/
    private Integer code;
}
