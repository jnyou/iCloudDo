package io.jnyou.controller;

import io.jnyou.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TestController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Api(tags = "测试会员系统")
@RestController
public class TestController {

    @GetMapping("/test")
    @ApiOperation(value = "会员系统测试",authorizations = {@Authorization("Authorization")})
    public R<String> testR(){
        return R.ok("会员系统搭建成功...");
    }

}