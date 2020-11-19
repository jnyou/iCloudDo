package org.jnyou.component.stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Parallel {
    private static final int times = 500000000;
    long start;
    @Before
    public void init() {
        start = System.currentTimeMillis();
    }

    @After
    public void destory() {
        long end = System.currentTimeMillis();
        System.out.println("消耗时间:" + (end - start));
    }

    // 并行的Stream : 消耗时间:137
    @Test
    public void testParallelStream() {
        LongStream.rangeClosed(0, times).parallel().reduce(0, Long::sum);
    }

    // 串行的Stream : 消耗时间:343
    @Test
    public void testStream() {
        // 得到5亿个数字,并求和
        LongStream.rangeClosed(0, times).reduce(0, Long::sum);
    }

    // 使用for循环 : 消耗时间:235
    @Test
    public void testFor() {
        int sum = 0;
        for (int i = 0; i < times; i++) {
            sum += i;
        }
    }

    // parallelStream线程安全问题
    @Test
    public void parallelStreamNotice() {
        ArrayList<Integer> list = new ArrayList<>();
        /*IntStream.rangeClosed(1, 1000)
                .parallel()
                .forEach(i -> {
                    list.add(i);
                });
        System.out.println("list = " + list.size());*/

        // 解决parallelStream线程安全问题方案一: 使用同步代码块
        /*Object obj = new Object();
        IntStream.rangeClosed(1, 1000)
                .parallel()
                .forEach(i -> {
                    synchronized (obj) {
                        list.add(i);
                    }
                });*/

        // 解决parallelStream线程安全问题方案二: 使用线程安全的集合
        // Vector<Integer> v = new Vector();
        /*List<Integer> synchronizedList = Collections.synchronizedList(list);
        IntStream.rangeClosed(1, 1000)
                .parallel()
                .forEach(i -> {
                    synchronizedList.add(i);
                });
        System.out.println("list = " + synchronizedList.size());*/

        // 解决parallelStream线程安全问题方案三: 调用Stream流的collect/toArray
        List<Integer> collect = IntStream.rangeClosed(1, 1000)
                .parallel()
                .boxed()
                .collect(Collectors.toList());
        System.out.println("collect.size = " + collect.size());
    }

    @Test
    public void testParallel() {
        Stream.of(4, 5, 3, 9, 1, 2, 6)
                .parallel() // 转成并行流
                .filter(s -> {
                    System.out.println(Thread.currentThread() + "::" + s);
                    return s > 3;
                })
                .count();
    }

    @Test
    public void testgetParallelStream() {
        // 掌握获取并行Stream流的两种方式
        // 方式一:直接获取并行的Stream流
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.parallelStream();

        // 方式二:将串行流转成并行流
        Stream<String> parallel = list.stream().parallel();
    }

    @Test
    public void test0Serial() {
        Stream.of(4, 5, 3, 9, 1, 2, 6)
                .filter(s -> {
                    System.out.println(Thread.currentThread() + "::" + s);
                    return s > 3;
                }).count();
    }
}
