package org.jnyou.smsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName yjn
 * @Description: 短信服务
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@ComponentScan(basePackages = {"org.jnyou"}) // 扫描公共模块的配置类
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
public class SmsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsWebApplication.class, args);
    }
}