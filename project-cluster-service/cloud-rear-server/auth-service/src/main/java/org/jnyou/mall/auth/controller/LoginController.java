package org.jnyou.mall.auth.controller;

import org.jnyou.common.constant.AuthServerConstant;
import org.jnyou.common.exception.BizCodeEnume;
import org.jnyou.common.utils.R;
import org.jnyou.mall.auth.feign.ThridPartyFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * LoginController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@RestController
public class LoginController {

    @Autowired
    ThridPartyFeignClient thridPartyFeignClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/sendSms")
    public R sendSms(@RequestParam("phone") String phone) {

        // 1、接口防刷。同一个手机号在60秒内再次放松验证码
        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if(!StringUtils.isEmpty(redisCode)){
            long redisTime = Long.parseLong(redisCode.split("_")[1]);
            // 60s内不能再发
            if(System.currentTimeMillis() - redisTime < 60000){
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(),BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        String code = UUID.randomUUID().toString().substring(0, 5) + "_" + System.currentTimeMillis();
        // 2、验证码过期时间，再次校验 Redis过期 key:phone,value:code
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, code, 10, TimeUnit.MINUTES);


        thridPartyFeignClient.sendCode(phone, code);
        return R.ok();
    }


}