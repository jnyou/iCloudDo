package org.jnyou.gmall.thridpartyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jnyou
 * 第三方服务对接
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ThridPartyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThridPartyServiceApplication.class, args);
    }

}
