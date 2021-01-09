package io.jnyou.springsecurity.controller;

import io.jnyou.springsecurity.common.HttpResult;
import io.jnyou.springsecurity.entity.User;
import io.jnyou.springsecurity.mapper.UserMapper;
import io.jnyou.springsecurity.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author JnYou
 * @version 1.0.0
 */
@RestController
public class SecurityController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @GetMapping("/list")
    public HttpResult<List<User>> userList() {

        return HttpResult.success(userMapper.list());
    }

    @RequestMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping("/hasPermission")
    public HttpResult<Boolean> hasPermission() {
        return HttpResult.success(redisService.memberInSet("1312321", "test"));
    }
}
