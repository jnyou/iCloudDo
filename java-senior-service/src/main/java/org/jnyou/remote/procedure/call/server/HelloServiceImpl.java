package org.jnyou.remote.procedure.call.server;

/**
 * 分类名称
 *
 * @ClassName HelloServiceImpl
 * @Description:
 * @Author: jnyou
 **/
public class HelloServiceImpl implements HelloService{

    @Override
    public String sayHello(String name) {
        return "hi:" +name;
    }
}