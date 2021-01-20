package org.jnyou.mall.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author jnyou
 * 解决子域session共享问题和JSON序列化机制配置类
 */
@Configuration
public class MallSessionConfig {

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        // 使用JSON序列化到Redis中
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        // 设置cookie名称
        serializer.setCookieName("JSESSIONID");
        // 设置父级域名
        serializer.setDomainName("gmall.com");
        return serializer;
    }
}
