package org.jnyou.gmall.memberservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jnyou
 */
@SpringBootApplication
@MapperScan("org.jnyou.gmall.memberservice.dao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.jnyou.gmall.memberservice.client")
public class MemberWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberWebServiceApplication.class, args);
    }

}
