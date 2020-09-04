package org.jnyou.component.lambda;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.Assert;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 分类名称
 *
 * @ClassName FunctionDto
 * @Description: function interface
 * @Author: jnyou
 **/
@Slf4j
public class FunctionDto {

    public static void main(String[] args) {
        // Supplier<T>
        printMax();

        // Consumer<T>
        consumeString(s -> System.out.println(s));
        consumeString(s -> System.out.println(s.toUpperCase()), s -> System.out.println(s.toLowerCase()));
        printInfo();

        // Predicate<T>
        method(s -> s.length() > 5);
        method(s -> s.contains("H"), s -> s.contains("W"));
        printFilter();

        // Function<T,R>
        methods(s -> Integer.parseInt(s));
        methods(str -> Integer.parseInt(str) + 10, i -> i *= 10);
        printConcat();

    }

    /**
     * Supplier demo
     * 计算数组的最大值
     * 定一个方法,方法的参数传递Supplier,泛型使用Integer
     *
     * @param sup
     * @return
     * @Author jnyou
     */
    public static int getMax(Supplier<Integer> sup) {

        return sup.get();

    }

    public static void printMax() {
        int[] arr = {2, 3, 4, 52, 333, 23};
        //调用getMax方法,参数传递Lambda
        int maxNum = getMax(() -> {
            //计算数组的最大值
            int max = arr[0];
            for (int i : arr) {
                if (i > max) {
                    max = i;
                }
            }
            return max;
        });
        System.out.println(maxNum);
    }

    private static final String TAG = "FunctionDto";


    /**
     * Consumer
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    public static void consumeString(Consumer<String> function) {

//        抽象方法：accept，意为消费一个指定泛型的数据
        function.accept("hello");

    }

    public static void consumeString(Consumer<String> one, Consumer<String> two) {
//        default.method: andThen()
//        如果一个方法的参数和返回值全都是 Consumer 类型，那么就可以实现效果：消费数据的时候，首先做一个操作，然后再做一个操作，实现组合。
//        而这个方法就是 Consumer 接口中的default方法 andThen
//        要想实现组合，需要两个或多个Lambda表达式即可，而 andThen 的语义正是“一步接一步”操作。例如两个步骤组合的情况

        one.andThen(two).accept("Hello");

    }


    /**
     * Consumer<T >
     * 格式化打印信息 demo
     *
     * @param one
     * @param two
     * @param array
     * @return
     * @Author jnyou
     */
    private static void printInfo(Consumer<String> one, Consumer<String> two, String[] array) {

        for (String info : array) {
            // 姓名：迪丽热巴。性别：女。
            one.andThen(two).accept(info);

        }

    }

    public static void printInfo() {
        String[] array = {"迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男"};

        printInfo(s -> System.out.print("姓名：" + s.split(",")[0]), s ->

                System.out.println("。性别：" + s.split(",")[1] + "。"), array);
    }


    /**
     * Predicate<?></>
     * @param predicate
     * @return
     * @Author jnyou
     */
    private static void method(Predicate<String> predicate) {
        //  Predicate 接口中包含一个抽象方法： boolean test(T t) 。用于条件判断的场景：
        // 抽象方法：test
        boolean veryLong = predicate.test("HelloWorld");

        System.out.println("字符串很长吗：" + veryLong);

        //  默认方法：negate
        //  表示取反
        boolean veryLongNe = predicate.negate().test("HelloWorld");

        System.out.println("取反info：" + veryLongNe);
    }

    private static void method(Predicate<String> one, Predicate<String> two) {
        // 默认方法：and 表示并且，属于接口中的default方法，如果是需要或者，则用or()方法
        boolean isValid = one.and(two).test("Helloworld");

        System.out.println("字符串符合要求吗：" + isValid);

    }

    /**
     * 使用Predicate进行数据过滤
     * 1. 必须为女生；
     *
     * 2. 姓名为4个字。
     * @param array
     * @param one
     * @param two
     * @return
     * @Author jnyou
     */
    private static List<String> filter(String[] array, Predicate<String> one, Predicate<String> two) {

        List<String> list = new ArrayList<>();

        for (String info : array) {

            if (one.and(two).test(info)) {

                list.add(info);

            }

        }

        return list;

    }

    public static void printFilter() {
        String[] array = {"迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男", "赵丽颖,女"};

        List<String> list = filter(array, s -> "女".equals(s.split(",")[1]), s -> s.split(",")[0].length() == 4);

        System.out.println(list);
    }


    /**
     * Function<T,R></>
     * @param function
     * @return
     * @Author jnyou
     */
    private static void methods(Function<String, Integer> function) {
        // 抽象方法：apply
        // R apply(T t)：根据类型T的参数获取类型R的结果
        // 将 String 类型转换为 Integer 类型
        int num = function.apply("10");

        System.out.println(num + 20);

    }

    /**
     * Function<T, R>
     * @param one
     * @param two
     * @return
     * @Author jnyou
     */
    private static void methods(Function<String, Integer> one, Function<Integer, Integer> two) {
        // 默认方法：andThen ,语义是“一步接一步”操作
        int num = one.andThen(two).apply("10");

        System.out.println(num + 20);
    }


    /**
     * Function<T, R>
     * @param str
     * @param one
     * @param two
     * @param three
     * @return
     * @Author jnyou
     */
    private static int getAgeNum(String str, Function<String, String> one, Function<String, Integer> two, Function<Integer, Integer> three) {
        // String str = "赵丽颖,20";
        //
        //1. 将字符串截取数字年龄部分，得到字符串；
        //
        //2. 将上一步的字符串转换成为int类型的数字；
        //
        //3. 将上一步的int数字累加100，得到结果int数字。
        return one.andThen(two).andThen(three).apply(str);

    }

    public static void printConcat() {
        String str = "赵丽颖,20";

        int age = getAgeNum(str, s -> s.split(",")[1], s -> Integer.parseInt(s), n -> n += 100);

        System.out.println(age);
    }


}