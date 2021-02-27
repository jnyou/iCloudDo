package org.jnyou.ossservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName yjn
 * @Description: oss服务
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"org.jnyou"}) // 扫描公共模块的配置类
@EnableDiscoveryClient
public class OssWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssWebApplication.class);
    }
}