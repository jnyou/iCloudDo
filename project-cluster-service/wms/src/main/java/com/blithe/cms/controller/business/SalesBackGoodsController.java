package com.blithe.cms.controller.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.pojo.business.Customer;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.pojo.business.Salesback;
import com.blithe.cms.service.business.CustomerService;
import com.blithe.cms.service.business.GoodsService;
import com.blithe.cms.service.business.SalesbackService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SalesGoodsController
 * @Description: 商品销售管理
 * @Author: 夏小颜
 * @Date: 16:11
 * @Version: 1.0
 **/
@RequestMapping("/salesback")
@RestController
public class SalesBackGoodsController {

    @Autowired
    private SalesbackService salesbackService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/list")
    public R list(Salesback salesback){
        Page<Salesback> page = new Page<>(salesback.getPage(),salesback.getLimit(),"salesbacktime",false);
        Wrapper<Salesback> wrapper = new EntityWrapper<Salesback>();
        wrapper.eq(null!=salesback.getCustomerid()&&salesback.getCustomerid()!=0,"customerid",salesback.getCustomerid());
        wrapper.eq(null!=salesback.getGoodsid()&&salesback.getGoodsid()!=0,"goodsid",salesback.getGoodsid());
        wrapper.ge(salesback.getStartTime()!=null,"salesbacktime",salesback.getStartTime());
        wrapper.le(salesback.getEndTime()!=null,"salesbacktime",salesback.getEndTime());
        wrapper.like(StringUtils.isNotBlank(salesback.getOperateperson()), "operateperson", salesback.getOperateperson());
        wrapper.like(StringUtils.isNotBlank(salesback.getPaytype()), "paytype", salesback.getPaytype());
        salesbackService.selectPage(page,wrapper);
        List<Salesback> records = page.getRecords();
        if(CollectionUtils.isNotEmpty(records)){
            for (Salesback record : records) {
                Goods goods = goodsService.selectById(record.getGoodsid());
                if(null!=goods){
                    record.setGoodsname(goods.getGoodsname());
                    record.setSize(goods.getSize());
                }
                Customer customer = customerService.selectById(record.getCustomerid());
                if(null!=customer){
                    record.setCustomername(customer.getCustomername());
                }
            }
        }
        return R.ok().put("data",records).put("count",page.getTotal());
    }


    /**
     * 销售退货
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @RequestMapping("/salesBackConfirm")
    public R OutportConfirm(Integer id,Integer number,String remark){
        try {
            salesbackService.salesConfirm(id,number,remark);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
        return R.ok();
    }
}