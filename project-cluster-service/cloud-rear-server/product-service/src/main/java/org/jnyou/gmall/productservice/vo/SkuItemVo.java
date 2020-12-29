package org.jnyou.gmall.productservice.vo;


import lombok.Data;
import lombok.ToString;
import org.jnyou.gmall.productservice.entity.SkuImagesEntity;
import org.jnyou.gmall.productservice.entity.SkuInfoEntity;
import org.jnyou.gmall.productservice.entity.SpuInfoDescEntity;

import java.util.List;

@Data
public class SkuItemVo {
    /**
     * 1.sku基本信息获取pms_sku_info
     */
    SkuInfoEntity info;

    /**
     * 是否有货
     */
    boolean hasStock = true;

    /**
     * 2.sku的图片信息 pms_sku_imgs
     */
    List<SkuImagesEntity> images;

    /**
     * 3.获取spu的销售属性组合
     */
    List<SkuItemSaleAttrVo> saleAttr;

    /**
     * 4.获取spu的介绍
     */
    SpuInfoDescEntity desp;

    /**
     * 5.获取spu的规格参数信息
     */
    List<SpuItemAttrGroupVo> groupAttrs;

}
