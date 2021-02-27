package org.jnyou.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName yjn
 * @Description: 配置mybatisplus 和扫描
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Configuration
@EnableTransactionManagement
@MapperScan(value = {"org.jnyou.eduservice.mapper"})
public class MpConfiguration {

    /**
     * 乐观锁插件
     */
//    @Bean
//    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
//        return new OptimisticLockerInterceptor();
//    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return  new PaginationInterceptor();
    }

    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * SQL 执行性能分析插件
     * 开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
     */
//    @Bean
//    @Profile({"dev","test"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        //ms，超过此处设置的ms则sql不执行
//        performanceInterceptor.setMaxTime(100);
//        //SQL是否格式化 默认false
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }

}