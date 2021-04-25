package io.jnyou.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SocketServer
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("等待连接！");
            // 阻塞等待
            final Socket client = serverSocket.accept();
            System.out.println("客户端连接成功！");
            // 优化一点BIO程序的不足和问题，让主线接受连接，开启新的子线程来进行处理
            new Thread(new Runnable() {
                public void run() {
                    try {
                        handler(client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static void handler(Socket client) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备read。。");

        // 接受客户端的数据，阻塞方法，没有数据可读时就会阻塞
        int read = client.getInputStream().read(bytes);

        System.out.println("数据读取完成！");
        if (read != -1) {
            System.out.println("接受到客户端数据：" + new String(bytes, 0, read));
        }
        // 服务端向客户端数据响应数据
        client.getOutputStream().write("helloClient".getBytes());
        client.getOutputStream().flush();
    }

}