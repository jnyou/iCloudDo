package org.jnyou.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 分类名称
 *
 * @ClassName AclWebApplication
 * @Description: 权限管理模块
 * @Author: jnyou
 * @create 2020/08/23
 * @module 智慧园区
 **/
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("org.jnyou")
@MapperScan("org.jnyou.aclservice.mapper")
public class AclWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclWebApplication.class, args);
    }
}