package org.jnyou.smsservice.controller;

import org.jnyou.commonutils.R;
import org.jnyou.smsservice.service.SmsApiService;
import org.jnyou.smsservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName yjn
 * @Description: 短信服务API
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@RestController
@RequestMapping("edusms/sms")
public class SmsApiController {

    @Autowired
    private SmsApiService smsApiService;

    @GetMapping("send/{phone}")
    public R code(@PathVariable String phone){
        boolean isSend = smsApiService.send(phone);
        if(isSend) {
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }

}