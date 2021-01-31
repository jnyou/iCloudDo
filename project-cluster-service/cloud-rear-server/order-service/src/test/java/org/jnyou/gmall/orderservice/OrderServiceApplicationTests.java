package org.jnyou.gmall.orderservice;

import lombok.extern.slf4j.Slf4j;
import org.jnyou.gmall.orderservice.entity.OrderReturnReasonEntity;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@Slf4j
@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessageTest() {
        for (int i = 0; i < 10; i++) {
            OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
            reasonEntity.setId(1L).setCreateTime(new Date()).setName("退货-" + i);
            // 发送消息 tip：如果发送的消息是对象，需要使用序列化机制，所以bean需要实现Serializable接口
            String message = "Hello World!";
            rabbitTemplate.convertAndSend("java-exchange", "hello.java", reasonEntity);
            log.info("消息发送完成{}", reasonEntity.getName());
        }

    }

    /**
     * 1、如何创建Exchange、Queue、Binding
     * 使用AmqpAdmin进行创建。
     * 2、如何收发消息
     *
     * @Author JnYou
     */
    @Test
    void createExchange() {
        // 声明一个交换机
        amqpAdmin.declareExchange(new DirectExchange("java-exchange", true, false));
        log.info("Exchange[{}]创建成功", "java-exchange");
    }

    @Test
    void createQueue() {
        // 声明一个队列
        amqpAdmin.declareQueue(new Queue("java-queue", true, false, false));
        log.info("Queue[{}]创建成功", "java-queue");
    }

    @Test
    void createBinding() {
        // 声明一个队列
        // String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
        amqpAdmin.declareBinding(new Binding("java-queue", Binding.DestinationType.QUEUE, "java-exchange", "hello.java", null));
        log.info("Binding[{}]创建成功", "java-binding");
    }

}
