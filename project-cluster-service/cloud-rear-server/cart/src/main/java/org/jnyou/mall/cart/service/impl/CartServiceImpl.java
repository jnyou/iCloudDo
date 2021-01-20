package org.jnyou.mall.cart.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jnyou.mall.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * CartServiceImpl
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    StringRedisTemplate redisTemplate;


}