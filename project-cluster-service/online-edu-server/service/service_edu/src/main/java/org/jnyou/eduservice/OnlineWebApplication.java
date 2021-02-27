package org.jnyou.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName yjn
 * @Description: 启动类
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"org.jnyou"}) // 扫描公共模块的配置类
@EnableDiscoveryClient // nacos注册
@EnableFeignClients
public class OnlineWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineWebApplication.class,args);
    }

}