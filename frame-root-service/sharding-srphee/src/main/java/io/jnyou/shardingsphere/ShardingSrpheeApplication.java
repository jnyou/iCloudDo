package io.jnyou.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jnyou
 */
@MapperScan("io.jnyou.shardingsphere.mapper")
@SpringBootApplication
public class ShardingSrpheeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingSrpheeApplication.class, args);
    }

}