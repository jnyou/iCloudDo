package org.jnyou.seckillservice.feign;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * CouponFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient("coupon-service")
public interface CouponFeignClient {

    /**
     * 查询最近三天需要秒杀的商品
     */
    @GetMapping("/coupon/seckillsession/getLatest3DaysSession")
    R getLatest3DaysSession();

}