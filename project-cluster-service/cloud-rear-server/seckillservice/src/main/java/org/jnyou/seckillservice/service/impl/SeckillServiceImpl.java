package org.jnyou.seckillservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.common.utils.R;
import org.jnyou.seckillservice.feign.CouponFeignClient;
import org.jnyou.seckillservice.feign.ProductFeignClient;
import org.jnyou.seckillservice.service.SeckillService;
import org.jnyou.seckillservice.to.SeckillSkuRedisTo;
import org.jnyou.seckillservice.vo.SeckillSessionsWithSkus;
import org.jnyou.seckillservice.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillServiceImpl
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    CouponFeignClient couponFeignClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ProductFeignClient productFeignClient;
    @Autowired
    RedissonClient redissonClient;

    private final String SESSION_CACHE_PREFIX = "seckill:session:";
    private final String SKUKILL_CACHE_PREFIX = "seckill:skus:";
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:"; // 后加商品随机码

    @Override
    public void uploadSeckillSkuLatest3Days() {
        log.info("上架秒杀商品开始...");
        // 扫描需要参与的秒杀活动
        R r = couponFeignClient.getLatest3DaysSession();
        if (r.getCode() == 0) {
            // 上架商品
            List<SeckillSessionsWithSkus> data = r.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {
            });
            // 缓存到redis中
            // 1、缓存活动信息
            saveSessionInfos(data);
            // 2、缓存活动关联的商品信息
            saveSessionSkuInfos(data);
        }
    }

    private void saveSessionInfos(List<SeckillSessionsWithSkus> sessions) {
        sessions.forEach(session -> {
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SESSION_CACHE_PREFIX + startTime + "_" + endTime;
            if (!stringRedisTemplate.hasKey(key)) {
                // 缓存活动信息
                List<String> skuIds = session.getEntities().stream().map(item -> item.getPromotionSessionId() + "_" + item.getSkuId()).collect(Collectors.toList());
                stringRedisTemplate.opsForList().leftPushAll(key, skuIds);
            }
        });
    }

    private void saveSessionSkuInfos(List<SeckillSessionsWithSkus> sessions) {

        sessions.stream().forEach(session -> {
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
            session.getEntities().stream().forEach(skuInfo -> {
                // 秒杀随机码，防止恶意侵入
                String token = UUID.randomUUID().toString().replace("-", "");

                // 判断保证接口幂等
                if (!hashOps.hasKey(skuInfo.getPromotionSessionId() + "_" + skuInfo.getSkuId())) {
                    // 缓存活动商品信息
                    SeckillSkuRedisTo seckillSkuRedisTo = new SeckillSkuRedisTo();

                    // 1、sku的基本信息
                    R r = productFeignClient.getSkuInfo(skuInfo.getSkuId());
                    if (r.getCode() == 0) {
                        SkuInfoVo skuInfoVo = r.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                        });
                        seckillSkuRedisTo.setSkuInfoVo(skuInfoVo);
                    }

                    // 2、sku的秒杀信息
                    BeanUtils.copyProperties(skuInfo, seckillSkuRedisTo);

                    // 3、秒杀商品的开始时间和结束时间
                    seckillSkuRedisTo.setStartTime(session.getStartTime().getTime());
                    seckillSkuRedisTo.setEndTime(session.getEndTime().getTime());

                    String json = JSON.toJSONString(seckillSkuRedisTo);
                    hashOps.put(skuInfo.getPromotionSessionId() + "_" + skuInfo.getSkuId(), json);

                    // 4、redisson分布式信号量减库存
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                    semaphore.trySetPermits(skuInfo.getSeckillCount());
                }
            });
        });
    }


}