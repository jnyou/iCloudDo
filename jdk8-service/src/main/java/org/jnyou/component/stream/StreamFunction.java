package org.jnyou.component.stream;

import org.jnyou.entity.PersonNarmal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamFunction {

    public static void main(String[] args) {

    }

    public void testContact() {
        Stream<String> streamA = Stream.of("张三");
        Stream<String> streamB = Stream.of("李四");

        // 合并成一个流
        Stream<String> newStream = Stream.concat(streamA, streamB);
        // 注意:合并流之后,不能操作之前的流啦.
        // streamA.forEach(System.out::println);

        newStream.forEach(System.out::println);
    }

    /**
     * IntStream mapToInt(ToIntFunction<? super T> mapper);
     * IntStream: 内部操作的是int类型的数据,就可以节省内存,减少自动装箱和拆箱
     *
     * @return
     * @Author jnyou
     */
    public void testNumericStream() {
        // Integer占用的内存比int多,在Stream流操作中会自动装箱和拆箱
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        // 把大于3的打印出来
        // stream.filter(n -> n > 3).forEach(System.out::println);

        // IntStream mapToInt(ToIntFunction<? super T> mapper);
        // IntStream: 内部操作的是int类型的数据,就可以节省内存,减少自动装箱和拆箱
        /*IntStream intStream = Stream.of(1, 2, 3, 4, 5).mapToInt((Integer n) -> {
            return n.intValue();
        });*/

        IntStream intStream = Stream.of(1, 2, 3, 4, 5).mapToInt(Integer::intValue);
        intStream.filter(n -> n > 3).forEach(System.out::println);
    }

    public void testMapReduce() {
        // 求出所有年龄的总和
        // 1.得到所有的年龄
        // 2.让年龄相加
        Integer totalAge = Stream.of(
                new PersonNarmal("刘德华", 58),
                new PersonNarmal("张学友", 56),
                new PersonNarmal("郭富城", 54),
                new PersonNarmal("黎明", 52))
                .map((p) -> p.getAge()).reduce(0, Integer::sum);

        System.out.println("totalAge = " + totalAge);


        // 找出最大年龄
        // 1.得到所有的年龄
        // 2.获取最大的年龄
        Integer maxAge = Stream.of(
                new PersonNarmal("刘德华", 58),
                new PersonNarmal("张学友", 56),
                new PersonNarmal("郭富城", 54),
                new PersonNarmal("黎明", 52))
                .map(p -> p.getAge())
                .reduce(0, Math::max);
        System.out.println("maxAge = " + maxAge);

        // 统计 a 出现的次数
        //                          1    0     0    1    0    1
        Integer count = Stream.of("a", "c", "b", "a", "b", "a")
                .map(s -> {
                    if (s == "a") {
                        return 1;
                    } else {
                        return 0;
                    }
                })
                .reduce(0, Integer::sum);
        System.out.println("count = " + count);
    }

    public void testReduce() {
        // T reduce(T identity, BinaryOperator<T> accumulator);
        // T identity: 默认值
        // BinaryOperator<T> accumulator: 对数据进行处理的方式
        // reduce如何执行?
        // 第一次, 将默认值赋值给x, 取出集合第一元素赋值给y
        // 第二次, 将上一次返回的结果赋值x, 取出集合第二元素赋值给y
        // 第三次, 将上一次返回的结果赋值x, 取出集合第三元素赋值给y
        // 第四次, 将上一次返回的结果赋值x, 取出集合第四元素赋值给y
        int reduce = Stream.of(4, 5, 3, 9).reduce(0, (x, y) -> {
            System.out.println("x = " + x + ", y = " + y);
            return x + y;
        });
        System.out.println("reduce = " + reduce); // 21

        // 获取最大值
        Integer max = Stream.of(4, 5, 3, 9).reduce(0, (x, y) -> {
            return x > y ? x : y;
        });
        System.out.println("max = " + max);
    }

    public void testMax_Min() {
        // 获取最大值
        // 1, 3, 5, 6
        Optional<Integer> max = Stream.of(5, 3, 6, 1).max((o1, o2) -> o1 - o2);
        System.out.println("最大值: " + max.get());

        // 获取最小值
        // 1, 3, 5, 6
        Optional<Integer> min = Stream.of(5, 3, 6, 1).min((o1, o2) -> o1 - o2);
        System.out.println("最小值: " + min.get());
    }

    public void testFind() {
        Stream<Integer> stream = Stream.of(33, 11, 22, 5);
        Optional<Integer> first = stream.findFirst();
        // Optional<Integer> first = stream.findAny();
        System.out.println(first.get());
    }

    public void testMatch() {
        Stream<Integer> stream = Stream.of(5, 3, 6, 1);

        // boolean b = stream.allMatch(i -> i > 0); // allMatch: 匹配所有元素,所有元素都需要满足条件
        // boolean b = stream.anyMatch(i -> i > 5); // anyMatch: 匹配某个元素,只要有其中一个元素满足条件即可
        boolean b = stream.noneMatch(i -> i < 0); // noneMatch: 匹配所有元素,所有元素都不满足条件
        System.out.println(b);
    }

    // distinct对自定义对象去除重复
    public void testDistinct2() {
        Stream<PersonNarmal> stream = Stream.of(
                new PersonNarmal("貂蝉", 18),
                new PersonNarmal("杨玉环", 20),
                new PersonNarmal("杨玉环", 20),
                new PersonNarmal("西施", 16),
                new PersonNarmal("西施", 16),
                new PersonNarmal("王昭君", 25)
        );

        stream.distinct().forEach(System.out::println);
    }

    public void testDistinct() {
        Stream<Integer> stream = Stream.of(22, 33, 22, 11, 33);

        stream.distinct().forEach(System.out::println);

        Stream<String> stream1 = Stream.of("aa", "bb", "aa", "bb", "cc");
        stream1.distinct().forEach(System.out::println);

    }

    public void testSorted() {
        // sorted(): 根据元素的自然顺序排序
        // sorted(Comparator<? super T> comparator): 根据比较器指定的规则排序
        Stream<Integer> stream = Stream.of(33, 22, 11, 55);

        // stream.sorted().forEach(System.out::println);
        /*stream.sorted((Integer i1, Integer i2) -> {
            return i2 - i1;
        }).forEach(System.out::println);*/

        stream.sorted((i1, i2) -> i2 - i1).forEach(System.out::println);
    }

    public void testMap() {
        Stream<String> original = Stream.of("11", "22", "33");

        // Map可以将一种类型的流转换成另一种类型的流
        // 将Stream流中的字符串转成Integer
        /*Stream<Integer> stream = original.map((String s) -> {
            return Integer.parseInt(s);
        });*/
        // original.map(s -> Integer.parseInt(s)).forEach(System.out::println);

        original.map(Integer::parseInt).forEach(System.out::println);
    }

    public void testSkip() {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子");

        // 跳过前两个数据
        one.stream()
                .skip(2)
                .forEach(System.out::println);
    }

    public void testLimit() {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子");

        // 获取前3个数据
        one.stream()
                .limit(3)
                .forEach(System.out::println);
    }

    public void testFilter() {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子");

        // 得到名字长度为3个字的人(过滤)
        // filter(Predicate<? super T> predicate)
        /*one.stream().filter((String s) -> {
            return s.length() == 3;
        }).forEach(System.out::println);*/

        // one.stream().filter(s -> s.length() == 3).forEach(System.out::println);
    }

    public void testCount() {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子");

        long count = one.stream().count();
        System.out.println(count);
    }

    public void testForEach() {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子");

        /*// 得到流
        // 调用流中的方法
        one.stream().forEach((String str) -> {
            System.out.println(str);
        });

        // Lambda可以省略
        one.stream().forEach(str -> System.out.println(str));*/

        // Lambda可以转成方法引用
        one.stream().forEach(System.out::println);

    }
}
