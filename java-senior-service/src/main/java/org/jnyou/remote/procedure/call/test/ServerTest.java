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

    public static void main(String[] args) {
        ServerImpl server = new ServerImpl(9999);
        server.register(HelloService.class, HelloServiceImpl.class);
        server.start();
    }

}