package io.jnyou.controller;

import io.jnyou.model.LoginResult;
import io.jnyou.model.R;
import io.jnyou.service.SysLoginService;
import io.jnyou.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SysLoginController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@RestController
@Api(tags = "登录的控制器")
public class SysLoginController {

    @Autowired
    SysLoginService sysLoginService;

    @PostMapping("/login")
    @ApiOperation("后台管理人员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称"),
            @ApiImplicitParam(name = "password", value = "用户密码")
    })
    public R<LoginResult> login(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        LoginResult result = sysLoginService.login(username, password);
        return R.ok(result);
    }

}