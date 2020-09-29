package org.jnyou.gmall.memberservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jnyou
 */
@SpringBootApplication
@MapperScan("org.jnyou.gmall.memberservice.dao")
public class MemberWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberWebServiceApplication.class, args);
    }

}
