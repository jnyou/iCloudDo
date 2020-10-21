package org.jnyou.component;

import java.util.stream.LongStream;

/**
 * @ClassName OtherMethod
 * @Description: 其他形式
 * @Author: jnyou
 **/
public class OtherMethod {

    public static void main(String[] args) {
        System.out.println(54 & 3);
    }

    /**
     * FinalObj： 内部类
     */
    public static void finalObj() {
        int num=0;
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println(num);
            }
        };
    }

    /**
     * ParallelIO： 并行流操作
     */
    public static void parallelIo() {
        LongStream.rangeClosed(0, 1000000000000L)
                .parallel()  //并行流,如果不加则默认是串行流
                .reduce(0, Long::sum);
    }

}