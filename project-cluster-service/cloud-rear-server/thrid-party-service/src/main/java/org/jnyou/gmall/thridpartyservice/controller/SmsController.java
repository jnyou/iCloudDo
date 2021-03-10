package org.jnyou.gmall.thridpartyservice.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.thridpartyservice.entity.Sms;
import org.jnyou.gmall.thridpartyservice.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * sms短信发送平台
 * @Author JnYou
 */
@RestController
@RequestMapping("/sms")
public class SmsController {


    @Autowired
    private SmsService smsService;

    @PostMapping("/sendTo")
    @ApiOperation(value = "发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sms", value = "短信的json数据")
    })
    public R sendMsg(@RequestBody @Validated Sms sms) {
        boolean isOk = smsService.sendMsg(sms);
        if (isOk) {
            return R.ok();
        }
        return R.error("发送失败");
    }
}