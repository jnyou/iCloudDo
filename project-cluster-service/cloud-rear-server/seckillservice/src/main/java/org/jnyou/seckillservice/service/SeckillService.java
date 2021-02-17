package org.jnyou.seckillservice.service;

import org.jnyou.seckillservice.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillService
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public interface SeckillService {
    void uploadSeckillSkuLatest3Days();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();
}