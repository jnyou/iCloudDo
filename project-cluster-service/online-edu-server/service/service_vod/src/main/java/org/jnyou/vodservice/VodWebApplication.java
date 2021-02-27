package org.jnyou.vodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName yjn
 * @Description: 视频点播服务
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages={"org.jnyou"})
@EnableDiscoveryClient
public class VodWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodWebApplication.class, args);
    }
}