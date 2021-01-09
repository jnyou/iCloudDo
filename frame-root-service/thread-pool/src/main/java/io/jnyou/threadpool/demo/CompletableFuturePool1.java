package io.jnyou.threadpool.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 线程池的异步编排
 *
 * @className CompletableFutureTest
 * @author: JnYou xiaojian19970910@gmail.com
 **/
public class CompletableFuturePool1 {

    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * CompletableFuture 异步编排；Future：能够获取异步的返回结果
     *
     * @param args
     * @Author JnYou
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main====start");
        // 无返回值
//        CompletableFuture.runAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            // 线程的执行体
//            int result = 10 / 2;
//            System.out.println("运行结果：" + result);
//        },executor);

        /**
         * 有返回值 whenComplete方法完成后的感知
         */
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            // 线程的执行体
//            int result = 10 / 0;
//            System.out.println("运行结果：" + result);
//            return result;
//        }, executor).whenComplete((res, err) -> {
//            // whenComplete虽然能得到异常信息，但是没法修改返回数据
//            System.out.println("异步任务完成了。。结果是：" + res + ";异常是" + err);
//        }).exceptionally(t -> {
//            // exceptionally能感知异常，同时返回一个自定义的默认值
//            return 10;
//        });

        /**
         * 有返回值 handle方法完成后的后续处理处理
         */
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            // 线程的执行体
//            int result = 10 / 0;
//            System.out.println("运行结果：" + result);
//            return result;
//        }, executor).handle((res ,err) -> {
//            if(res != null){
//                return res*2;
//            }
//            if(err != null){
//                return 0;
//            }
//            return 0;
//        });

        /**
         * 串联化（1） thenRunAsync不能获取到上一步的执行结果
         */
//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            // 线程的执行体
//            int result = 10 / 2;
//            System.out.println("运行结果：" + result);
//            return result;
//        }, executor).thenRunAsync(() -> {
//            System.out.println("任务2启动了。。。");
//        },executor);

        /**
         * 串联化（2） thenAcceptAsync能获取到上一步的执行结果，但是当前执行完成后没有返回值
         */
//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            // 线程的执行体
//            int result = 10 / 2;
//            System.out.println("运行结果：" + result);
//            return result;
//        }, executor).thenAcceptAsync(res -> {
//            System.out.println("任务2启动了。。。" + res);
//        },executor);

        /**
         * 串联化（3） thenApplyAsync能获取到上一步的执行结果，但是当前执行完成后没有返回值
         */
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            // 线程的执行体
//            int result = 10 / 2;
//            System.out.println("运行结果：" + result);
//            return result;
//        }, executor).thenApplyAsync(res -> {
//            System.out.println("任务2启动了。。。" + res);
//            return "hello" + res;
//        },executor);

        CompletableFuture<Object> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务一：" + Thread.currentThread().getId());
            // 线程的执行体
            int result = 10 / 2;
            System.out.println("任务一结束：" + result);
            return result;
        }, executor);

        CompletableFuture<Object> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务二：" + Thread.currentThread().getId());
            System.out.println("任务二结束");
            return "hello";
        }, executor);

        /**
         * 组合任务执行(前两个任务都完成则执行) (runAfterBothAsync、thenAcceptBothAsync、thenCombineAsync)
         */
        // 无返回值
//        CompletableFuture<Void> future03 = future01.runAfterBothAsync(future02,()-> {
//            System.out.println("任务一和任务二完成之后任务三开始执行");
//        } , executor);

        // 有返回值
//        future01.thenAcceptBothAsync(future02,(f1,f2) -> {
//            System.out.println("任务三开始执行。。之前的结果：" + f1 + "--->" + f2);
//        },executor);

        // 能获取到前面组合的任务的返回值，还能获取当前任务执行完成之后的返回值
//        CompletableFuture<String> future = future01.thenCombineAsync(future02, (f1, f2) -> {
//            return f1 + f2 + "hello";
//        }, executor);

        /**
         * 组合任务执行(其中有一个完成则执行) (runAfterEitherAsync、acceptEitherAsync、applyToEitherAsync)
         */
        // 前两个任务有一个执行完成后就执行任务三 无返回值
//        CompletableFuture<Void> future = future01.runAfterEitherAsync(future02, () -> {
//            System.out.println("前两个任务有一个执行完成后就执行任务三");
//        }, executor);

        // 前两个任务有一个执行完成后就执行任务三 有返回值
//        CompletableFuture<Void> future = future01.acceptEitherAsync(future02, (res) -> {
//            System.out.println("任务三开始。。。之前的结果：" + res);
//        }, executor);

        // 前两个任务有一个执行完成后就执行任务三 能获取到之前的任务返回的结果，也能返回当前任务的结果
//        CompletableFuture<Object> future = future01.applyToEitherAsync(future02, res -> {
//            System.out.println("任务三开始。。。之前的结果：" + res);
//            return res.toString() + "hello";
//        }, executor);

        /**
         * 多任务执行
         */
        CompletableFuture<String> futureImg = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的图片信息");
            return "hello.jpg";
        },executor);

        CompletableFuture<String> futureAttr = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的属性");
            return "黑色+256G";
        },executor);

        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的介绍");
            return "华为";
        },executor);

        CompletableFuture<Void> all = CompletableFuture.allOf(futureImg, futureAttr, futureDesc);
        all.get();// 等待所有的任务完成之后执行下面逻辑
        System.out.println("执行代码");

        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(futureImg, futureAttr, futureDesc);
        anyOf.get(); // 其中任意一个任务完成之后执行下面逻辑
        System.out.println("执行代码");

        // 获取返回值
//        Integer rnum = future.get();

        System.out.println("main====end");
    }

}