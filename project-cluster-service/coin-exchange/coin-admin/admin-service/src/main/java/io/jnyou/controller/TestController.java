package io.jnyou.controller;

import io.jnyou.domain.SysUser;
import io.jnyou.model.R;
import io.jnyou.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RestController
@Api(tags = "后台管理系统的接口")
public class TestController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/user/info/{id}")
    @ApiOperation(value = "查询用户详情")
    @ApiImplicitParam(name = "id", value = "用户id")
    public R<SysUser> getUsers(@PathVariable("id") Long id) {
        SysUser user = sysUserService.getById(id);
        return R.ok(user);
    }
}