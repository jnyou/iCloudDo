package org.jnyou.component.stream;

import org.jnyou.entity.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
收集流中的数据
 */
public class Collect {
    // 拼接
    public void testJoining() {
        Stream<Student> studentStream = Stream.of(
                new Student("赵丽颖", 52, 95),
                new Student("杨颖", 56, 88),
                new Student("迪丽热巴", 56, 99),
                new Student("柳岩", 52, 77));

        // 根据一个字符串拼接: 赵丽颖__杨颖__迪丽热巴__柳岩
        // String names = studentStream.map(Student::getName).collect(Collectors.joining("__"));

        // 根据三个字符串拼接
        String names = studentStream.map(Student::getName).collect(Collectors.joining("__", "^_^", "V_V"));
        System.out.println("names = " + names);
    }

    // 分区
    public void testPartition() {
        Stream<Student> studentStream = Stream.of(
                new Student("赵丽颖", 52, 95),
                new Student("杨颖", 56, 88),
                new Student("迪丽热巴", 56, 55),
                new Student("柳岩", 52, 33));

        Map<Boolean, List<Student>> map = studentStream.collect(Collectors.partitioningBy(s -> {
            return s.getSocre() > 60;
        }));

        map.forEach((k , v) -> {
            System.out.println(k + " :: " + v);
        });
    }

    // 多级分组
    public void testCustomGroup() {
        Stream<Student> studentStream = Stream.of(
                new Student("赵丽颖", 52, 95),
                new Student("杨颖", 56, 88),
                new Student("迪丽热巴", 56, 55),
                new Student("柳岩", 52, 33));

        // 先根据年龄分组,每组中在根据成绩分组
        // groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
        Map<Integer, Map<String, List<Student>>> map = studentStream.collect(Collectors.groupingBy(Student::getAge, Collectors.groupingBy((s) -> {
            if (s.getSocre() > 60) {
                return "及格";
            } else {
                return "不及格";
            }
        })));

        // 遍历
        map.forEach((k, v) -> {
            System.out.println(k);
            // v还是一个map,再次遍历
            v.forEach((k2, v2) -> {
                System.out.println("\t" + k2 + " == " + v2);
            });
        });
    }

    // 分组
    public void testGroup() {
        Stream<Student> studentStream = Stream.of(
                new Student("赵丽颖", 52, 95),
                new Student("杨颖", 56, 88),
                new Student("迪丽热巴", 56, 55),
                new Student("柳岩", 52, 33));

        // Map<Integer, List<Student>> map = studentStream.collect(Collectors.groupingBy(Student::getAge));

        // 将分数大于60的分为一组,小于60分成另一组
        Map<String, List<Student>> map = studentStream.collect(Collectors.groupingBy((s) -> {
            if (s.getSocre() > 60) {
                return "及格";
            } else {
                return "不及格";
            }
        }));

        map.forEach((k, v) -> {
            System.out.println(k + "::" + v);
        });
    }

    // 其他收集流中数据的方式(相当于数据库中的聚合函数)
    public void testStreamToOther() {
        Stream<Student> studentStream = Stream.of(
                new Student("赵丽颖", 58, 95),
                new Student("杨颖", 56, 88),
                new Student("迪丽热巴", 56, 99),
                new Student("柳岩", 52, 77));

        // 获取最大值
        // Optional<Student> max = studentStream.collect(Collectors.maxBy((s1, s2) -> s1.getSocre() - s2.getSocre()));
        // System.out.println("最大值: " + max.get());

        // 获取最小值
        // Optional<Student> min = studentStream.collect(Collectors.minBy((s1, s2) -> s1.getSocre() - s2.getSocre()));
        // System.out.println("最小值: " + min.get());

        // 求总和
        // Integer sum = studentStream.collect(Collectors.summingInt(s -> s.getAge()));
        // System.out.println("总和: " + sum);

        // 平均值
        // Double avg = studentStream.collect(Collectors.averagingInt(s -> s.getSocre()));
        // Double avg = studentStream.collect(Collectors.averagingInt(Student::getSocre));
        // System.out.println("平均值: " + avg);

        // 统计数量
        Long count = studentStream.collect(Collectors.counting());
        System.out.println("统计数量: " + count);

    }

    // 将流中数据收集到数组中
    public void testStreamToArray() {
        Stream<String> stream = Stream.of("aa", "bb", "cc");

        // 转成Object数组不方便
        // Object[] objects = stream.toArray();
        // for (Object o : objects) {
        //     System.out.println("o = " + o);
        // }
        // String[]
        String[] strings = stream.toArray(String[]::new);
        for (String string : strings) {
            System.out.println("string = " + string + ", 长度: " + string.length());
        }
    }

    // 将流中数据收集到集合中
    public void testStreamToCollection() {
        Stream<String> stream = Stream.of("aa", "bb", "cc", "bb");

        // 将流中数据收集到集合中
        // collect收集流中的数据到集合中
        // List<String> list = stream.collect(Collectors.toList());
        // System.out.println("list = " + list);

        // Set<String> set = stream.collect(Collectors.toSet());
        // System.out.println("set = " + set);

        // 收集到指定的集合中ArrayList
        // ArrayList<String> arrayList = stream.collect(Collectors.toCollection(ArrayList::new));
        // System.out.println("arrayList = " + arrayList);
        HashSet<String> hashSet = stream.collect(Collectors.toCollection(HashSet::new));
        System.out.println("hashSet = " + hashSet);

    }
}
