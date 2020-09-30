package org.jnyou.gmall.productservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jnyou
 */
@SpringBootApplication
@MapperScan("org.jnyou.gmall.productservice.dao")
@EnableDiscoveryClient
public class ProductWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductWebServiceApplication.class, args);
    }

}
