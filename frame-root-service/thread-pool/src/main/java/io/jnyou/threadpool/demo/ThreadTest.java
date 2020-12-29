package io.jnyou.threadpool.demo;

import java.util.concurrent.*;

/**
 * <p>
 *
 * @className ThreadTest
 * @author: JnYou xiaojian19970910@gmail.com
 **/
public class ThreadTest {

    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    // CompletableFuture 异步编排；Future：能够获取异步的返回结果
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

    // TODO
    public static void testThreads(String[] args) {
        // 1
        Thread thread = new ThreadVo();
        thread.start();

        // 2、
        Thread runables = new Thread(new RunableVo());
        runables.start();

        /**
         *
         * 开启线程执行的4中方式：
         * 1、extends Thread class
         * 2、implements Runnable interface
         * 3、after jdk1.5，implements Callable<?> interface</>
         * 4、创建线程池执行
         *   4.1、使用JUC中的ExecutorService接口： ExecutorService service = Executors.newFixedThreadPool(10);
         *   4.2、使用原生的ThreadPoolExecutor class   ThreadPoolExecutor executor = new ThreadPoolExecutor(, , , unit, new LinkedBlockingQueue<Runnable>(), threadFactory);
         *     7大参数：
         *     int corePoolSize：核心线程数；线程池创建之后就准备就绪线程的数量，等待接收异步任务去执行；
         *     int maximumPoolSize：最大线程数量；控制资源并发
         *     long keepAliveTime：存活时间。如果当前的线程数量大于核心数量，释放空闲线程（maximumPoolSize - corePoolSize）
         *     TimeUnit unit：线程时间单位
         *     BlockingQueue<Runnable> workQueue：阻塞队列。如果有很多任务，每次执行的数是200，其他的线程任务放在队列中，只要有线程空闲，就会去队列里面获取新的任务继续执行。
         *     ThreadFactory threadFactory：线程的创建工厂
         *     RejectedExecutionHandler handler：如果队列满了，按照指定的拒绝策略拒绝执行任务。
         *
         * 工作顺序：
         *  1、线程池创建，准备好core数量的核心线程，准备接受任务
         *  2、core满了。就会阻塞将任务放在队列中，空闲的时候直接从队列中获取任务执行
         *  3、如果队列满了，就会开启新的线程，但最多不能超过max的线程数量。
         *  4、如果max满了，就会使用RejectedExecutionHandler拒绝策略来拒绝接受任务，max都执行完成，有很多空闲，在指定的存活时间（keepAliveTime）以后，释放max-core这些线程
         *      new LinkedBlockingQueue<>() ：默认是Integer的最大值。内存不够
         *
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, 200,10 , TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        // 可缓存的线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        // 固定大小的线程池 core = max
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        // 定时任务的线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        // 单线程的方式执行，后台从队列中获取任务单个执行
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    }
    public static class ThreadVo extends Thread{
        @Override
        public void run() {
            // 执行方法
            System.out.println("Thread==执行线程方法");
        }
    }


    public static class RunableVo implements Runnable{
        @Override
        public void run() {
            // 执行方法
            System.out.println("Runnable==执行线程方法");
        }
    }

}