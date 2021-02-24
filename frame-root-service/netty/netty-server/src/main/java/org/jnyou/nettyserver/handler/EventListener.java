package org.jnyou.nettyserver.handler;

/**
 * 事件监听器
 * netty监听后对外暴露的接口，可进行存储和推送
 * @since 1.0.0
 */
public interface EventListener {


    public void onDataHandle(PacketData packetData);

}