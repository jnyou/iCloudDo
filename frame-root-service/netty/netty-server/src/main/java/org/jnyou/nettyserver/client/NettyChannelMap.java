package org.jnyou.nettyserver.client;

import io.netty.channel.socket.SocketChannel;
import org.jnyou.nettyserver.base.PushMsg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保存Channel的类
 *
 * @Author JnYou
 */
public class NettyChannelMap {
    private static Map<String, SocketChannel> map = new ConcurrentHashMap<String, SocketChannel>();
    private static NettyChannelMap instance = new NettyChannelMap();

    private NettyChannelMap() {

    }

    public static NettyChannelMap getInstance() {
        return instance;
    }

    public void addSocketChannel(String clientId, SocketChannel socketChannel) {
        map.put(clientId, socketChannel);
    }

    public SocketChannel getSocketChannel(String clientId) {
        return map.get(clientId);
    }

    public void removeSocketChannel(String clientId) {
        map.remove(clientId);
    }

    public void removeSocketChannel(SocketChannel socketChannel) {
        if (map.containsValue(socketChannel)) {//查看是否包含
            String key = null;
            SocketChannel value = null;
            for (Map.Entry<String, SocketChannel> entry : map.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if (value == socketChannel) {
                    break;
                }
            }
            map.remove(key);
        }
    }


    public void pushMsg2AllClient(PushMsg pushMsg) {
        if (map.size() == 0)
            return;
        for (SocketChannel socketChannel : map.values()) {
            socketChannel.writeAndFlush(pushMsg);
        }
    }
}