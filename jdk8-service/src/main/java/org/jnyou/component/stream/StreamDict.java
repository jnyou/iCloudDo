package org.jnyou.component.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 分类名称
 *
 * @ClassName Lambda
 * @Description: lambda expression
 * @Author: jnyou
 **/
@Slf4j
public class StreamDict {

    public static void main(String[] args) {

        String[] strs = {"aa", "bb", "cc"};
        Optional optional = Optional.ofNullable(strs);
        optional.map(str -> {
            Stream<String> stream7 = Stream.of(strs);
            stream7.forEach(item -> System.out.println(item));
            return str;
        });

        Map<String, String> map = new HashMap<>(124);
        map.put("1","1");
        map.put("2","2");map.put("2","2");
        Stream<String> stream3 = map.keySet().stream();
        Stream<String> stream4 = map.values().stream();
        Stream<Map.Entry<String, String>> stream5 = map.entrySet().stream();
        stream5.forEach(item -> System.out.println(item));

        long count = Arrays.stream(strs).filter((s) -> {
            System.out.println(s);
            return true;
        }).count();
        System.out.println(count);


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