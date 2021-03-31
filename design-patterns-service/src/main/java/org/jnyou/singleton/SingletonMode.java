package org.jnyou.singleton;

import sun.security.jca.GetInstance;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SingletonMde
 *
 * @author: youjiannan
 * @date 03月31日 17:46
 **/
public class SingletonMode {

    private static SingletonMode instance = new SingletonMode();

    // 饿汉模式
    private SingletonMode() {
    }

    public static SingletonMode getInstance() {
        return instance;
    }

}

class Singleton {

    // 懒汉模式
    private static volatile Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {

        if (instance == null) {
            // 加锁
            synchronized (Singleton.class) {
                // 这次判断一定要上，否则会有并发问题
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;

    }

}

class NestSingleton {

    private NestSingleton(){}

    // 嵌套类（最经典）：利用了嵌套类可以访问外部类的静态属性和方法特性
    private static class Holder {
        private static NestSingleton instance = new NestSingleton();
    }

    public static NestSingleton getInstance(){
        return Holder.instance;
    }

}