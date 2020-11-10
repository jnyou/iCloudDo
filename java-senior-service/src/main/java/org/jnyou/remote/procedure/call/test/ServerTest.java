package org.jnyou.remote.procedure.call.test;

import org.jnyou.remote.procedure.call.ServerImpl;
import org.jnyou.remote.procedure.call.server.HelloService;
import org.jnyou.remote.procedure.call.server.HelloServiceImpl;

/**
 * 分类名称
 *
 * @ClassName ServerTest
 * @Description:
 * @Author: jnyou
 **/
public class ServerTest {


    /**
     *
     *  整体RPC思路和原理：
     *  1、客户端通过socket请求服务端，并且通过以字符串形式或者Class形式将请求的接口名字发给服务端（使用动态代理方式发送：包括：接口名，方法名，方法参数类型，方法参数）
     *  2、服务端将可提供的接口注册到服务中心（通过map保存，key：请求接口的名字，value：请求接口真正的实现类）
     *  3、服务端接受到请求，获取通过请求的字符串形式的接口名字在服务中心中寻找对应的接口实现类，
     *  找到之后解析客户端发来的接口名，方法名，方法参数类型，方法参数。通过反射技术将该方法执行，执行完毕后，将结果返回给客户端
     *
     */

    public static void main(String[] args) {
        // 通过开启线程来启动服务
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerImpl server = new ServerImpl(9999);
                server.register(HelloService.class, HelloServiceImpl.class);
                server.start();
            }
        }).start();

    }

}