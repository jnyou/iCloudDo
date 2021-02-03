package org.jnyou.mall.cart.feign;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

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

    @RequestMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);

    @GetMapping("/product/skusaleattrvalue/salelist/{skuId}")
    List<String> getSkuSaleAttrValue(@PathVariable Long skuId);

    @GetMapping("/product/skuinfo/{skuId}/price")
    BigDecimal getPrice(@PathVariable("skuId") Long skuId);

}