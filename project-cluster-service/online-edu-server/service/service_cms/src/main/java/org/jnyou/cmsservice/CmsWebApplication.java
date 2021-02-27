package org.jnyou.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName yjn
 * @Description: cms banner服务
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"org.jnyou"}) // 扫描公共模块的配置类
@MapperScan("org.jnyou.cmsservice.mapper")
public class CmsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsWebApplication.class,args);
    }

}