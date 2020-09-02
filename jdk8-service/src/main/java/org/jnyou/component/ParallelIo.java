package org.jnyou.component;

import java.util.stream.LongStream;

/**
 * 分类名称
 *
 * @ClassName ParallelIO
 * @Description: 并行流操作
 * @Author: jnyou
 **/
public class ParallelIo {

    public static void main(String[] args) {
        LongStream.rangeClosed(0, 1000000000000L)
                .parallel()  //并行流,如果不加则默认是串行流
                .reduce(0, Long::sum);
    }

}