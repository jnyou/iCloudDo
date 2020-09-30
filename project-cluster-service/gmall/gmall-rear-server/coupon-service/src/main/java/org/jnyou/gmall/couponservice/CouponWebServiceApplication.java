package org.jnyou.gmall.couponservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jnyou
 */
@SpringBootApplication
@MapperScan("org.jnyou.gmall.couponservice.dao")
@EnableDiscoveryClient
public class CouponWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponWebServiceApplication.class, args);
    }

}
