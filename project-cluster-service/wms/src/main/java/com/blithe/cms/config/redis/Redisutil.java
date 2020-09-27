package com.blithe.cms.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @ClassName redisutil
 * @Description: redis事务工具类
 * @Author: 夏小颜
 * @Date: 12:25
 * @Version: 1.0
 **/
public class Redisutil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 开启Redis 事务
     *
     * @param isTransaction
     */
    public void begin() {
        // 开启Redis 事务权限
        stringRedisTemplate.setEnableTransactionSupport(true);
        // 开启事务
        stringRedisTemplate.multi();
    }
    /**
     * 提交事务
     *
     * @param isTransaction
     */
    public void exec() {
        // 成功提交事务
        stringRedisTemplate.exec();
    }
    /**
     * 回滚Redis 事务
     */
    public void discard() {
        stringRedisTemplate.discard();
    }

}

