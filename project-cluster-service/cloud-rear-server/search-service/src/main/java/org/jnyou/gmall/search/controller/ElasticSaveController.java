package org.jnyou.gmall.search.controller;

import lombok.extern.slf4j.Slf4j;
import org.jnyou.common.exception.BizCodeEnume;
import org.jnyou.common.to.es.SkuEsModel;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.search.service.ElasticSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * @ClassName ElasticSaveController
 * @Author: JnYou
 **/
@Slf4j
@RestController
@RequestMapping("search")
public class ElasticSaveController {

    @Autowired
    private ElasticSaveService elasticSaveService;

    /**
     * 上架商品
     *
     * @param skuEsModels
     * @Author JnYou
     */
    @PostMapping("product/save")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean b = false;
        try {
            b = elasticSaveService.productUp(skuEsModels);
        } catch (Exception e) {
            log.error("ElasticSearch上架商品失败；{}" + e);
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if (!b) {
            return R.ok();
        } else {
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }
    }

}