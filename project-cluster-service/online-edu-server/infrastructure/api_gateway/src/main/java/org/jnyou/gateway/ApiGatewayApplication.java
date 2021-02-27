package org.jnyou.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 分类名称
 *
 * @ClassName ApiGatewayApplication
 * @Description: 网关启动器
 * @Author: jnyou
 * @create 2020/08/22
 * @module 智慧园区
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}