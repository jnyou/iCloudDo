package org.jnyou.service.templete.impl;

import org.jnyou.constant.Constants;
import org.jnyou.entity.User;
import org.jnyou.mapper.UserMapper;
import org.jnyou.service.templete.CacheTemplete;
import org.jnyou.service.templete.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description:
 * @Author: jnyou
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheTemplete cacheTemplete;

    @Override
    public User getUserById(Integer id) {
        // 避免缓存击穿
        User user = (User) redisTemplate.opsForValue().get(Constants.USER_KEY + id);
        if(null != user){
            // 1000
            synchronized (this){
                user = (User) redisTemplate.opsForValue().get(Constants.USER_KEY + id);
                if(null != user){
                    user = userMapper.selectById(id);
                    redisTemplate.opsForValue().set(Constants.USER_KEY + id,user);
                }
            }
        }
        return user;
    }

    /**
     * 模板方法模式实现
     * @param id
     * @return
     */
    @Override
    public User getUserByIdTemplate(Integer id) {
        User user = cacheTemplete.loadCache(Constants.USER_KEY + id,10,() -> {
            return userMapper.selectById(id);
        });
        return user;
    }
}