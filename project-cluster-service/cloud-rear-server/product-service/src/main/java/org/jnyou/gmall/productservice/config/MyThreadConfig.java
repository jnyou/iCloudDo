package org.jnyou.gmall.productservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * @className MyThreadConfig
 * @author: JnYou xiaojian19970910@gmail.com
 **/
@Configuration
// ThreadPoolConfigProperties配置类没有写@Component注解，也可以使用下面注解开启
//@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class MyThreadConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties pool) {
        return new ThreadPoolExecutor(
                // 核心线程数
                pool.getCorePoolSize(),
                // 最大线程数
                pool.getMaximumPoolSize(),
                // 存活时间
                pool.getKeepAliveTime(),
                // 时间单位
                TimeUnit.SECONDS,
                // 线程队列，给100000容量大小
                new LinkedBlockingQueue<>(100000),
                // 线程池的默认工厂
                Executors.defaultThreadFactory(),
                // 可抛弃的拒绝策略
                new ThreadPoolExecutor.AbortPolicy());
    }

}