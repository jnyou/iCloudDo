package org.jnyou.interfaces.imlp;

import org.jnyou.interfaces.ReflexService;
import org.jnyou.interfaces.ReflexServiceTo;

/**
 * 分类名称
 *
 * @ClassName ReflexServiceImpl
 * @Description: 反射实现类
 * @Author: jnyou
 * @create 2020/08/17
 * @module 智慧园区
 **/
public class ReflexServiceImpl implements ReflexService , ReflexServiceTo {

    @Override
    public void getReflexInfo() {
        System.out.println("获取反射接口");
    }

    @Override
    public void reflexServiceToDo() {
        System.out.println("TODO");
    }
}