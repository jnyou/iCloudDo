package io.jnyou.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    /**
     * 使用获取到的access_token获取该用户的对象
     *
     * @param principal
     * @return
     */
    @GetMapping("/user/info")
    public Principal usrInfo(Principal principal) { // 此处的principal 由OAuth2.0 框架自动注入
        // 原理就是：利用Context概念，将授权用户放在线程里面，利用ThreadLocal来获取当前的用户对象 
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return principal;
    }
}