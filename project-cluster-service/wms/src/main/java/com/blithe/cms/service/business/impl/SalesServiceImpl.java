package com.blithe.cms.service.business.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.common.tools.HttpContextUtils;
import com.blithe.cms.mapper.business.GoodsMapper;
import com.blithe.cms.mapper.business.SalesMapper;
import com.blithe.cms.pojo.business.*;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.business.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 夏小颜
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

    @Resource
    private SalesMapper salesMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean insert(Sales entity) {
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber() - entity.getNumber());
        goodsMapper.updateById(goods);
        return super.insert(entity);
    }

    @Override
    public boolean updateById(Sales entity) {
        Sales sales = salesMapper.selectById(entity.getId());
        // 库存修改
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber() - sales.getNumber() + entity.getNumber());
        goodsMapper.updateById(goods);
        return super.updateById(entity);
    }

    @Override
    public boolean deleteById(Serializable id) {
        Sales sales = salesMapper.selectById(id);
        Goods goods = goodsMapper.selectById(sales.getGoodsid());
        goods.setNumber(goods.getNumber() + sales.getNumber());
        goodsMapper.updateById(goods);
        return super.deleteById(id);
    }
}
