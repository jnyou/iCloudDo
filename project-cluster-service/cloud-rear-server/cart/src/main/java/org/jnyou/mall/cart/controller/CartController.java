package org.jnyou.mall.cart.controller;

import org.jnyou.common.constant.AuthServerConstant;
import org.jnyou.mall.cart.interceptor.InvokeInterceptor;
import org.jnyou.mall.cart.service.CartService;
import org.jnyou.mall.cart.vo.CartItemVo;
import org.jnyou.mall.cart.vo.CartVo;
import org.jnyou.mall.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    CartService cartService;

    /**
     * 登录  session 中有
     * 没登录：按照cookie里面带来的user-key来做
     * 如果是第一次使用，没有临时用户，则创建临时用户user-key，时间为1个月
     * @param session
     * @Author JnYou
     */
    @GetMapping("/cart.html")
    public String cartListPage(Model model){
        // 快速获取到拦截器放行后的用户信息，使用ThreadLocal-同一个线程共享数据
//        UserInfoTo userInfoTo = InvokeInterceptor.threadLocal.get();
//        System.out.println(userInfoTo);
        CartVo cartVo = cartService.getCart();
        model.addAttribute("cart",cartVo);
        return "cartList";
    }

    @GetMapping("/addToCart")
    public String addToCart (@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes model) {
        CartItemVo cartItemVo = cartService.addToCart(skuId,num);
        model.addAttribute("skuId",skuId);
        return "redirect:http://cart.gmall.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addCartToSuccessPage(@RequestParam("skuId") Long skuId,Model model) {
        // 重定向到成功页面，再次查询购物车即可,解决重复提交的问题
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("cartItem",cartItemVo);
        return "success";
    }

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId ,@RequestParam("check") Integer check) {
        cartService.checkItem(skuId,check);
        return "redirect:http://cart.gmall.com/cart.html";
    }

}