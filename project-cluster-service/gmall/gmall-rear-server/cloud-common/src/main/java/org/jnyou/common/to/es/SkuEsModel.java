package org.jnyou.common.to.es;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *
 * @ClassName SkuEsModel
 * @Author: JnYou
 **/
@Data
@Accessors(chain = true)
public class SkuEsModel {

    /**
     * sku基本信息
     * @Author JnYou
     */
    private Long skuId;
    private Long spuId;
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;

    /**
     * 其他信息
     * @Author JnYou
     */
    private Long saleCount;
    private boolean hasStock;
    private Long hotScore;
    private Long brandId;
    private Long catalogId;
    /**
     * 冗余字段
     * @Author JnYou
     */
    private String brandName;
    private String brandImg;
    private String catalogName;
    private List<Attrs> attrs;

    @Data
    public static class Attrs{
        private Long attrId;
        private String attrName;
        private String attrValue;
    }

}