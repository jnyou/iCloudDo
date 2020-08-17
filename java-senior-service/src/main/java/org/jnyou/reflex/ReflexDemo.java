package org.jnyou.reflex;

import lombok.SneakyThrows;
import org.apache.poi.ss.formula.functions.T;
import org.jnyou.entity.Goods;

import java.lang.reflect.Method;

/**
 * 分类名称
 *
 * @ClassName ReflexDemo
 * @Description: 反射机制
 * @Author: jnyou
 * @create 2020/08/17
 * @module 智慧园区
 **/
public class ReflexDemo {



    /**
     * 反射：反射机制是在【运行状态中】：
     *      对于任意一个类，都能够知道这个类d的所有属性和方法
     *      对于任意一个对象，都能够调用它的任何一个属性和方法
     *
     *
     * 反射提供的功能：
     *      在运行时判断一个对象所属的类；
     *      在运行时构造任意一个类的对象；
     *      在运行时判断任意一个类所具有的成员变量和方法；
     *      在运行时调用任意一个对象的方法；
     *      生成动态代理；
     *
     * 反射的入口：Class（三种方式获取） ： Class.forName()    类名.class    对象.getClass()
     **/

    public static void main(String[] args) {

        /**
         * 1、Class.forName()获取
         *
         * Class.forName("类的全路径");
         */
        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.entity.Goods");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(aClazz);

        /**
         * 2、类名.class 获取
         *
         */
        Class<Goods> goodsClass = Goods.class;
        System.out.println(goodsClass);

        /**
         * 3、对象.getClass() 获取
         *
         */
        Goods goods = new Goods();
        Class<? extends Goods> aClass1 = goods.getClass();
        System.out.println(aClass1);


    }


}