package org.jnyou.gmall.storageservice.feign;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * OrderFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient("order-service")
public interface OrderFeignClient {

    @GetMapping("/orderservice/order/status/{orderSn}")
    R getOrderStatus(@PathVariable("orderSn") String orderSn);

}
