package org.jnyou.gmall.orderservice.config;

import org.jnyou.gmall.orderservice.interceptor.LoginUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MyWebConfig
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**");
    }
}