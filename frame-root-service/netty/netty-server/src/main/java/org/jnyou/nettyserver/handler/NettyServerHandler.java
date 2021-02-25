package org.jnyou.nettyserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.jnyou.nettyserver.packet.DataType;
import org.jnyou.nettyserver.packet.EventListener;
import org.jnyou.nettyserver.packet.PacketData;
import org.jnyou.nettyserver.packet.PacketHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @ClassName NettyServerHandler
 * @Author: JnYou
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int counter;

    private static final int DATA_SIZE = 171;
    private static byte[] RESP_Login = new byte[]{0x55, (byte) 0xAA, 0x00, 0x1A, 0x02, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private ByteBuffer headerBuf;
    private ByteBuffer dataRowBuf;
    //private ByteBuffer LoginBuf;
    //  private ByteBuffer dataArkBuf;
    private ByteBuffer inCrcBuf;
    private PacketHeader packetHeader;
    private EventListener linstener;
    public NettyServerHandler(EventListener linstener) {
        headerBuf = ByteBuffer.wrap(new byte[PacketHeader.SIZE]);
        headerBuf.order(ByteOrder.BIG_ENDIAN);
        dataRowBuf = ByteBuffer.wrap(new byte[DATA_SIZE]);
        dataRowBuf.order(ByteOrder.BIG_ENDIAN);
//        LoginBuf = ByteBuffer.wrap(new byte[64]);
//        LoginBuf.order(ByteOrder.BIG_ENDIAN);
//        dataArkBuf = ByteBuffer.wrap(new byte[64]);
//        dataArkBuf.order(ByteOrder.BIG_ENDIAN);
        inCrcBuf = ByteBuffer.wrap(new byte[2]);
        inCrcBuf.order(ByteOrder.BIG_ENDIAN);
        this.packetHeader = new PacketHeader();
        this.linstener=linstener;
    }

    /*
     * channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().localAddress().toString() + " 通道已激活！");
    }

    /*
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     *
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().localAddress().toString() + " 主动断开连接！");
        // 关闭流
    }

    /**
     * 功能：读取客户端/终端发送过来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ByteBuf buf = (ByteBuf) msg;
        // byte[] req = new byte[buf.readableBytes()];
        // buf.readBytes(req);
        // String body = new String(req, StandardCharsets.UTF_8);
        String body = (String) msg;
        logger.info("server receive msg: [{}]; counter is [{}]", body, ++counter);

        String respStr = "From Netty Client".equals(body) ? "From Netty Server" : "Error format";
        respStr = respStr + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(respStr.getBytes());
        ByteBuf buf = (ByteBuf) msg;
        ByteBuffer buffer = buf.nioBuffer();
        if ( buffer.getShort() == DataType.HEAD) { // 18245
            buffer.rewind();
            reviceHeader(buffer);
            reviceData(ctx,buffer);
        }
        ctx.writeAndFlush(resp);
    }

    private void reviceHeader(ByteBuffer buffer) {
        headerBuf.clear();
        buffer.get(headerBuf.array(),0,PacketHeader.SIZE);
        packetHeader.decodeFrom(headerBuf);
    }

    private void reviceData(ChannelHandlerContext ctx, ByteBuffer buffer) {
        if(packetHeader.getCmd()==DataType.CMD_Login){
//            LoginBuf.clear();
//            buffer.get(LoginBuf.array(),0,64);
//            reviceCRC(buffer);
            ctx.writeAndFlush(RESP_Login);
        }else if(packetHeader.getCmd()== DataType.CMD_ReportData){ // 32
            dataRowBuf.clear();
            buffer.get(dataRowBuf.array(),0,DATA_SIZE);
            PacketData packetData = new PacketData();
            packetData.decodeFrom(dataRowBuf);
            linstener.onDataHandle(packetData);
            reviceCRC(buffer);
        }
    }

    private void reviceCRC(ByteBuffer buffer) {
        inCrcBuf.clear();
        buffer.get(inCrcBuf.array(),0,2);
    }

    /**
     * 功能：读取完毕客户端发送过来的数据之后的操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);//.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 功能：服务端发生异常的操作
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.error("异常信息：\r\n" + cause.getMessage());
    }

}