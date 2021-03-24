package io.jnyou.rocket;

import io.jnyou.disruptor.DisruptorTemplate;
import io.jnyou.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MessageConsumerListener
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Slf4j
@Service
public class MessageConsumerListener {

    @Autowired
    DisruptorTemplate disruptorTemplate;

    /**
     * 消息监听
     * @return
     */
    @StreamListener("order_in")
    public void handleMessage(Order order) {
        log.info("接收到委托单");
        disruptorTemplate.onData(order);
    }


}