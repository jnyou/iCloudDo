package org.jnyou.gmall.orderservice.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * @ClassName SkuHasStock
 * @Author: JnYou
 **/
@Data
@Accessors(chain = true)
public class SkuStockVo {

    private Long skuId;

    private Boolean hasStock;

}