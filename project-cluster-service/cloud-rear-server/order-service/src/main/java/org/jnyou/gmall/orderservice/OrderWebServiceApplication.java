package org.jnyou.gmall.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author jnyou
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableRabbit
@MapperScan("org.jnyou.gmall.orderservice.dao")
public class OrderWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderWebServiceApplication.class, args);
    }

}
