package org.jnyou.rpc.test;

import org.jnyou.rpc.client.Client;
import org.jnyou.rpc.server.HelloService;

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
        HelloService helloService = Client.getRemoteProxyObj(Class.forName("org.jnyou.rpc.server.HelloService"), new InetSocketAddress("127.0.0.1", 9999));
        System.out.println(helloService.sayHello("zs"));
    }

}