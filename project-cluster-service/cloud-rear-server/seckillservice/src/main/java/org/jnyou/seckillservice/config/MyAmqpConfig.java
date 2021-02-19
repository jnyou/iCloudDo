package org.jnyou.seckillservice.config;

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
public class MyAmqpConfig {

//    @Autowired
//    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter() {
        // 使用json序列化消息转换器
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 消息确认机制
     *
     * 消费者确认（ack确认模式）（保证每个消息被正确消费，此时才可以从broker删除这个消息）
     * 默认是自动确认的，只要消息接收到，客户端会自动确认，服务端就会移除这个消息
     *   问题？ 我们收到很多消息，自动回复给服务器ack，只有一个消息处理成功，宕机了，其他消息发生消息丢失。
     *   solve：手动确认模式。一定加上配置 spring.rabbitmq.listener.direct.acknowledge-mode: manual。只要没有明确告诉MQ，货物被签收。没有ack，消息一是unacked状态。即使consumer宕机，消息也不会丢失，会重新变为ready状态，下次再次发送给消费者。
     *   使用channel.basicAck(deliveryTag, false); 签收模式
     *      channel.basicNack(deliveryTag,false,false); 拒签模式
     *
     * @Author JnYou
     */
//    @PostConstruct // 相当于MyRabbitConfig这个类初始化完成之后进行调用
//    public void initRabbitTemplate() {
//        // 服务器设置确认回调（P -> B）
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            /**
//             *
//             * @param correlationData 当前消息的唯一关联数据（相当于消息的唯一id）
//             * @param ack 消息是否被broker收到
//             * @param cause 失败的原因
//             * @Author JnYou
//             */
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                // 服务器收到了，在数据库修改消息的状态
//                System.out.println("confirm...CorrelationData【" + correlationData + "】===》ack【" + ack + "】===》失败的原因【" + cause + "】");
//            }
//        });
//        // 设置消息被队列确认的回调（E -> Q）
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            /**
//             * 只要消息没有投递到指定的队列，就会触发这个失败回调
//             * @param message 投递失败的详细信息
//             * @param replyCode 回复的状态码
//             * @param replyText 回复的文本内容
//             * @param exchange  当时消息发给哪个交换机
//             * @param routingKey 当时这个消息用的路由键
//             * @Author JnYou
//             */
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                // 修改数据库
//                System.out.println("Fail Massage【" + message + "】===》replyCode【" + replyCode + "】===》replyText【" + replyText + "】===》exchange【" + exchange + "】===》routingKey【" + routingKey + "】");
//            }
//        });
//    }


}