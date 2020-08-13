package org.jnyou.component;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * 分类名称
 *
 * @ClassName Lambda
 * @Description: lambda表达式
 * @Author: jnyou
 * @create 2020/08/13
 * @module 智慧园区
 **/
@Slf4j
public class Lambda {

    public static void main(String[] args) {

        // forEach
        Stream<String> stream = Stream.of("张无忌", "张三丰", "周芷若");
        stream.forEach(name -> System.out.println(name));
        log.info("张无忌   张三丰   周芷若");


        // filter 姓张的将被筛选出来
        Stream<String> original = Stream.of("张无忌", "张三丰", "周芷若");
        Stream<String> result = original.filter(s -> s.startsWith("张"));
        result.forEach(name -> System.out.println(name));
        log.info("张无忌   张三丰");


        // 映射map
        Stream<String> originalOne = Stream.of("10", "12", "18");
        Stream<Integer> resultOne = originalOne.map(str -> Integer.parseInt(str));


        // 统计个数：count
        Stream<String> originalTwo = Stream.of("张无忌", "张三丰", "周芷若");
        Stream<String> resultTwo = originalTwo.filter(s -> s.startsWith("张"));
        System.out.println(resultTwo.count());


        // limit  从0到当前
        Stream<String> originalThree = Stream.of("张无忌", "张三丰", "周芷若");
        Stream<String> resultThree = originalThree.limit(2);
        resultThree.forEach(name -> System.out.println(name));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


        // skip  从当前到最后
        Stream<String> originalFour = Stream.of("张无忌", "张三丰", "周芷若");
        Stream<String> resultFour = originalFour.skip(1);
        resultFour.forEach(name -> System.out.println(name));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        // concat：合并流
        Stream<String> streamA = Stream.of("张无忌");
        Stream<String> streamB = Stream.of("张翠山");
        Stream<String> resultAB = Stream.concat(streamA, streamB);
        resultAB.forEach(item -> System.out.println(item));

    }

}