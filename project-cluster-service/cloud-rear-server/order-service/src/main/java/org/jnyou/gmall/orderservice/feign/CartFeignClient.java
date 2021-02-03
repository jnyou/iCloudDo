package org.jnyou.gmall.orderservice.feign;

import org.jnyou.gmall.orderservice.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * CartFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient("cart-service")
public interface CartFeignClient {

    @GetMapping("/currentCartItems")
    public List<OrderItemVo> getCurrentCartItems();

}
