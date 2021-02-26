package io.jnyou.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * io.jnyou.gateway.CloudGatewayApplication
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class CoinCloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoinCloudGatewayApplication.class, args);
    }
}