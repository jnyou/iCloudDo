package org.jnyou.gmall.storageservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jnyou
 */
@SpringBootApplication
@MapperScan("org.jnyou.gmall.storageservice.dao")
public class StorageWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageWebServiceApplication.class, args);
    }

}
