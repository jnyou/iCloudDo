package io.jnyou.consumer.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 手动确认消息 需要配置 spring.rabbitmq.listener.direct.acknowledge-mode: manual
 *
 * @author JnYou
 * @version 1.0.0
 */
@Slf4j
@Component
public class MessageAckListener implements ChannelAwareMessageListener {

    /**
     * 消息确认机制
     *
     * 消费者确认（ack确认模式）（保证每个消息被正确消费，此时才可以从broker删除这个消息）
     * 默认是自动确认的，只要消息接收到，客户端会自动确认，服务端就会移除这个消息
     *   问题？ 我们收到很多消息，自动回复给服务器ack，只有一个消息处理成功，宕机了，其他消息会发生消息丢失。
     *   solve：手动确认模式。一定加上配置 spring.rabbitmq.listener.direct.acknowledge-mode: manual。只要没有明确告诉MQ，货物被签收。没有ack，消息一是unacked状态。即使consumer宕机，消息也不会丢失，会重新变为ready状态，下次再次发送给消费者。
     *   使用channel.basicAck(deliveryTag, false); 签收模式
     *      channel.basicNack(deliveryTag,false,false); 拒签模式
     *
     */

    /**
     * 消费者ack（确认）模式
     * @param message
     * @param channel
     * @Author JnYou
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // deliveryTag在channel内按顺序自增的。
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            // 可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            String[] msgArray = msg.split("'");
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
            log.info("【AckReceiver】");
            log.info("type:{}", msgMap.get("type"));
            log.info("msgId:{}", msgMap.get("msgId"));
            log.info("msgData:{}", msgMap.get("msgData"));
            log.info("createTime:{}", msgMap.get("createTime"));
            String queue = message.getMessageProperties().getConsumerQueue();
            log.info("消费的主题消息来自：{}", queue);
            if ("defaultQueue".equals(queue)) {
                log.info("这是一个默认交换机的队列");
            } else if ("directQueue".equals(queue)) {
                log.info("这是一个直连交换机的队列");
            }
            // 肯定确认：消息已被处理
            // false 非批量模式，一个一个签收 相当于收货
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            log.error("网络中断");
            log.error("消费信息异常，重回队列");
            /*
             * 为true会重新放回队列，false则丢掉这条消息
             * 选择重入需要注意。可能会出现：
             * 消息一直消费-入列-消费-入列这样循环，会导致消息积压
             * 消息重复消费（幂等性控制）
             */
            // 退货
            // long deliveryTag, boolean multiple, boolean requeue：是否重新放入队列，false：丢弃，true：发回服务器，重新入队。
            channel.basicNack(deliveryTag,false,false);
            // long deliveryTag, boolean requeue
            // channel.basicReject();
            System.out.println("没有签收货物");
            e.printStackTrace();
        }
    }

    /**
     * 格式转换成map
     * {key=value,key=value,key=value} 格式转换成map
     */
    private Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strArr = str.split(",");
        Map<String, String> map = new HashMap<>();
        for (String string : strArr) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}
