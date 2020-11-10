package org.jnyou.socket;

import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 分类名称
 *
 * @ClassName MyDownload
 * @Description:
 * @Author: jnyou
 **/
public class ConcurrentDownload implements Runnable {

    private Socket socket;

    public ConcurrentDownload(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {

        System.out.println("与客户端连接成功...");

        // 服务端向客户端发送消息
        OutputStream outputStream = socket.getOutputStream();
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
        socket.close();
    }

}