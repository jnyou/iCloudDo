package com.blithe.cms.controller.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.exception.RbacException;
import com.blithe.cms.pojo.business.Customer;
import com.blithe.cms.service.business.CustomerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CustomerController
 * @Description: 客户管理
 * @Author: 夏小颜
 * @Date: 15:25
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/list")
    public R list(Customer customer){

        Page page = new Page<>(customer.getPage(),customer.getLimit(),"id",false);
        Wrapper wrapper = new EntityWrapper();
        wrapper.like(StringUtils.isNotBlank(customer.getCustomername()),"customername",customer.getCustomername());
        wrapper.like(StringUtils.isNotBlank(customer.getTelephone()),"telephone",customer.getTelephone());
        wrapper.like(StringUtils.isNotBlank(customer.getConnectionperson()),"connectionperson",customer.getConnectionperson());
        customerService.selectPage(page,wrapper);
        return R.ok().put("data",page.getRecords()).put("count",page.getTotal());
    }


    @RequestMapping("/customerSaveOrUpdate")
    public R customerSaveOrUpdate(Customer customer){
        try {
            boolean flag = false;
            if(customer.getId() == null){
                flag = customerService.insert(customer);
            }else {
                flag = customerService.updateById(customer);
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

    @PostMapping("/deleteBatch")
    public R deleteCustomer(@RequestBody List<Map<String ,Object>> params){
        try {
            Boolean flag = false;
            if(CollectionUtils.isNotEmpty(params)){
                for (Map<String, Object> param : params) {
                    flag = customerService.deleteByMap(param);
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

}