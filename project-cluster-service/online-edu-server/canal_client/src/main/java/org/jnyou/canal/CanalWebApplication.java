package org.jnyou.canal;

import org.jnyou.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * 分类名称
 *
 * @ClassName CanalWebApplication
 * @Description: canal启动器
 * @Author: jnyou
 * @create 2020/08/23
 * @module 智慧园区
 **/
@SpringBootApplication
public class CanalWebApplication implements CommandLineRunner {

    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //项目启动，执行canal客户端监听
        canalClient.run();
    }
}