package org.jnyou.gmall.productservice.web;

import org.jnyou.gmall.productservice.service.SkuInfoService;
import org.jnyou.gmall.productservice.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *
 * @className ItemController
 * @author: JnYou xiaojian19970910@gmail.com
 **/
@Controller
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String toItemDescPage(@PathVariable("skuId") Long skuId){
        System.out.println("准备查询" + skuId + "商品的详情信息");
        SkuItemVo skuItemVo = skuInfoService.item(skuId);
        return "item";
    }

}