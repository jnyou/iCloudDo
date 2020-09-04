package org.jnyou.component.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 分类名称
 *
 * @ClassName StreamEx
 * @Description: Lambda base demo
 * @Author: jnyou
 **/
public class StreamEx {

    public static void main(String[] args) {
        eventDoThree();
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