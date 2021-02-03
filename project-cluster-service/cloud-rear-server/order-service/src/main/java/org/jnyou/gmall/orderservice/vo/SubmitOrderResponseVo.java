package org.jnyou.gmall.orderservice.vo;

import lombok.Data;
import org.jnyou.gmall.orderservice.entity.OrderEntity;

@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;

    /** 错误状态码 **/
    private Integer code;
}
