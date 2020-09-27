package com.blithe.cms.config.myconfig;

import com.blithe.cms.interceptor.IsMeIntercepors;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @ClassName yjn
 * @Description: mvc的配置
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /**
     * 不通过controller跳转，使用注册器空参跳转到页面
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 请求转发  请求http://localhost:8080/index/text，能够跳转到login页面，地址还是http://localhost:8080/index/text
        registry.addViewController("/test").setViewName("test");
        // 重定向  两次跳转，地址栏发生变化 请求http://localhost:8080/index/text1，会重定向到login的controller中，通过controller跳转到login页面，地址栏变为http://localhost:8080/login
        registry.addRedirectViewController("/index/text1","/login");
        // 如果没有编写login的跳转的controller，则可以再次请求转发一次到login
//        registry.addViewController("/login").setViewName("login");
    }


    /**
     * 自定义的拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        IsMeIntercepors isMeIntercepors = new IsMeIntercepors();
//        RememberMeInterceptor rememberMeInterceptor = new RememberMeInterceptor();
//        registry.addInterceptor(rememberMeInterceptor).addPathPatterns("/","/login");
//        registry.addInterceptor(isMeIntercepors)
//                // 添加需要拦截的controller路径，可配置多个
//                .addPathPatterns("/**")
//                // 放行的controller路径，可配置多个
//                .excludePathPatterns("/test");
    }

    /**
     * 自定义的消息转换器  如 Date/Double 等
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addFormatter(new Formatter<Date>() {

            @Override
            public String print(Date object, Locale locale) {
                return null;
            }

            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sf.parse(text);
            }
        });
    }

    /**
     * fastjson 自定义扩展消息转换器[一般不用配置,因为有jsckson的转换器]
     * 配置后会覆盖掉jackson转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter fc = new FastJsonHttpMessageConverter();
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        fc.setFastJsonConfig(config);
//        // 使用converters注册
//        converters.add(fc);
    }
}