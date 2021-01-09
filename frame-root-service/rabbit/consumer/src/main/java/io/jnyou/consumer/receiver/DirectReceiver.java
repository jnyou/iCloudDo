package io.jnyou.consumer.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 直接交换机消费
 *
 * @author JnYou
 * @version 1.0.0
 */
@Slf4j
@Component
@RabbitListener(queues = "directQueue")
public class DirectReceiver {

    @RabbitHandler
    public void process(Map<String, Object> directMsg) {
        log.info("receiver 【direct】 message");
        log.info("【direct】 type:{}", directMsg.get("type"));
        log.info("【direct】 msgId:{}", directMsg.get("msgId"));
        log.info("【direct】 msgData:{}", directMsg.get("msgData"));
        log.info("【direct】 createTime:{}", directMsg.get("createTime"));
    }

}
