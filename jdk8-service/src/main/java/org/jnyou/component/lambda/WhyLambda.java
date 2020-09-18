package org.jnyou.component.lambda;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * 分类名称
 *
 * @ClassName WhyLambda
 * @Description: why use lambda do?
 * @Author: jnyou
 **/
public class WhyLambda {

    public static void main(String[] args) {
        eventDoOne();
        eventDoTwo();

    }

    /**
     * 为什么使用 Lambda 表达式
     *
     * Lambda 是一个匿名函数，我们可以把 Lambda 表达式理解为是一段可以传递的代码（将代码
     * 像数据一样进行传递）。可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。
     *
     */

    public static void eventDoOne() {
        // JDK8之前写法： 通过Runnable接口创建线程
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };

        // lambda表达式写法；效果等效于上面
        Runnable runnableLam = () -> System.out.println("hello");
    }

    public static void eventDoTwo() {
        // 比较用来排序的Comparator 接口
        TreeSet<String> treeSet=new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(),o2.length());
            }
        });

        // JDK8写法 , 双冒号是一种关键字
        TreeSet<String> treeSetLam=new TreeSet<>((o1, o2) -> Integer.compare(o1.length(),o2.length()));

        TreeSet<String> treeSetCom=new TreeSet<>(Comparator.comparingInt(String::length));
    }

}