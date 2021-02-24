package io.jnyou.threadpool.demo;

import java.util.concurrent.*;

/**
 *
 * @author jnyou
 */
public class ThreadPoolCollect {

    /**
     *
     * 开启线程执行的4种方式：
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

    // 创建线程池方式：
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

    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "Thread ID:" + Thread.currentThread().getId());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        MyTask myTask = new MyTask();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 20; i++) {
            executorService.submit(myTask);
        }
    }


    public static void scheduledExecutorService(String args[]) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis() / 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

}
