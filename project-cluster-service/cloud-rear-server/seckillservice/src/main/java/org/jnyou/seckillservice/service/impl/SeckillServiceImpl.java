package org.jnyou.seckillservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.common.to.mq.SeckillOrderTo;
import org.jnyou.common.utils.R;
import org.jnyou.common.vo.MemberResponseVo;
import org.jnyou.seckillservice.feign.CouponFeignClient;
import org.jnyou.seckillservice.feign.ProductFeignClient;
import org.jnyou.seckillservice.interceptor.LoginUserInterceptor;
import org.jnyou.seckillservice.service.SeckillService;
import org.jnyou.seckillservice.to.SeckillSkuRedisTo;
import org.jnyou.seckillservice.vo.SeckillSessionsWithSkus;
import org.jnyou.seckillservice.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
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
    @Autowired
    RabbitTemplate rabbitTemplate;

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
        log.info("上架秒杀商品结束...");
    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        // 1、确定当前时间属于哪个秒杀场次
        long time = new Date().getTime();
        // 从redis中获取到所有的场次信息  格式：seckill:session:1613520000000_1613520600000
        Set<String> keys = stringRedisTemplate.keys(SESSION_CACHE_PREFIX + "*");
        for (String key : keys) {
            String replace = key.replace(SESSION_CACHE_PREFIX, "");
            String[] s = replace.split("_");
            long start = Long.parseLong(s[0]);
            long end = Long.parseLong(s[1]);
            if (time >= start && time <= end) {
                // 2、获取这个秒杀场次的所有商品信息
                List<String> range = stringRedisTemplate.opsForList().range(key, -100, 100);
                BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                List<String> objects = hashOps.multiGet(range);
                if (CollectionUtils.isNotEmpty(objects)) {
                    List<SeckillSkuRedisTo> collect = objects.stream().map(item -> {
                        SeckillSkuRedisTo seckillSkuRedisTo = JSON.parseObject((String) item, SeckillSkuRedisTo.class);
                        return seckillSkuRedisTo;
                    }).collect(Collectors.toList());
                    return collect;
                }
                break;
            }
        }
        return null;
    }

    @Override
    public SeckillSkuRedisTo getCurrentSeckillInfo(Long skuId) {
        // 找到所有需要参与秒杀的商品key
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);

        Set<String> keys = hashOps.keys();
        if (CollectionUtils.isNotEmpty(keys)) {
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                boolean matches = Pattern.matches(regx, key);
                if (matches) {
                    String json = hashOps.get(key);
                    SeckillSkuRedisTo seckillSkuRedisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);

                    // 随机码处理
                    long time = new Date().getTime();
                    if (time >= seckillSkuRedisTo.getStartTime() && time <= seckillSkuRedisTo.getEndTime()) {

                    } else {
                        seckillSkuRedisTo.setRandomCode(null);
                    }
                    return seckillSkuRedisTo;
                }
            }
        }

        return null;
    }

    /**
     * 秒杀业务
     * TODO 上架秒杀的商品，每一个数据都应该有过期时间
     * TODO 秒杀的后袖流程，简化了收货地址
     *
     * @Author JnYou
     */
    @Override
    public String kill(String killId, String key, Integer num) {
        long s = System.currentTimeMillis();
        MemberResponseVo memberResponseVo = LoginUserInterceptor.loginUser.get();

        // 判断是否登录  拦截器执行
        // 合法性校验
        // 1、获取当前秒杀商品的详细信息
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        // http://seckill.gmall.com/kill?killId=4_10&key=undefined&num=1
        String str = hashOps.get(killId);
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            SeckillSkuRedisTo redis = JSON.parseObject(str, SeckillSkuRedisTo.class);
            // 合法性校验
            // 1.1、校验时间的合法性
            Long startTime = redis.getStartTime();
            Long endTime = redis.getEndTime();
            long time = new Date().getTime();
            long ttl = endTime - time;
            if (time >= startTime && time <= endTime) {
                // 1.2、校验随机码和商品id
                String randomCode = redis.getRandomCode();
                Long skuId = redis.getSkuId();
                String bizKillId = redis.getPromotionSessionId() + "_" + redis.getSkuId();
                if (randomCode.equals(key) && bizKillId.equals(killId)) {
                    // 1.3 验证购物数量
                    if (num <= redis.getSeckillLimit()) {
                        // 1.4、验证是否购买过；幂等性处理：如果只要秒杀成功，就去占位。userId_sessionId_skuId
                        String redisKey = memberResponseVo.getId() + "_" + bizKillId;
                        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                        if (aBoolean) {
                            // 占位成功，当前用户从来没有买过
                            RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                            // 尝试从信号量中取出i个
                            boolean b = semaphore.tryAcquire(num);
                            if (b) {
                                // 秒杀成功，发送MQ消息给订单服务，快速下单
                                // 返回订单号
                                String orderSn = IdWorker.getTimeId();
                                SeckillOrderTo seckillOrderTo = new SeckillOrderTo()
                                        .setOrderSn(orderSn)
                                        .setNum(num)
                                        .setSkuId(redis.getSkuId())
                                        .setPromotionSessionId(redis.getPromotionSessionId())
                                        .setMemberId(memberResponseVo.getId())
                                        .setSeckillPrice(redis.getSeckillPrice());
                                rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", seckillOrderTo);
                                long e = System.currentTimeMillis();
                                log.info("秒杀成功。。。耗时{}毫秒", e - s);
                                return orderSn;
                            }
                            return null;
                        } else {
                            // 说明已经买过了
                            return null;
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        return null;
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
        if (CollectionUtils.isNotEmpty(sessions)) {
            sessions.stream().forEach(session -> {
                BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                session.getEntities().stream().forEach(skuInfo -> {
                    // 秒杀随机码，防止恶意侵入
                    String token = UUID.randomUUID().toString().replace("-", "");

                    // 判断保证接口幂等
                    if (!hashOps.hasKey(skuInfo.getPromotionSessionId() + "_" + skuInfo.getSkuId())) {
                        // 缓存活动商品信息
                        SeckillSkuRedisTo seckillSkuRedisTo = new SeckillSkuRedisTo();
                        seckillSkuRedisTo.setRandomCode(token);
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


}