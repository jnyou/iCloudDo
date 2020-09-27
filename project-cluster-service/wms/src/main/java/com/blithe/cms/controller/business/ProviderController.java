package com.blithe.cms.controller.business;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.exception.RbacException;
import com.blithe.cms.pojo.business.Customer;
import com.blithe.cms.pojo.business.Provider;
import com.blithe.cms.service.business.ProviderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ProviderController
 * @Description: 供应商管理
 * @Author: 夏小颜
 * @Date: 9:37
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @RequestMapping("/list")
    public R list(Provider provider){

        Page page = new Page<>(provider.getPage(),provider.getLimit(),"id",false);
        Wrapper wrapper = new EntityWrapper();
        wrapper.like(StringUtils.isNotBlank(provider.getProvidername()),"providername",provider.getProvidername());
        wrapper.like(StringUtils.isNotBlank(provider.getTelephone()),"telephone",provider.getTelephone());
        wrapper.like(StringUtils.isNotBlank(provider.getConnectionperson()),"connectionperson",provider.getConnectionperson());
        providerService.selectPage(page,wrapper);
        return R.ok().put("data",page.getRecords()).put("count",page.getTotal());
    }


    @RequestMapping("/providerSaveOrUpdate")
    public R providerSaveOrUpdate(Provider provider){
        try {
            boolean flag = false;
            if(provider.getId() == null){
                flag = providerService.insert(provider);
            }else {
                flag = providerService.updateById(provider);
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
    public R deleteProdiver(@RequestBody List<Map<String ,Object>> params){
        Boolean flag = false;
        try {
            if(CollectionUtils.isNotEmpty(params)){
                for (Map<String, Object> param : params) {
                    flag = providerService.deleteByMap(param);
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

    @GetMapping("/loadSelectProviderName")
    public R loadSelectProviderName(){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("available",1);
        List<Provider> list = providerService.selectList(entityWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            return R.ok().put("data",list);
        }else {
            return R.error("无供应商列表");
        }
    }

}