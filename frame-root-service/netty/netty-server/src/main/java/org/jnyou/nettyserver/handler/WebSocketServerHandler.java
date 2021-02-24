package org.jnyou.nettyserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.jnyou.nettyserver.base.BaseMsg;
import org.jnyou.nettyserver.base.LoginMsg;
import org.jnyou.nettyserver.base.PingMsg;
import org.jnyou.nettyserver.base.PongMsg;
import org.jnyou.nettyserver.client.NettyChannelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName WebSocketServerHandler
 * @Author: JnYou
 **/
@Component
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private WebSocketServerHandshaker webSocketServerHandshaker;

    //客户端组
    public  static ChannelGroup channelGroup;

    static {
        channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }
    //存储ip和channel的容器
    public static ConcurrentMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    /**
     * Handler活跃状态，表示连接成功
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端连接成功");
        channelGroup.add(ctx.channel());
        channelMap.put(ctx.channel().id().asLongText(),ctx.channel());
    }

    /**
     * 非活跃状态，没有连接远程主机的时候。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端关闭");
        channelGroup.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断传入的协议类型
        if (msg instanceof FullHttpRequest) {
            // 传统的 Http 接入
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            //  WebSocket 接入
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }

    }


    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpRequest(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://localhost:8080/websocket", null, false
        );
        webSocketServerHandshaker = factory.newHandshaker(request);
        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            webSocketServerHandshaker.handshake(ctx.channel(), request);
        }
    }

    private void sendHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {
        if (response.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();

        }
        ChannelFuture future = ctx.channel().writeAndFlush(response);
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 关闭指令
        if (frame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }

        // 是否为 PING 消息
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain())
            );
            return;
        }

        // 这里只支持文本消息，不支持二进制
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(frame.getClass().getName() + " frame types not supported");
        }
        String request = ((TextWebSocketFrame) frame).text();
        logger.info("{} received {}", ctx.channel(), request);

        //第一次连接成功后，给客户端发送消息
        sendMessageAll("连接成功");
        //获取当前channel绑定的IP地址
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String address = ipSocket.getAddress().getHostAddress();
        System.out.println("address为:"+address);
        //将IP和channel的关系保存
        if (!channelMap.containsKey(address)){
            channelMap.put(address,ctx.channel());
        }

        ctx.channel().write("From WebSocket Server :" + System.currentTimeMillis());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 给指定用户发内容
     * 后续可以掉这个方法推送消息给客户端
     */
    public void  sendMessage(String address,String message){
        Channel channel=channelMap.get(address);
//        String message="你好，这是指定消息发送";
        channel.writeAndFlush(new TextWebSocketFrame(message));
    }

    /**
     * 群发消息
     */
    public void sendMessageAll(String message){
//        String message="这是群发信息";
        channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }

}