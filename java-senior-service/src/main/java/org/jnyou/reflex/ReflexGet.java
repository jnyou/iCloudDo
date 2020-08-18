package org.jnyou.reflex;

import org.jnyou.entity.Goods;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 分类名称
 *
 * @ClassName ReflexGet
 * @Description: 反射获取
 * @Author: jnyou
 * @create 2020/08/17
 * @module 智慧园区
 **/
public class ReflexGet {

    public static void main(String[] args) {
//        getAllInterfaces();
//
//        getAllMethods();

//        getAllSuper();

//        getAllConstructor();

//        getAllField();

        getObjInstance();
    }


    /***
     * 获取所有接口
     */
    public static void getAllInterfaces(){

        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.interfaces.imlp.ReflexServiceImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class<?>[] interfaces = aClazz.getInterfaces();
        for (Class<?> inter : interfaces) {
            System.out.println(inter);
        }

        /**
         * 打印结果
         * interface org.jnyou.interfaces.ReflexService
         * interface org.jnyou.interfaces.ReflexServiceTo
         */
    }



    /***
     * 获取所有公共方法
     */
    public static void getAllMethods(){
        /**
         * 获取反射入口
         *
         */
        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.entity.Goods");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(aClazz);

        /**
         * 获取所有的公共方法（1.包括本类以及父类、接口中的所有方法 2.符合访问修饰符规则）
         */
        Method[] methods = aClazz.getMethods();
        for (Method method : methods) {
//            System.out.println(method);
        }

        /****
         * 打印结果：
         * public boolean org.jnyou.entity.Goods.equals(java.lang.Object)
         * public java.lang.String org.jnyou.entity.Goods.toString()
         * public int org.jnyou.entity.Goods.hashCode()
         *
         * public java.lang.Integer org.jnyou.entity.Goods.getId()
         * public void org.jnyou.entity.Goods.setCreateTime(java.util.Date)
         * public double org.jnyou.entity.Goods.getPrice()
         * public java.util.Date org.jnyou.entity.Goods.getCreateTime()
         * public double org.jnyou.entity.Goods.getWeight()
         * public java.util.Date org.jnyou.entity.Goods.getExpire()
         * public void org.jnyou.entity.Goods.setId(java.lang.Integer)
         * public void org.jnyou.entity.Goods.setExpire(java.util.Date)
         * public void org.jnyou.entity.Goods.setGoodName(java.lang.String)
         * public void org.jnyou.entity.Goods.setPrice(double)
         * public java.lang.String org.jnyou.entity.Goods.getGoodName()
         * public void org.jnyou.entity.Goods.setWeight(double)
         *
         * public final void java.lang.Object.wait() throws java.lang.InterruptedException
         * public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
         * public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
         * public final native java.lang.Class java.lang.Object.getClass()
         * public final native void java.lang.Object.notify()
         * public final native void java.lang.Object.notifyAll()
         *
         */

        /**
         * 获取当前类的所有方法（1.只能时当前类 2.忽略访问修饰符的限制）
         *
         */
        Method[] declaredMethods = aClazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }

    }

    /**
     * 获取所有的父类
     */
    public static void getAllSuper(){
        /**
         * 获取反射入口
         *
         */
        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.entity.Goods");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(aClazz);
        Class<?> superclass = aClazz.getSuperclass();
        System.out.println(superclass);
        // class java.lang.Object
    }


    /***
     * 获取所有构造器
     */
    public static void getAllConstructor(){
        /**
         * 获取反射入口
         *
         */
        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.entity.Goods");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Constructor<?>[] constructors = aClazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }

        /**
         * public org.jnyou.entity.Goods()
         * public org.jnyou.entity.Goods(java.lang.Integer,java.lang.String,double,double,java.util.Date,java.util.Date)
         */

    }



    public static void getAllField(){
        /**
         * 获取反射入口
         *
         */
        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.entity.Goods");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /***
         * 获取所有公共属性
         */
        Field[] fields = aClazz.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        /***
         * 获取当前类的所有属性(忽略修饰符限制)
         */
        Field[] declaredFields = aClazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }
    }

    /**
     * 获取当前所反射类的对象实例
     *
     */
     public static void getObjInstance() {
         /**
          * 获取反射入口
          *
          */
         Class<?> aClazz = null;
         try {
             aClazz = Class.forName("org.jnyou.entity.Goods");
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
         Goods obj = null;
         try {
             obj = (Goods) aClazz.newInstance();
         } catch (InstantiationException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         }
         System.out.println(obj);

         // Goods(id=null, goodName=null, price=0.0, weight=0.0, createTime=null, expire=null, ine=null)
     }


}