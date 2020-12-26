package org.jnyou.gmall.storageservice.feign;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ProductFeignClient
 * @Author: jnyou
 **/
@FeignClient("product-service")
public interface ProductFeignClient {

    /**
     * 如果需要过网关查询则  ： @FeignClient("gmall-gateway")
     *                       @RequestMapping("/api/product/skuinfo/info/{skuId}")
     *
     * 不过网关查询：          @FeignClient("product-service")
     *                       @RequestMapping("/product/skuinfo/info/{skuId}")
     */

    @RequestMapping("/product/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);

}