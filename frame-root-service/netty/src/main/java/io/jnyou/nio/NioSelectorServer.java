package io.jnyou.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * NioSelectorServer
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class NioSelectorServer {

    public static void main(String[] args) throws IOException {
        // 创建NIO ServerSocketChannel，与BIO的ServerSocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置ServerSocketChannel为非阻塞的，主要针对连接，如果是true，那就跟BIO一样，一直会是阻塞状态
        serverSocketChannel.configureBlocking(false);
        // 打开Selector处理channel，即创建epoll
        Selector selector = Selector.open();
        // 把ServerSocketChannel注册到Selector上，并且Selector对客户端accept连接操作感知
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务启动成功！");

        while (true) {
            // 阻塞等待需要处理的事件发生
            selector.select();

            // 获取Selector中注册的全部事件的SelectionKey实例
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            // 遍历SelectionKey对事件进行处理
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                // 如果是OP_ACCEPT事件，则进行连接获取和事件注册
                if (sk.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) sk.channel();
                    SocketChannel sc = server.accept();
                    sc.configureBlocking(false);
                    // 这里只注册了读事件，如果需要给客户端发送数据可以注册写事件
                    SelectionKey selKey = sc.register(selector, SelectionKey.OP_READ);

                    System.out.println("客户端连接成功！");
                } else if (sk.isReadable()) { // 如果是OP_READ事件，则进行读取和打印
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(128);
                    // 非阻塞read方法不会阻塞
                    int len = socketChannel.read(buffer);
                    // 如果有数据
                    if (len > 0) {
                        System.out.println("接受到客户端消息" + new String(buffer.array()));
                    } else if (len == -1) { // 如果客户端断开，把socket从集合中去掉
                        iterator.remove();
                        System.out.println("客户端断开连接！");
                    }
                }
            }
        }
    }

}