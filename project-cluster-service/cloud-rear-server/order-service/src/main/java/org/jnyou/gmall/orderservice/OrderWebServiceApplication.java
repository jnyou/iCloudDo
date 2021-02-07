package org.jnyou.gmall.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 分布式事务seata：
 * 1、给每一个服务的数据表添加回滚日志表 undo_log
 * 2、安装事务协调器：seata-server：https://github.com/seata/seata/releases
 * 3、导入seata依赖，修改seata配置，启动seata服务
 * 4、所有想用到微分布式事务的微服务使用seata，DataSourceProxy代理自己的数据源
 * 5、每个服务必须导入seata的 file.conf和 registry.conf
 * 6、修改file.conf中的 vgroup_mapping.ware-service-fescar-service-group = "seata-server" 格式：vgroup_mapping.项目名称-fescar-service-group = "seata-server"
 * 7、给分布式大失误的入口标注@GlobalTransactional，每一个远程的小事务用@Transactional
 * 7、启动测试分布式事务
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
