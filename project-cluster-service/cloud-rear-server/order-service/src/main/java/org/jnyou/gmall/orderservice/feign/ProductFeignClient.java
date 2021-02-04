package org.jnyou.gmall.orderservice.feign;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ProductFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient("product-service")
public interface ProductFeignClient {

    @GetMapping("/product/spuinfo/skuId/{id}")
    public R getSpuInfoBySkuId(@PathVariable("id") Long skuId);

}