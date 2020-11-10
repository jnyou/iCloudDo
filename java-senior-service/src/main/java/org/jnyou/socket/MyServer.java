package org.jnyou.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 分类名称
 *
 * @ClassName MyServer
 * @Author: jnyou
 **/
public class MyServer {

    /**
     * socket基础知识：
     * 服务端通过ServerSocket对象暴露一个端口，通过里面的accept()方法等待客户端的连接
     * 客户端通过Socket对象与服务端进行通信，连接服务端的IP和port
     * <p>
     * 附：消息发送使用OutputStream输出流，接受消息使用InputStream输入流，消息都是使用字节byte类型进行传递
     */

    public static void main(String[] args) throws IOException {
        // 字符串发送
//        serverInstance();

        // 文件发送
//        serverFile();

        // 并发执行
        concurrent();
    }


    // 服务端与客户端的一次字符串通信
    public static void serverInstance() throws IOException {
        // 暴露一个服务，默认为 IP为本机
        ServerSocket socket = new ServerSocket(9999);
        // 等到客户端访问
        Socket accept = socket.accept();
        System.out.println("与客户端连接成功...");

        // 服务端向客户端发送消息
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write("hello".getBytes());

        // 接受客户端发送的消息
        InputStream inputStream = accept.getInputStream();
        byte[] bs = new byte[100];
        inputStream.read(bs);
        System.out.println("Server 接受客户端消息" + new String(bs));

        // 关闭资源
        socket.close();
        accept.close();
        outputStream.close();
        inputStream.close();
    }

    // 服务端与客户端文件消息通信
    public static void serverFile() throws IOException {

        // 暴露一个服务，默认为 IP为本机
        ServerSocket socket = new ServerSocket(9999);

        while (true) {
            // 等到客户端访问
            Socket accept = socket.accept();
            System.out.println("与客户端连接成功...");

            // 服务端向客户端发送消息
            OutputStream outputStream = accept.getOutputStream();
            File file = new File("D:\\MeDrivers\\谷歌访问助手.zip");
            // 将文件从硬盘读到计算机内存中：通过FileInputStream输入流
            InputStream in = new FileInputStream(file);
            int len = -1;  // 读取完毕
            // 可能文件较大，不能一次性发送完，需分批发送，进行遍历发送字节
            byte[] buf = new byte[100]; // 定义每次发送文件的字节大小的缓冲区
            while ((len = in.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }

            outputStream.close();
            in.close();
            accept.close();
        }
    }

    // 并发执行下载处理
    public static void concurrent() throws IOException {
        // 暴露一个服务，默认为 IP为本机
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            // 等到客户端访问
            Socket socket = serverSocket.accept();
            new Thread(new ConcurrentDownload(socket)).start();
        }
    }

}