package org.jnyou.service.templete;

import org.apache.poi.ss.formula.functions.T;
import org.jnyou.constant.Constants;
import org.jnyou.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CacheTemplete
 * @Description: 使用函数式接口来实现针对数据库的操作，只关注取得数据，不关注数据得操作过程
 * @Author: jnyou
 **/
@Component
public class CacheTemplete {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T loadCache(String key,long expire,BaseServiceHandle<T> service){
        // 避免缓存击穿
        T result = (T) redisTemplate.opsForValue().get(key);
        if(null == result){
            // 1000线程
            synchronized (this){
                result = (T) redisTemplate.opsForValue().get(key);
                if(null == result){
                    // 查询数据库
                    result = service.loadData();
                    redisTemplate.opsForValue().set(key,result,expire, TimeUnit.MINUTES);
                }
            }
        }
        return result;
    }



}