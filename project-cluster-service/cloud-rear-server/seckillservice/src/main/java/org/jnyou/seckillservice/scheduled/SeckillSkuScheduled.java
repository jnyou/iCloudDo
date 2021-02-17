package org.jnyou.seckillservice.scheduled;

import org.jnyou.seckillservice.service.SeckillService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillSkuScheduled
 * 秒杀商品定时上架：每天晚上3点，上架最近三天需要秒杀的商品
 * 当天：00:00:00 - 23:59:59
 * 明天：00:00:00 - 23:59:59
 * 后天：00:00:00 - 23:59:59
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Service
public class SeckillSkuScheduled {

    @Autowired
    SeckillService seckillService;
    @Autowired
    RedissonClient redissonClient;
    // 分布式锁
    private final String UPLOAD_LOCK = "seckill:upload:lock";

    @Scheduled(cron = "* * 3 * * ?")
    public void uploadSeckillSkuLatest3Days() {
        // 幂等性处理，分布式定时任务下只需要一台机器上架商品即可，使用分布式锁
        RLock lock = redissonClient.getLock(UPLOAD_LOCK);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            seckillService.uploadSeckillSkuLatest3Days();
        } finally {
            lock.unlock();
        }
    }


}