package com.blithe.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CmsWebApplication
 * @Description: start  @EnableCaching 启用缓存，这个注解很重要；
 * @Author: 夏小颜
 * @Date: 13:53
 * @Version: 1.0
 **/
@EnableCaching
@MapperScan(basePackages = {"com.blithe.cms.mapper"})
@SpringBootApplication
@EnableTransactionManagement
public class CmsWebApplication {
    public static void main(String[] args) {
//        System.out.println(" .   ____          _            __ _ _\n" +
//                " /\\\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\\n" +
//                "( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\\n" +
//                " \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )\n" +
//                "  '  |____| .__|_| |_|_| |_\\__, | / / / /\n" +
//                " =========|_|==============|___/=/_/_/_/");
        SpringApplication.run(CmsWebApplication.class);
        System.out.println("http://localhost:52350/login");
    }
}