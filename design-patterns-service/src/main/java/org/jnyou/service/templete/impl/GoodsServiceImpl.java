package org.jnyou.service.templete.impl;

import org.jnyou.constant.Constants;
import org.jnyou.entity.Goods;
import org.jnyou.entity.User;
import org.jnyou.mapper.GoodsMapper;
import org.jnyou.service.templete.CacheTemplete;
import org.jnyou.service.templete.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @ClassName GoodsServiceImpl
 * @Description:
 * @Author: jnyou
 **/
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private CacheTemplete cacheTemplete;

    @Override
    public Goods getGoodsById(Integer id) {
        // 避免缓存击穿
        Goods goods = (Goods) redisTemplate.opsForValue().get(Constants.GOODS_KEY + id);
        if(null == goods){
            // 1000
            synchronized (this){
                goods = (Goods) redisTemplate.opsForValue().get(Constants.GOODS_KEY + id);
                if(null == goods){
                    goods = goodsMapper.selectById(id);
                    redisTemplate.opsForValue().set(Constants.GOODS_KEY + id,goods);
                }
            }
        }
        return goods;
    }

    /**
     * 模板方法模式实现
     * @param id
     * @return
     */
    @Override
    public Goods getGoodsByIdTemplate(Integer id) {
        Goods goods = cacheTemplete.loadCache(Constants.USER_KEY + id,10,() -> {
            return goodsMapper.selectById(id);
        });
        return goods;
    }
}