package com.blithe.cms.config.myconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @ClassName yjn
 * @Description: 国际化支持,根据系统的操作语言来读取properties文件来进行解析
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Configuration
public class I18nConfiguration {

    @Bean
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 使用.properties里面的内容作为消息
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        // 以.application开头的文件
        messageSource.setBasename("application");
        // 设置读取配置文件中的编码格式
        messageSource.setDefaultEncoding("UTF-8");
        // 设置缓存时间2s
        messageSource.setCacheSeconds(2);

        return  messageSource;
    }


}