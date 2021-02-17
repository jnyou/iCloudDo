package org.jnyou.seckillservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * <p>
 *
 * @className MyRedissonConfig
 * @author: JnYou xiaojian19970910@gmail.com
 **/
@Configuration
public class MyRedissonConfig {

    /**
     * 所有对Redisson的使用都是通过RedissonClient对象
     * @Author JnYou
     */
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        // 创建配置
        Config config = new Config();
//        config.useClusterServers()
//                .addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
        // 单Redis节点模式
        config.useSingleServer().setAddress("redis://192.168.56.10:6379"); // Redis url should start with redis:// or rediss:// (for SSL connection)
        // 根据config构建RedissonClient实例
        return Redisson.create(config);
    }


}