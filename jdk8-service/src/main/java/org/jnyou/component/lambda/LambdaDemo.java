package org.jnyou.component.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 分类名称
 *
 * @ClassName LambdaDemo
 * @Description: Lambda base demo
 * @Author: jnyou
 **/
public class LambdaDemo {

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

        // JDK8写法
        TreeSet<String> treeSetLam=new TreeSet<>((o1, o2) -> Integer.compare(o1.length(),o2.length()));

        TreeSet<String> treeSetCom=new TreeSet<>(Comparator.comparingInt(String::length));
    }

    public static void eventDoThree() {
        List<String> list = staticMethod();

        list.stream().filter(item -> item.startsWith("张"))
                .filter(item -> item.length() == 3)
                .forEach(System.out :: println);

        list.stream().filter(item -> item.startsWith("张")).filter(item -> item.length() == 3)
                .map(item -> {
                    System.out.println(item);
                    return item;
                }).collect(Collectors.toList());

        /**
         * Code semantics：获取流、获取姓张、获取长度为three、逐一打印。
         *
         * 代码 中并没有体现使用线性循环或是其他任何算法进行遍历，我们真正要做的事情内容被更好地体现在代码中。
         *
         * @FunctionalInterface 函数式接口： a functional interface has exactly one abstract method. 一个接口中只有一个准确抽象方法
         *
         */
    }


    /**
     * loadData
     * @return
     * @Author jnyou
     */
    public static List<String> staticMethod() {
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("张强");
        list.add("张三丰");
        return list;
    }
}