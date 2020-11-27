package com.blithe.cms.common.tools;

import com.guoyin.amtp.tools.redis.JedisUtil;
import org.apache.log4j.Logger;

/**
 * Description  根据IP限制给定时间内允许访问的次数
 * Created by crazy
 * Created on 2018/11/20.
 */
public class CountReqByIpUtil {
    private static Logger logger = Logger.getLogger(CountReqByIpUtil.class);
    public static boolean countReqByIp(String key){
        int max = 100;//最大允许max个请求
        long total = 1L;//本次请求次数
        int expireTime = 10;//key有效期
        try {
            if (JedisUtil.get(key) == null) {
                //如果redis目前没有这个key，创建并赋予1，有效时间为expireTime秒
                JedisUtil.setex(key, expireTime, "1");
            } else {
                //获取加1后的值
                total = JedisUtil.incr(key).longValue();
                //Redis TTL命令以秒为单位返回key的剩余过期时间。当key不存在时，返回-2。当key存在但没有设置剩余生存时间时，返回-1。否则，以秒为单位，返回key的剩余生存时间。
                if (JedisUtil.ttl(key).longValue() == -1L){
                    //为给定key设置生存时间，当key过期时(生存时间为0)，它会被自动删除。
                    JedisUtil.expire(key, expireTime);
                }
            }
        } catch (Exception e) {
            logger.error("执行计数操作失败,无法执行计数");
        }
        long keytotaltransactions = max;
        //判断是否已超过最大值，超过则返回false
        if (total > keytotaltransactions) {
            return false;
        }
        return true;
    }
}
