package org.jnyou.statisticsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 分类名称
 *
 * @ClassName StatisticsWebApplication
 * @Description: 统计分析启动类
 * @Author: jnyou
 * @create 2020/08/22
 * @module 智慧园区
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("org.jnyou.statisticsservice.mapper")
// 扫描公共模块的配置类
@ComponentScan(basePackages = {"org.jnyou"})
// 开启定时任务
@EnableScheduling
public class StatisticsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsWebApplication.class,args);
    }
}