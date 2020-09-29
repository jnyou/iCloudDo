package org.jnyou.gmall.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jnyou
 */
@SpringBootApplication
@MapperScan("org.jnyou.gmall.orderservice.dao")
public class OrderWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderWebServiceApplication.class, args);
    }

}
