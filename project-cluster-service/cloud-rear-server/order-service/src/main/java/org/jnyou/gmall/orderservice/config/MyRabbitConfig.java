package org.jnyou.gmall.orderservice.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MyRabbitConfig
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
public class MyRabbitConfig {

    @Bean
    public MessageConverter messageConverter () {
        // 使用json序列化消息转换器
        return new Jackson2JsonMessageConverter();
    }

}