package org.jnyou.gmall.productservice.feign;

import org.jnyou.common.utils.R;
import org.jnyou.gmall.productservice.feign.fallback.SeckillFeignFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient(value = "seckill-service",fallback = SeckillFeignFallbackImpl.class)
public interface SeckillFeignClient {

    @GetMapping("/sku/seckill/{skuId}")
    R getCurrentSeckillInfo(@PathVariable("skuId") Long skuId);

}