package org.jnyou.remote.procedure.call.test;

import org.jnyou.remote.procedure.call.client.Client;
import org.jnyou.remote.procedure.call.server.HelloService;

import java.net.InetSocketAddress;

/**
 * 分类名称
 *
 * @ClassName ClientTest
 * @Description:
 * @Author: jnyou
 **/
public class ClientTest {

    public static void main(String[] args) throws ClassNotFoundException {
        HelloService helloService = Client.getRemoteProxyObj(Class.forName("org.jnyou.remote.procedure.call.server.HelloService"), new InetSocketAddress("127.0.0.1", 9999));
        System.out.println(helloService.sayHello("zs"));
    }

}