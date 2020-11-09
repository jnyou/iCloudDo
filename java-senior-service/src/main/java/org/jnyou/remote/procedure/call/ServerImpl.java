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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private static HashMap<String, Class> serviceMap = new HashMap<>();
    private int port;
    /**
     * 连接池：连接池中存在多个连接对象，每个连接对象都可以处理一个客户端请求
     * 根据CPU处理个数创建一个线程池（获取处理器）
     */
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    // 服务启动和关闭标识
    private static boolean isRunning = false;

    public ServerImpl(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        ServerSocket server = null;
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = true;
        // 优化开启多线程处理并发客户端请求
        while (true) {
            // 如果想让多个客户端请求并发执行 -》多线程

            System.out.println("Server Start....");
            /**
             * 以下两行代码解析：客户端每次请求一次连接，则服务端从连接池中获取一个线程对象去处理
             */
            Socket socket = null;
            try {
                // 等待客户端连接
                socket = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 启动线程：从线程池中取线程执行，每执行一个execute()方法，执行一个线程
            executor.execute(new ServiceTask(socket));

        }

    }

    @Override
    public void stop() { // 关闭服务
        isRunning = false;
        executor.shutdown();
    }

    @Override
    public void register(Class clazz, Class serviceImpl) {
        serviceMap.put(clazz.getName(), serviceImpl);
    }

    /**
     * 执行一个线程任务方法
     */
    private static class ServiceTask implements Runnable {
        // 一个客户端请求得一个socket处理，通过初始化传入start()方法中得socket对象
        private Socket socket;

        public ServiceTask() {
        }

        public ServiceTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {

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

    }

}