package io.jnyou.rocket;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Sink 消息消费者
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public interface Sink {

    @Input("order_in")
    public MessageChannel messageChannel();

}
