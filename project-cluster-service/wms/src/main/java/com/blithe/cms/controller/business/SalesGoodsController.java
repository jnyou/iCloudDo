package com.blithe.cms.controller.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.exception.RbacException;
import com.blithe.cms.common.utils.HttpContextUtils;
import com.blithe.cms.pojo.business.Customer;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.pojo.business.Sales;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.business.CustomerService;
import com.blithe.cms.service.business.GoodsService;
import com.blithe.cms.service.business.SalesService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SalesGoodsController
 * @Description: 商品销售管理
 * @Author: 夏小颜
 * @Date: 16:11
 * @Version: 1.0
 **/
@RequestMapping("/sales")
@RestController
public class SalesGoodsController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/list")
    public R list(Sales sales){
        Page<Sales> page = new Page<>(sales.getPage(),sales.getLimit(),"salestime",false);
        Wrapper<Sales> wrapper = new EntityWrapper<Sales>();
        wrapper.eq(null!=sales.getCustomerid()&&sales.getCustomerid()!=0,"customerid",sales.getCustomerid());
        wrapper.eq(null!=sales.getGoodsid()&&sales.getGoodsid()!=0,"goodsid",sales.getGoodsid());
        wrapper.ge(sales.getStartTime()!=null,"salestime",sales.getStartTime());
        wrapper.le(sales.getEndTime()!=null,"salestime",sales.getEndTime());
        wrapper.like(StringUtils.isNotBlank(sales.getOperateperson()), "operateperson", sales.getOperateperson());
        wrapper.like(StringUtils.isNotBlank(sales.getPaytype()), "paytype", sales.getPaytype());
        salesService.selectPage(page,wrapper);
        List<Sales> records = page.getRecords();
        if(CollectionUtils.isNotEmpty(records)){
            for (Sales record : records) {
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


    @RequestMapping("/loadSelectSalesName")
    public R loadSelectSalesName(){
        EntityWrapper<Customer> wrapper = new EntityWrapper<Customer>();
        wrapper.setSqlSelect("id","customername");
        List<Customer> customers = customerService.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(customers)){
            return R.ok().put("data",customers);
        }else{
            return R.error("无可用的商品顾客");
        }
    }


    /**
     * 删除/批量删除
     * @param params
     * @return
     */
    @PostMapping("/delete")
    public R deleteSales(Integer id){
        boolean flag = false;
        try {
            if(null!=id){
                flag = salesService.deleteById(id);
            }
            if(flag){
                return R.ok("删除成功");
            }else {
                return R.error("删除失败");
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
    @RequestMapping("/salesSaveOrUpdate")
    public R salesSaveOrUpdate(Sales sales){
        try {
            if(null == sales.getId()){
                sales.setSalestime(new Date());
                SysUser user = (SysUser) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("user");
                sales.setOperateperson(user.getName());
                salesService.insert(sales);
            }else {
                salesService.updateById(sales);
            }
        }catch (Exception e){
            throw new RbacException(e.getMessage());
        }
        return R.ok();
    }
}