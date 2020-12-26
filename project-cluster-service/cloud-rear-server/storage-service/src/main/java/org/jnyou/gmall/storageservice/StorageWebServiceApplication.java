package org.jnyou.gmall.storageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jnyou
 * 库存服务
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class StorageWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageWebServiceApplication.class, args);
    }

}

