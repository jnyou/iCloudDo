package org.jnyou.nettyserver;

import org.jnyou.nettyserver.handler.WebSocketServerHandler;
import org.jnyou.nettyserver.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@EnableScheduling
@SpringBootApplication
public class NettyServerApplication {

    @Autowired
    WebSocketServerHandler webSocketServerHandler;

    public static void main(String[] args) {
        System.out.println(WebSocketServerHandler.channelGroup);
        SpringApplication.run(NettyServerApplication.class, args);
        // 启动netty的websocket服务
        new WebSocketServer().run(8081);
    }

    @Scheduled(fixedRate = 5000)
    public void task() {
        System.out.println(WebSocketServerHandler.channelMap);
        long time = new Date().getTime();
        // 群发
//        webSocketServerHandler.sendMessageAll(time + "");
        // 给指定用户发送
        WebSocketServerHandler.channelMap.keySet().forEach(item -> {
            webSocketServerHandler.sendMessage(item, time + "");
        });
//        WebSocketServerHandler.channelMap.values().forEach(item-> {
//            item.writeAndFlush(time);
//        });
//        PushMsg pushMsg = new PushMsg();
//        pushMsg.setContent("当前时间是：" + time);
//        NettyChannelMap.getInstance().pushMsg2AllClient(pushMsg);
    }

}
