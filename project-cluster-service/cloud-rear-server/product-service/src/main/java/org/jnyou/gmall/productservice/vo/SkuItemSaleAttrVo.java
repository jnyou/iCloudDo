package org.jnyou.gmall.productservice.vo;

import lombok.Data;

import java.util.List;

/**
 * @author jnyou
 */
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}