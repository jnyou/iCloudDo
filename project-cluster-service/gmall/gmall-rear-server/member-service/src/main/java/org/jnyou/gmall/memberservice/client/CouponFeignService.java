package org.jnyou.gmall.memberservice.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jnyou
 * 声明式的远程调用
 */
@FeignClient("coupon-service")
public interface CouponFeignService {



}
