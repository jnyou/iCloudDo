package org.jnyou.mall.cart.controller;

import org.jnyou.common.constant.AuthServerConstant;
import org.jnyou.mall.cart.interceptor.InvokeInterceptor;
import org.jnyou.mall.cart.vo.UserInfoTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * CartController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Controller
public class CartController {

    /**
     * 登录  session 中有
     * 没登录：按照cookie里面带来的user-key来做
     * 如果是第一次使用，没有临时用户，则创建临时用户user-key，时间为1个月
     * @param session
     * @Author JnYou
     */
    @GetMapping("/cart.html")
    public String cartListPage(HttpSession session){
        // 快速获取到拦截器放行后的用户信息，使用ThreadLocal-同一个线程共享数据
        UserInfoTo userInfoTo = InvokeInterceptor.threadLocal.get();
        System.out.println(userInfoTo);
        return "cartList";
    }

}