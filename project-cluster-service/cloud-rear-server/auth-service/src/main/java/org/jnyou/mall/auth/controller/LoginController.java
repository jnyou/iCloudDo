package org.jnyou.mall.auth.controller;

import org.jnyou.common.utils.R;
import org.jnyou.mall.auth.feign.ThridPartyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * LoginController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Controller
public class LoginController {

    @Autowired
    ThridPartyClient thridPartyClient;

    @GetMapping("/sendSms")
    public R sendSms(@RequestParam("phone") String phone){

        thridPartyClient.sendCode(phone,"");
        return R.ok();
    }


}