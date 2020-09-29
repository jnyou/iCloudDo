package org.jnyou.gmall.couponservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jnyou
 */
@SpringBootApplication
@MapperScan("org.jnyou.gmall.couponservice.dao")
public class CouponWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponWebServiceApplication.class, args);
    }

}
