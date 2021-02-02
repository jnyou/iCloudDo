package org.jnyou.gmall.orderservice.interceptor;

import org.jnyou.common.constant.AuthServerConstant;
import org.jnyou.common.vo.MemberResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * LoginUserInterceptor
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberResponseVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MemberResponseVo attribute = (MemberResponseVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if(null != attribute){
            // 登录了，将用户信息放入ThreadLocal中，其他的线程都能获取
            loginUser.set(attribute);
            return true;
        } else {
            // 未登录，去登录
            request.getSession().setAttribute("msg","请先登录");
            response.sendRedirect("http://auth.gmall.com/login.html");
            return false;
        }
    }
}