package org.jnyou.mall.auth.feign;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ThridPartyClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient("third-party-service")
public interface ThridPartyClient {

    @GetMapping(value = "/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) ;

}