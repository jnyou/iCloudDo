package org.jnyou.mall.cart.config;

import org.jnyou.mall.cart.interceptor.InvokeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MyWebMvcConfig
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 给MVC添加拦截器机制，否则拦截器不生效
        registry.addInterceptor(new InvokeInterceptor()).addPathPatterns("/**");
    }
}