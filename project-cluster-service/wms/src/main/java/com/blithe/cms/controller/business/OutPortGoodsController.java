package com.blithe.cms.controller.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.pojo.business.Inport;
import com.blithe.cms.pojo.business.Outport;
import com.blithe.cms.pojo.business.Provider;
import com.blithe.cms.service.business.GoodsService;
import com.blithe.cms.service.business.OutportService;
import com.blithe.cms.service.business.ProviderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName OutPortGoodsController
 * @Description: 退货
 * @Author: 夏小颜
 * @Date: 11:57
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/outport")
public class OutPortGoodsController {

    @Autowired
    private OutportService outportService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private GoodsService goodsService;


    @RequestMapping("/list")
    public R list (Outport outport){
        Page<Outport> page = new Page<Outport>(outport.getPage(),outport.getLimit(),"outputtime",false);
        Wrapper<Outport> wrapper = new EntityWrapper<Outport>();
        wrapper.eq(outport.getProviderid()!=null&&outport.getProviderid()!=0,"providerid",outport.getProviderid());
        wrapper.eq(outport.getGoodsid()!=null&&outport.getGoodsid()!=0,"goodsid",outport.getGoodsid());
        wrapper.ge(outport.getStartTime()!=null, "outputtime", outport.getStartTime());
        wrapper.le(outport.getEndTime()!=null, "outputtime", outport.getEndTime());
        wrapper.like(StringUtils.isNotBlank(outport.getOperateperson()), "operateperson", outport.getOperateperson());
        outportService.selectPage(page,wrapper);
        List<Outport> records = page.getRecords();
        if(CollectionUtils.isNotEmpty(records)){
            for (Outport record : records) {
                Goods goods = goodsService.selectById(record.getGoodsid());
                if(null!=goods){
                    record.setGoodsname(goods.getGoodsname());
                    record.setSize(goods.getSize());
                }
                Provider provider = providerService.selectById(record.getProviderid());
                if(null!=provider){
                    record.setProvidername(provider.getProvidername());
                }
            }
        }
        return R.ok().put("data",records).put("count",page.getTotal());
    }


    /**
     * 退货
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @RequestMapping("/outportConfirm")
    public R OutportConfirm(Integer id,Integer number,String remark){
        try {
            outportService.addOutPort(id,number,remark);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
        return R.ok();
    }

}