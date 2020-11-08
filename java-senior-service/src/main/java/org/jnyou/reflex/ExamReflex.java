package org.jnyou.reflex;

import org.jnyou.entity.Goods;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 分类名称
 *
 * @ClassName ExamReflex
 * @Description:
 * @Author: jnyou
 * @create 2020/11/08
 * @module 智慧园区
 **/
public class ExamReflex {

    public static void main(String[] args) throws Exception {
//        operateMethods();

//        operateFileds();


//        operateConstructor();

//        array1();

        array2();
    }


    // 操作对象
    public static void operateMethods() throws IllegalAccessException, InstantiationException {
        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.entity.Goods");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Goods goods = (Goods)aClazz.newInstance();
        goods.setGoodName("xiaomi");
        System.out.println(goods.getGoodName());
    }

    // 操作属性
    public static void operateFileds() throws IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.entity.Goods");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Goods goods = (Goods)aClazz.newInstance();
        // 获取当前类中的属性
        Field idfiled = aClazz.getDeclaredField("id");
        // 修改属性/方法访问权限（方法使用invoke调用）
        idfiled.setAccessible(true);
        idfiled.set(goods,1);
        System.out.println(goods.getId()); // 报错(需要修改属性/方法)：Exception in thread "main" java.lang.IllegalAccessException: Class org.jnyou.reflex.ExamReflex can not access a member of class org.jnyou.entity.Goods with modifiers "private"

        // 获取当前类中的方法
        Method privateMet = aClazz.getDeclaredMethod("privateMet", Integer.class);
        // 设置方法的访问权限
        privateMet.setAccessible(true);
        Object invoke = privateMet.invoke(goods, 20);
    }

    // 操作构造方法
    public static void operateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> aClazz = null;
        try {
            aClazz = Class.forName("org.jnyou.entity.Goods");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 当前类中所有的构造
        Constructor<?>[] declaredConstructors = aClazz.getDeclaredConstructors();

        // 获取指定的构造  在反射类型中  包装类和基本数据类型是两种类型
        Constructor<?> constructor = aClazz.getConstructor(Integer.class);
        Goods o = (Goods)constructor.newInstance(1);
        System.out.println(o);

        // 获取私有的构造(访问需要设置访问权限)
        Constructor<?> declaredConstructor = aClazz.getDeclaredConstructor(String.class);
        declaredConstructor.setAccessible(true);
        Goods o1 = (Goods)declaredConstructor.newInstance("zs");
        System.out.println(o1);

    }

    // 使用反射操作动态的一维数组
    public static void array1() throws Exception{

        Scanner input = new Scanner(System.in);
        System.out.println("请输入数组类型：");
        String type = input.nextLine(); // java.lang.String
        System.out.println("请输入数组大小：");
        int num = input.nextInt();

        Class<?> aClass = Class.forName(type);
        Object o = Array.newInstance(aClass, num);

        Array.set(o,1,"zs");
        Array.set(o,2,"zs");
        Array.set(o,3,"zs");

        System.out.println(Array.get(o,1));
        System.out.println(Array.get(o,2));
        System.out.println(Array.get(o,3));


    }


    // 使用反射操作动态的二维数组
    public static void array2() throws Exception{
        Class<Integer> type = Integer.TYPE;
        // 数组的长度
        int [] dim = {3,3} ;

        Object o = Array.newInstance(type, dim);

        // 从二维数组获取行
        Object o1 = Array.get(o, 2);
        // 从第二行第一列获取值100
        Array.set(o1,1,100);

        // 从第二行第一列取值
        System.out.println(Array.get(o1,1));

    }

}