package org.jnyou.nettyserver;

import org.jnyou.nettyserver.handler.WebSocketServerHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class NettyServerApplicationTests {

    @Autowired
    WebSocketServerHandler webSocketServerHandler;

    @Test
    void contextLoads() {
        long time = new Date().getTime();
        webSocketServerHandler.sendMessageAll("当前时间是：" + time);
    }

}
