package org.jnyou.gmall.productservice.web;

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

    @GetMapping("/{skuId}.html")
    public String toItemDescPage(Long skuId){
        System.out.println("准备查询" + skuId + "商品的详情信息");
        return "item";
    }

}