package org.jnyou.gmall.orderservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MyFeignConfig 解决feign远程调用丢失头信息
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
public class MyFeignConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                // 获取开始浏览器请求过来的所有头信息
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                // 获取浏览器发送的老请求的信息
                HttpServletRequest request = requestAttributes.getRequest();
                // 同步头信息到新请求，最主要的是cookie信息
                System.out.println("调用feign 之前先进行RequestInterceptor.apply()");
                // 给新请求（feign的请求模板）添加了老请求的cookie信息
                requestTemplate.header("Cookie",request.getHeader("Cookie"));
            }
        };
    }

}