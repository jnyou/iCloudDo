package org.jnyou.rpc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author jnyou
 */ // 服务中心
public interface Server {

    /**
     * 服务开启
     */
    public void start();

    /**
     * 服务关闭
     */
    public void stop();

    /**
     * 注册服务
     */
    public void register(Class clazz, Class serviceImpl);

    // .....

}
