package org.jnyou.gmall.memberservice.client;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jnyou
 * 声明式的远程调用
 */
@FeignClient("coupon-service") //告诉spring cloud这个接口是一个远程客户端，要调用coupon服务，再去调用coupon服务/coupon/coupon/member/list对应的方法
public interface CouponFeignService {

    @GetMapping("/couponservice/coupon/member/list")//注意写全优惠券类上还有映射//注意我们这个地方不熟控制层，所以这个请求映射请求的不是我们服务器上的东西，而是nacos注册中心的
    public R membercoupons();//得到一个R对象

}
