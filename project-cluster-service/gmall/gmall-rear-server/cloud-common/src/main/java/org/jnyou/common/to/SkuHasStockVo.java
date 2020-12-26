package org.jnyou.common.to;

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
public class SkuHasStockVo {

    private Long skuId;

    private Boolean hasStock;

}