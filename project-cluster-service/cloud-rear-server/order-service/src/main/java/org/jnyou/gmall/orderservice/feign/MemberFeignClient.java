package org.jnyou.gmall.orderservice.feign;

import org.jnyou.gmall.orderservice.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MemberFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient("member-service")
public interface MemberFeignClient {

    @GetMapping("/member/memberreceiveaddress/{memberId}/addresses")
    List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);

}