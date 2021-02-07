package org.jnyou.gmall.orderservice.config;

import com.rabbitmq.client.Channel;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MyMQConfig
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
public class MyRabbitConfig {

    @RabbitListener(queues = "order.release.order.queue")
    public void listener(OrderEntity orderEntity, Channel channel, Message message) throws IOException {
        System.out.println("收到过期的订单信息，准备关闭订单" + orderEntity);
        // 确认模式
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    /**
     * 延迟队列
     * @Author JnYou
     */
    @Bean
    public Queue orderDelayQueue(){
        Map<String, Object> arguments = new HashMap<>(124);
        // 死信路由交换器
        arguments.put("x-dead-letter-exchange","order-event-exchange");
        // 死信路由键
        arguments.put("x-dead-letter-routing-key","order.release.order");
        // 过期时间，一分钟
        arguments.put("x-message-ttl",60000);
        /**
         Queue(String name,  队列名字
         boolean durable,  是否持久化
         boolean exclusive,  是否排他
         boolean autoDelete, 是否自动删除
         Map<String, Object> arguments) 属性
         */
        return new Queue("order.delay.queue", true, false, false,arguments);
    }

    /**
     * 死信队列
     * @Author JnYou
     */
    @Bean
    public Queue orderReleaseOrderQueue(){
// String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        return new Queue("order.release.order.queue", true, false, false);
    }

    @Bean
    public Exchange orderEventQueue(){
        /**
         *   String name,
         *   boolean durable,
         *   boolean autoDelete,
         *   Map<String, Object> arguments
         */
        return new TopicExchange("order-event-exchange", true, false);
    }

    @Bean
    public Binding OrderCreateOrderBingding(){
        /**
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */
        Binding binding = new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.create.order",null);
        return binding;
    }

    @Bean
    public Binding OrderReleaseOrderBingding(){
        // String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
        Binding binding = new Binding("order.release.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.order",null);
        return binding;
    }

}