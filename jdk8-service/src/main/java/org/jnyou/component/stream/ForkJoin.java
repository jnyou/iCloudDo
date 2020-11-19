package org.jnyou.component.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        SumRecursiveTask task = new SumRecursiveTask(1, 99999999999L);
        Long result = pool.invoke(task);
        System.out.println("result = " + result);
        long end = System.currentTimeMillis();
        System.out.println("消耗时间: " + (end - start));
    }
}

// 1.创建一个求和的任务
// RecursiveTask: 一个任务
class SumRecursiveTask extends RecursiveTask<Long> {
    // 是否要拆分的临界值
    private static final long THRESHOLD = 3000L;
    // 起始值
    private final long start;
    // 结束值
    private final long end;

    public SumRecursiveTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length < THRESHOLD) {
            // 计算
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            // 拆分
            long middle = (start + end) / 2;
            SumRecursiveTask left = new SumRecursiveTask(start, middle);
            left.fork();
            SumRecursiveTask right = new SumRecursiveTask(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}
