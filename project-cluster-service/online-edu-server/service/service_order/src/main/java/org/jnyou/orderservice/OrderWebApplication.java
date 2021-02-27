package org.jnyou.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 分类名称
 *
 * @ClassName OrderWebApplication
 * @Description: 订单启动器
 * @Author: jnyou
 * @create 2020/08/09
 * @module 智慧园区
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"org.jnyou"}) // 扫描公共模块的配置类
@EnableDiscoveryClient // nacos注册
@MapperScan("org.jnyou.orderservice.mapper")
@EnableFeignClients
public class OrderWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderWebApplication.class,args);
    }
}