package org.jnyou.nettyserver.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义解码器，防止粘包,半包
 *
 * @since 1.0.0
 */
public class PacketDecode extends MessageToMessageDecoder<ByteBuf> {
    private byte[] remainingBytes;
    private static byte[] HEAD_DATA = new byte[]{0x55, (byte) 0xAA}; //协议帧起始序列 55AA 2个字节

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ByteBuf currBB=null;
        if(remainingBytes==null){
            currBB=msg;
        }else{
            byte[] tb = new byte[remainingBytes.length + msg.readableBytes()];
            System.arraycopy(remainingBytes, 0, tb, 0, remainingBytes.length);
            byte[] vb = new byte[msg.readableBytes()];
            msg.readBytes(vb);
            System.arraycopy(vb, 0, tb, remainingBytes.length, vb.length);
            currBB = Unpooled.copiedBuffer(tb);
        }
        while(currBB.readableBytes() > 0) {
            if(!doDecode(ctx, currBB, out)) {
                break;
            }
        }
        if(currBB.readableBytes() > 0) {
            remainingBytes = new byte[currBB.readableBytes()];
            currBB.readBytes(remainingBytes);
        }else {
            remainingBytes = null;
        }
    }

    private boolean doDecode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        if(msg.readableBytes() < 2)
            return false;
        msg.markReaderIndex();
        byte[] header = new byte[2];
        msg.readBytes(header);
        byte[] dataLength=new byte[2]; //报文的长度
        msg.readBytes(dataLength);
        if (!Arrays.equals(header, HEAD_DATA)) {
            return false;
            // throw new DecoderException("errorMagic: " + Arrays.toString(header));
        }
        int len = Integer.parseInt(DatatypeConverter.printHexBinary(dataLength), 16);
        // int len =msg.readInt();
        if(msg.readableBytes() < len) {
            msg.resetReaderIndex();
            return false;
        }
        msg.resetReaderIndex();
        byte[] body = new byte[len+4];
        msg.readBytes(body);
        //System.out.println("Received 防止沾包: 0x"+ ByteUtils.byteArrayToHexString(body));
        out.add(body);
        if(msg.readableBytes() > 0)
            return true;
        return false;
    }
}