package org.jnyou.gmall.productservice.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分类名称
 *
 * @ClassName BrandVo
 * @Description:
 * @Author: jnyou
 **/
@Data
@Accessors(chain = true)
public class BrandVo {

    private Long brandId;

    private String brandName;

}