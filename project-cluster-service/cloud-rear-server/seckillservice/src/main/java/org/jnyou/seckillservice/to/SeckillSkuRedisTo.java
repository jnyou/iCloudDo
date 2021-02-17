package org.jnyou.seckillservice.to;

import lombok.Data;
import org.jnyou.seckillservice.vo.SkuInfoVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillSkuRedisToi
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class SeckillSkuRedisTo {

    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private BigDecimal seckillCount;
    /**
     * 每人限购数量
     */
    private BigDecimal seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    /**
     * sku的详细信息
     */
    private SkuInfoVo skuInfoVo;

    /**
     * 当前商品秒杀的开始时间
     */
    private Long startTime;
    /**
     * 当前商品秒杀的结束时间
     */
    private Long endTime;

    /**
     * 秒杀随机码
     */
    private String randomCode;

}