package org.jnyou.component;

/**
 * 分类名称
 *
 * @ClassName FinalObj
 * @Description: 内部类
 * @Author: jnyou
 * @create 2020/08/13
 * @module 智慧园区
 **/
public class FinalObj {

    public static void main(String[] args) {
        int num=0;
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println(num);
            }
        };
    }

}