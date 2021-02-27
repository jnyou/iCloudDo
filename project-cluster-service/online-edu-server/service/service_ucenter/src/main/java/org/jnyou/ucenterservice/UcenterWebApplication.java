package org.jnyou.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName yjn
 * @Description: 用户服务 登陆注册。。
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@ComponentScan(basePackages = {"org.jnyou"}) // 扫描公共模块的配置类
@SpringBootApplication
@MapperScan("org.jnyou.ucenterservice.mapper")
@EnableDiscoveryClient
public class UcenterWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterWebApplication.class, args);
    }
}