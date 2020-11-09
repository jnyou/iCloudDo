package org.jnyou.remote.procedure.call.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.*;

/**
 * 分类名称
 *
 * @ClassName Client
 * @Description: 客户端
 * @Author: jnyou
 **/
public class Client {

    // 发送：output  接受：input
    // 获取代表服务端接口的动态代理对象。比如需要调用A接口（拿A接口的代理对象），B接口（拿B接口的代理对象）。。。
    // 至少需要传入请求的接口名称，ip/端口号
    public static <T> T getRemoteProxyObj(Class servieInterface, InetSocketAddress inetSocketAddress) {
        // 创建代理对象，1、ClassLoader：类的加载器 2、初始化接口列表 3、方法执行处理
        return (T) Proxy.newProxyInstance(servieInterface.getClassLoader(), new Class<?>[]{servieInterface}, new InvocationHandler() {
            // proxy；代理的对象，method：哪个方法，args：需要的参数列表
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 客户端向服务端发送请求：请求某个具体接口
                ObjectOutputStream output = null;
                ObjectInputStream input = null;
                try {
                    Socket socket = new Socket();
                    // socketAddress
                    socket.connect(inetSocketAddress);

                    output = new ObjectOutputStream(socket.getOutputStream());// 客户端发送
                    // 接口名，方法名，：writeUTF
                    output.writeUTF(servieInterface.getName());
                    output.writeUTF(method.getName());
                    // 方法参数的类型，方法参数：writeObject
                    output.writeObject(method.getParameterTypes());
                    output.writeObject(args);

                    //等待服务端处理。。
                    // 接受服务端处理后的返回值
                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject(); // 客户端 -》服务端-》返回值
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    try {
                        if (output != null) output.close();
                        if (input != null) input.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}