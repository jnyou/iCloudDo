package io.jnyou.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * NioServer
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class NioServer {

    /**
     * 客户端连接可通过运行cmd指令（可开启多个客户端连接测试），使用telnet localhost 9000
     * 发送命令：send 内容
     *
     */

    // 保存客户端的连接信息
    static List<SocketChannel> channels = new ArrayList<SocketChannel>();

    public static void main(String[] args) throws IOException {
        // 创建NIO ServerSocketChannel，与BIO的ServerSocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置ServerSocketChannel为非阻塞的，主要针对连接，如果是true，那就跟BIO一样，一直会是阻塞状态
        serverSocketChannel.configureBlocking(false);

        System.out.println("服务启动成功！");

        while (true) {
            // 非阻塞的accept方法不会阻塞；
            // NIO的非阻塞是由操作系统内部实现的。底层调用了Linux内核的accept函数
            SocketChannel socketChannel = serverSocketChannel.accept();

            // 如果有客户端进行连接
            if(null != socketChannel){
                System.out.println("连接成功！");
                // 设置SocketChannel为非阻塞的，主要针对读写操作，如果是true，下方读取数据时客户端没有向服务端发送数据，将会阻塞
                socketChannel.configureBlocking(false);
                // 保存客户端连接到集合中
                channels.add(socketChannel);
            }

            // 遍历客户端连接集合
            Iterator<SocketChannel> iterator = channels.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer buffer = ByteBuffer.allocate(128);
                // 非阻塞read方法不会阻塞
                int len = sc.read(buffer);
                // 如果有数据
                if(len > 0){
                    System.out.println("接受到客户端消息" + new String(buffer.array()));
                } else if(len == -1){ // 如果客户端断开，把socket从集合中去掉
                    iterator.remove();
                    System.out.println("客户端断开连接！");
                }
            }

        }

    }


}