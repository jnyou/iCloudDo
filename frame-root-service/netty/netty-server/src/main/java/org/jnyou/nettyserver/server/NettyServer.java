package org.jnyou.nettyserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.jnyou.nettyserver.handler.NettyServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName NettyServer
 * @Author: JnYou
 **/
@SuppressWarnings(value = "unchecked")
public class NettyServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;

    public NettyServer(int port) {
        this.port = port;
    }

    public void startUp() {
        // 负责处理新的客户端的接入请求（服务端接受客户端的连接）
        bossGroup = new NioEventLoopGroup();
        // 负责处理连接的客户端的网络请求（进行 SocketChannel 的网络读写）
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(workerGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            logger.info("地址:" + ch.localAddress() + ":" + ch.localAddress().getPort() + "链接到NettyServer");
                            System.out.println("地址:" + ch.remoteAddress() + "链接到本服务端");
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            // 绑定端口，同步等待成功。返回 ChannelFuture 用于异步操作的通知回调
            channelFuture = sb.bind(port).sync();
            // 服务器异步创建绑定监听
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    logger.info("NettyServer服务监听端口[" + port + "]绑定成功!");
                } else {
                    logger.info("NettyServer服务监听端口[" + port + "]绑定失败!");
                }
            });
            // 等待服务监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅的退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer(8080).startUp();
    }

}