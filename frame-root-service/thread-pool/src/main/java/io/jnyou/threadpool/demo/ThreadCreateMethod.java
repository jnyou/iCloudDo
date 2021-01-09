package io.jnyou.threadpool.demo;

import java.util.concurrent.*;

/**
 * <p>
 *
 * @className ThreadCreateMethod
 * @author: JnYou xiaojian19970910@gmail.com
 **/
public class ThreadCreateMethod {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1、extends Thread class
        Thread thread = new ThreadVo();
        thread.start();

        // 2、implements Runnable interface
        Thread runables = new Thread(new RunableVo());
        runables.start();

        // 3、implement Callable<V> interface（after jdk1.5）    Future：can get thread excute return result
        FutureTask<String> future = new FutureTask(new CallableVo());
        Thread t1=new Thread(future,"有返回值的线程");
        t1.start();
        future.get(); // 获取返回的结果

        // 4、创建线程池（详见 ThreadPoolCollect类）
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

    public static class CallableVo implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("Callable==执行线程方法");
            return "execute result";
        }
    }

}