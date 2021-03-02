package io.jnyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class TestCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestCommonApplication.class ,args) ;
    }
}