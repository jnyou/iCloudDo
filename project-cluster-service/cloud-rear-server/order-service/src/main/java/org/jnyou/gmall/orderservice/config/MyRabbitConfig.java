package org.jnyou.gmall.orderservice.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter() {
        // 使用json序列化消息转换器
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 消息确认机制
     *
     * @Author JnYou
     */
    @PostConstruct // 相当于MyRabbitConfig这个类初始化完成之后进行调用
    public void initRabbitTemplate() {
        // 服务器设置确认回调（P -> B）
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 当前消息的唯一关联数据（相当于消息的唯一id）
             * @param ack 消息是否被broker收到
             * @param cause 失败的原因
             * @Author JnYou
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm...CorrelationData【" + correlationData + "】===》ack【" + ack + "】===》失败的原因【" + cause + "】");
            }
        });
        // 设置消息被队列确认的回调（E -> Q）
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递到指定的队列，就会触发这个失败回调
             * @param message 投递失败的详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange  当时消息发给哪个交换机
             * @param routingKey 当时这个消息用的路由键
             * @Author JnYou
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Fail Massage【" + message + "】===》replyCode【" + replyCode + "】===》replyText【" + replyText + "】===》exchange【" + exchange + "】===》routingKey【" + routingKey + "】");
            }
        });
    }


}