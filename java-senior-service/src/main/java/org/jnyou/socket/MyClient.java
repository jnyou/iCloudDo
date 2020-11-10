package org.jnyou.socket;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 分类名称
 *
 * @ClassName MyClient
 * @Author: jnyou
 **/
public class MyClient {

    public static void main(String[] args) throws IOException {
//        clientInit();

        clientFile();
    }

    // 客户端与服务端的一次字符串通信
    public static void clientInit() throws IOException {
        // 客户端访问服务端暴露的服务
        Socket socket = new Socket("127.0.0.1", 9999);

        // 接受服务端发送的消息
        byte[] bs = new byte[100];
        InputStream inputStream = socket.getInputStream();
        inputStream.read(bs); // 读取服务端发送的数据
        System.out.println("Client 获取消息" + new String(bs));

        // 向服务端发送消息
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("world".getBytes());

        // 关闭资源
        socket.close();
        outputStream.close();
        inputStream.close();
    }

    // 客户端与服务端文件消息通信
    public static void clientFile() throws IOException {
        // 客户端访问服务端暴露的服务
        Socket socket = new Socket("127.0.0.1", 9999);

        // 接受服务端发送的消息
        byte[] bs = new byte[100]; // 接受每次发送过来的文件大小
        int len = -1;  // 读取完毕
        InputStream inputStream = socket.getInputStream();

        OutputStream os = new FileOutputStream("E:\\java\\aaa.zip");
        while ((len = inputStream.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        System.out.println("文件发送完成");
        // 关闭资源
        socket.close();
        os.close();
        inputStream.close();

    }


}