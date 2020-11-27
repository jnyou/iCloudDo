package com.blithe.cms.controller.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.exception.RbacException;
import com.blithe.cms.common.tools.HttpContextUtils;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.pojo.business.Inport;
import com.blithe.cms.pojo.business.Provider;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.business.GoodsService;
import com.blithe.cms.service.business.InportService;
import com.blithe.cms.service.business.ProviderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName InportGoodsController
 * @Description: 退货管理
 * @Author: 夏小颜
 * @Date: 16:11
 * @Version: 1.0
 **/
@RequestMapping("/inport")
@RestController
public class InportGoodsController {

    @Autowired
    private InportService inportService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderService providerService;

    @RequestMapping("/list")
    public R list(Inport inport){

        Page<Inport> page = new Page<>(inport.getPage(),inport.getLimit(),"inporttime",false);
        Wrapper<Inport> wrapper = new EntityWrapper<Inport>();
        wrapper.eq(null!=inport.getProviderid()&&inport.getProviderid()!=0,"providerid",inport.getProviderid());
        wrapper.eq(null!=inport.getGoodsid()&&inport.getGoodsid()!=0,"goodsid",inport.getGoodsid());
        wrapper.ge(inport.getStartTime()!=null,"inporttime",inport.getStartTime());
        wrapper.le(inport.getEndTime()!=null,"inporttime",inport.getEndTime());
        wrapper.like(StringUtils.isNotBlank(inport.getRemark()), "remark", inport.getRemark());
        inportService.selectPage(page,wrapper);
        List<Inport> records = page.getRecords();
        if(CollectionUtils.isNotEmpty(records)){
            for (Inport record : records) {
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
     * 删除/批量删除
     * @param params
     * @return
     */
    @PostMapping("/delete")
    public R deleteInport(Integer id){
        boolean flag = false;
        try {
            if(null!=id){
                flag = inportService.deleteById(id);
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
    @RequestMapping("/inportSaveOrUpdate")
    public R inportSaveOrUpdate(Inport inport){
        try {
            if(inport.getId() == null){
                inport.setInporttime(new Date());
                SysUser user = (SysUser) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("user");
                inport.setOperateperson(user.getName());
                inportService.insert(inport);
            }else {
                inportService.updateById(inport);
            }
        }catch (Exception e){
            throw new RbacException(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 根据供应商id查询商品列表
     * @param providerid
     * @return
     */
    @RequestMapping("/loadGoodsByProviderId")
    public R loadGoodsByProviderId(Integer providerid){
        EntityWrapper<Goods> wrapper = new EntityWrapper<Goods>();
        wrapper.eq(providerid!=null,"providerid",providerid);
        List<Goods> goods = goodsService.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(goods)){
            for (Goods good : goods) {
                Provider provider = providerService.selectById(good.getProviderid());
                if(null!=provider){
                    good.setProvidername(provider.getProvidername());
                }
            }
            return R.ok().put("data",goods);
        }else {
            return R.error("该供应商暂无商品列表");
        }
    }

}