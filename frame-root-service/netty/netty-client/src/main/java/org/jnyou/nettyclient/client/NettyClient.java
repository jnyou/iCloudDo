package org.jnyou.nettyclient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.jnyou.nettyclient.handler.NettyClientHandler;
import org.springframework.stereotype.Component;

/**
 * @ClassName NettyClient
 * @Author: JnYou
 **/
public class NettyClient {

    public void connect(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            // 发起异步连接操作
            ChannelFuture future = bootstrap.connect(host, port);
            // 等待客户端链路关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅的退出，释放线程池资源
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 8080);
    }


}