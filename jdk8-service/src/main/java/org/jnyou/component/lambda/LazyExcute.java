package org.jnyou.component.lambda;

import org.jnyou.function.MessageBuilder;
import org.springframework.util.Assert;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * 分类名称
 *
 * @ClassName LazyExcute
 * @Description: Lambda lazy excute
 * @Author: jnyou
 **/
public class LazyExcute {

    public static void main(String[] args) {
        String msgA = "Hello";

        String msgB = "World";

        String msgC = "Java";

//        logOne(1, msgA + msgB + msgC);

//        log(1, () -> msgA + msgB + msgC);

        // 证明Lambda的延迟
        log(2, () -> {

            System.out.println("Lambda执行！");

            return msgA + msgB + msgC;

        });
        Assert.hasText("从结果可以看出，在不符合要求的情况下，lambda将不会执行");
    }

    /**
     * <p>
     * Lambda的延迟执行
     * 有些场景的代码执行后，结果不一定会被使用，从而造成性能浪费。而Lambda表达式是延迟执行的，这正好可以作为解决方案，提升性能。
     * </p>
     * 性能浪费的案例
     *
     * @param
     * @return
     */
    public static void logOne(int level, String msg) {
        if (level == 1) {

            System.out.println(msg);

        }
    }

    /**
     * Lambda Optimized writing
     * @param level
     * @param builder
     * @return
     * @Author jnyou
     */
    public static void log(int level, MessageBuilder builder) {
        if (level == 1) {

            System.out.println(builder.buildMessage());

        }
    }


}