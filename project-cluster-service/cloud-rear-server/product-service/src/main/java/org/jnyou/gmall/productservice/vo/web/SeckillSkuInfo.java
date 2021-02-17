package org.jnyou.gmall.productservice.vo.web;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillSkuInfo
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class SeckillSkuInfo {

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