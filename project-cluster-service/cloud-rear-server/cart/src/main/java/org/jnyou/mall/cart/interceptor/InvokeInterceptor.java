package org.jnyou.mall.cart.interceptor;

import org.checkerframework.checker.units.qual.C;
import org.jnyou.common.constant.AuthServerConstant;
import org.jnyou.common.constant.CartConstant;
import org.jnyou.common.vo.MemberResponseVo;
import org.jnyou.mall.cart.vo.UserInfoTo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * InvokeInterceptor
 * 目的：在执行目标（Controller）方法之前，判断用户登录状态，并封装传递给controller目标请求
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Component
public class InvokeInterceptor implements HandlerInterceptor {

    public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();

    /**
     * 目标方法执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @Author JnYou
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberResponseVo member = (MemberResponseVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        UserInfoTo userInfoTo = new UserInfoTo();
        if (null != member) {
            // 用户登录了，添加用户信息
            userInfoTo.setUserId(member.getId());
        }
        // 获取每次请求过来的cookie
        Cookie[] cookies = request.getCookies();
        if(null != cookies && cookies.length > 0){
            for (Cookie cookie : cookies) {
                // 判断cookie中是否有临时用户的cookie，有了将获取值放入对象中
                if(CartConstant.TEMP_USER_COOKIE_NAME.equals(cookie.getName())){
                    userInfoTo.setUserKey(cookie.getValue());
                    userInfoTo.setTempUser(true);
                }
            }
        }
        // 如果没有临时用户，一定创建一个临时用户
        if(StringUtils.isEmpty(userInfoTo)){
            String uuid = UUID.randomUUID().toString();
            userInfoTo.setUserKey(uuid);
        }
        // 放入ThreadLocal中，供controller层使用，详见README.md file
        threadLocal.set(userInfoTo);
        // 放行
        return true;
    }

    /**
     * 目标方法执行完成之后，分配临时用户，让浏览器保存cookie
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @Author JnYou
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoTo userInfoTo = threadLocal.get();
        // 判断是否存在临时用户，不存在则给浏览器放入cookie
        if(!userInfoTo.isTempUser()){
            // 让浏览器将临时用户保存在cookie中，过期时间为一个月，key为user-key，value从threadLocal中获取（因为在执行之前就添加到了Thread中）
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, threadLocal.get().getUserKey());
            // 过期时间为一个月
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            // 作用域
            cookie.setDomain("gmall.com");

            response.addCookie(cookie);
        }
    }

}