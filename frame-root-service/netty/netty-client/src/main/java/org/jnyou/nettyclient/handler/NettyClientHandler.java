package org.jnyou.nettyclient.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


/**
 * @ClassName NettyClientHandler
 * @Author: JnYou
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final byte[] req;

    private int counter;

    public NettyClientHandler() {
        req = ("From Netty Client" + System.getProperty("line.separator")).getBytes();
    }

    /**
     * 连接的时候调用的方法
     *
     * @param ctx
     * @Author JnYou
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message;
        for (int i = 100; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, StandardCharsets.UTF_8);
        String body = (String) msg;
        logger.info("server receive msg: [{}]; counter is [{}]", body, ++counter);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }

}