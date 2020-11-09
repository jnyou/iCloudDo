package org.jnyou.remote.procedure.call;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 分类名称
 *
 * @ClassName ServerImpl
 * @Description: 服务中心的实现类
 * @Author: jnyou
 **/
public class ServerImpl implements Server {

    // map；服务端的所有可供客户端的接口，都注册到该map中；
    // key：接口的名字，value：真正的接口实现
    private final HashMap<String, Class> serviceMap = new HashMap<>();
    private int port;

    public ServerImpl(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        try {
            ServerSocket server = new ServerSocket();
            server.bind(new InetSocketAddress(port));

            // 等待客户端连接
            Socket socket = server.accept();

            // 接受到客户端连接及请求，处理该请求
            input = new ObjectInputStream(socket.getInputStream());
            // 因为序列化流ObjectInputStream需要严格根据客户端发送的顺序逐个解析：接口名，方法名 ，方法参数的类型，方法参数
            String serviceName = input.readUTF(); // 接口名
            String methodName = input.readUTF();  // 方法名
            Class[] parameterTypes = (Class[]) input.readObject(); // 方法参数的类型
            Object[] args = (Object[]) input.readObject(); // 方法参数
            // 根据客户端请求，到服务注册中心找到对应的接口
            Class aClass = serviceMap.get(serviceName);

            // 调用该方法
            Method method = aClass.getMethod(methodName, parameterTypes);
            // 执行该方法获取结果
            Object result = method.invoke(aClass.newInstance(), args);

            // 将发送执行完毕的返回值传给客户端
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) input.close();
                if (output != null) output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void stop() {

    }

    @Override
    public void register(Class clazz, Class serviceImpl) {
        serviceMap.put(clazz.getName(), serviceImpl);
    }
}