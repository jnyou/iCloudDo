package org.jnyou.gmall.thridpartyservice.controller;

import org.jnyou.common.utils.R;
import org.jnyou.gmall.thridpartyservice.component.SmsComponent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/sms")
public class SmsSendController {

    @Resource
    private SmsComponent smsComponent;

    /**
     * 提供给别的服务进行调用
     * @param phone
     * @param code
     * @return
     */
    @GetMapping(value = "/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {

        //发送验证码
//        smsComponent.sendCode(phone,code);
        System.out.println(phone+code);
        return R.ok();
    }

}
