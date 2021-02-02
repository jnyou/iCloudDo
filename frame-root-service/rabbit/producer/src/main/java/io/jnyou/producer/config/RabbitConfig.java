package io.jnyou.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author JnYou
 * @version 1.0.0
 */
@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter messageConverter() {
        // 使用json序列化消息转换器
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        // 消息发送到交换机，ack为true，如果要发送的交换机不存在，ack为false  【服务器设置确认回调（P -> B）】
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            /**
             * @param correlationData 当前消息的唯一关联数据（相当于消息的唯一id）
             * @param ack 消息是否被broker收到
             * @param cause 失败的原因
             */
            log.info("ConfirmCallback:     相关数据：{}", correlationData);
            log.info("ConfirmCallback:     确认情况：{}", ack);
            log.info("ConfirmCallback:     原因：{}", cause);
        });
        // 消息到达交换机，但是根据路由找不到对应的队列时会调用 【设置消息被队列确认的回调（E -> Q）】
        rabbitTemplate.setReturnsCallback((ReturnedMessage returned) -> {
            /**
             * 只要消息没有投递到指定的队列，就会触发这个失败回调
             * @param message 投递失败的详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange  当时消息发给哪个交换机
             * @param routingKey 当时这个消息用的路由键
             */
            log.info("ReturnCallback:     消息：{}", returned.getMessage());
            log.info("ReturnCallback:     回应码：{}", returned.getReplyCode());
            log.info("ReturnCallback:     回应信息：{}", returned.getReplyText());
            log.info("ReturnCallback:     交换机：{}", returned.getExchange());
            log.info("ReturnCallback:     路由键：{}", returned.getRoutingKey());
        });
//        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            log.info("ReturnCallback:     消息：{}", message);
//            log.info("ReturnCallback:     回应码：{}", replyCode);
//            log.info("ReturnCallback:     回应信息：{}", replyText);
//            log.info("ReturnCallback:     交换机：{}", exchange);
//            log.info("ReturnCallback:     路由键：{}", routingKey);
//        });
        return rabbitTemplate;
    }

}
