package com.blithe.cms.service.business.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.common.tools.HttpContextUtils;
import com.blithe.cms.mapper.business.GoodsMapper;
import com.blithe.cms.mapper.business.SalesMapper;
import com.blithe.cms.mapper.business.SalesbackMapper;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.pojo.business.Sales;
import com.blithe.cms.pojo.business.Salesback;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.business.SalesbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 夏小颜
 */
@Service
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements SalesbackService {

    @Resource
    private SalesbackMapper salesbackMapper;

    @Resource
    private SalesMapper salesMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void salesConfirm(Integer id, Integer number, String remark) {
        //1,根据销售单ID查询销售单信息
        Sales sales = salesMapper.selectById(id);
        sales.setNumber(sales.getNumber() - number);
        salesMapper.updateById(sales);
        //2,根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(sales.getGoodsid());
        goods.setNumber(goods.getNumber()+number);
        this.goodsMapper.updateById(goods);
        //3,添加销售退货单信息
        Salesback entity = new Salesback();
        entity.setGoodsid(sales.getGoodsid());
        entity.setNumber(number);
        SysUser user=(SysUser) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("user");
        entity.setOperateperson(user.getName());
        entity.setSalebackprice(new Double(String.valueOf(sales.getSaleprice())));
        entity.setSalesbacktime(new Date());
        entity.setPaytype(sales.getPaytype());
        entity.setCustomerid(sales.getCustomerid());
        entity.setRemark(remark);
        salesbackMapper.insert(entity);
    }

}
