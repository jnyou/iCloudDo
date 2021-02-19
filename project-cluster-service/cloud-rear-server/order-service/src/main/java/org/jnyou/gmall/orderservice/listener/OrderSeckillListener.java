package org.jnyou.gmall.orderservice.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.common.to.mq.SeckillOrderTo;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.jnyou.gmall.orderservice.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * OrderSeckillListener
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Slf4j
@Component
@RabbitListener(queues = "order.seckill.order.queue")
public class OrderSeckillListener {

    @Autowired
    OrderService orderService;

    @RabbitHandler
    public void listener(SeckillOrderTo order, Channel channel, Message message) throws IOException {
        log.info("收到秒杀的订单，准备创建订单" + order.getOrderSn());
        try {
            orderService.createSeckillOrder(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }

}