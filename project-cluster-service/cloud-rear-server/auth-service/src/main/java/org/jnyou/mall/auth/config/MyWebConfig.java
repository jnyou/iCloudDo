package org.jnyou.mall.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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

    /**
     * 视图映射
     * @param registry
     * @Author JnYou
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login.html").setViewName("login");
        registry.addViewController("reg.html").setViewName("reg");
    }
}