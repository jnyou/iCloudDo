package io.jnyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * AdminServcieApplication
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AdminServcieApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServcieApplication.class,args);
    }

}