package org.jnyou.component.lambda;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 分类名称
 *
 * @ClassName ReturnArgsLam
 * @Description: 使用Lambda作为参数和返回值
 * @Author: jnyou
 **/
public class ReturnArgsLam {

    public static void main(String[] args) {
        startThread(() -> System.out.println("线程任务执行！"));

        String[] array = {"abc", "ab", "abcd"};
        System.out.println(Arrays.toString(array));
        Arrays.sort(array, newComparator());
        System.out.println(Arrays.toString(array));

    }

    public static void startThread(Runnable task) {
        new Thread(task).start();
    }

    public static Comparator<String> newComparator () {
        return (a, b) -> b.length() - a.length();
    }

}