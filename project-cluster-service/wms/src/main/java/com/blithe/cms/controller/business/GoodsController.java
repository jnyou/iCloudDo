package com.blithe.cms.controller.business;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.exception.RbacException;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.pojo.business.Provider;
import com.blithe.cms.service.business.GoodsService;
import com.blithe.cms.service.business.ProviderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName GoodsController
 * @Description: 商品
 * @Author: 夏小颜
 * @Date: 10:16
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderService providerService;

    @RequestMapping("/list")
    public R list(Goods goods){

        Page page = new Page<>(goods.getPage(),goods.getLimit(),"id",false);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq(null!=goods.getProviderid()&&goods.getProviderid()!=0,"providerid",goods.getProviderid());
        wrapper.like(StringUtils.isNotBlank(goods.getGoodsname()),"goodsname",goods.getGoodsname());
        wrapper.like(StringUtils.isNotBlank(goods.getProduceplace()),"produceplace",goods.getProduceplace());
        wrapper.like(StringUtils.isNotBlank(goods.getProductcode()),"producecode",goods.getProductcode());
        wrapper.like(StringUtils.isNotBlank(goods.getPromitcode()),"promitcode",goods.getPromitcode());
        wrapper.like(StringUtils.isNotBlank(goods.getSize()),"size",goods.getSize());
        goodsService.selectPage(page,wrapper);
        List<Goods> goodsList = page.getRecords();
        for (Goods goods1 : goodsList) {
            Provider provider = providerService.selectById(goods1.getProviderid());
            if(null!= provider){
                goods1.setProvidername(provider.getProvidername());
            }
        }
        return R.ok().put("data",goodsList).put("count",page.getTotal());
    }


    /**
     * 删除/批量删除
     * @param params
     * @return
     */
    @PostMapping("/deleteBatch")
    public R deleteCustomer(@RequestBody List<Map<String ,Object>> params){
        Boolean flag = false;
        try {
            if(CollectionUtils.isNotEmpty(params)){
                for (Map<String, Object> param : params) {
                    flag = goodsService.deleteByMap(param);
                }
            }
            if(flag){
                return R.ok();
            }else {
                return R.error();
            }
        }catch (Exception e){
            throw new RbacException(e.getMessage());
        }
    }


    /**
     * 保存
     * @param provider
     * @return
     */
    @RequestMapping("/goodsSaveOrUpdate")
    public R goodsSaveOrUpdate(Goods goods){
        boolean flag = false;
        try {
            if(goods.getId() == null){
                flag = goodsService.insert(goods);
            }else {
                flag = goodsService.updateById(goods);
            }
            if(flag){
                return R.ok();
            }else {
                return R.error();
            }
        }catch (Exception e){
            throw new RbacException(e.getMessage());
        }
    }


    @GetMapping("/loadAllGoodsName")
    public R loadAllGoodsName(){
        EntityWrapper<Goods> entityWrapper = new EntityWrapper<Goods>();
        entityWrapper.eq("available",1);
        List<Goods> list = goodsService.selectList(entityWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            return R.ok().put("data",list);
        }else {
            return R.error("无商品列表");
        }
    }





}