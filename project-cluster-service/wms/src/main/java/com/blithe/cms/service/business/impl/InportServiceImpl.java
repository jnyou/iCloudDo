package com.blithe.cms.service.business.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.mapper.business.GoodsMapper;
import com.blithe.cms.mapper.business.InportMapper;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.pojo.business.Inport;
import com.blithe.cms.service.business.InportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional(rollbackFor = Exception.class)
/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/4/3
 * @Param:
 * @Return:
 **/
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private InportMapper inportMapper;
	
	@Override
	public boolean insert(Inport entity) {
		//根据商品编号查询商品
		Goods goods=goodsMapper.selectById(entity.getGoodsid());
		goods.setNumber(goods.getNumber()+entity.getNumber());
		goodsMapper.updateById(goods);
		//保存进货信息
		return super.insert(entity);
	}
	
	@Override
	public boolean updateById(Inport entity) {
		//根据进货ID查询进货
		Inport inport = inportMapper.selectById(entity.getId());
		//根据商品ID查询商品信息
		Goods goods = goodsMapper.selectById(entity.getGoodsid());
		//库存的算法  当前库存-进货单修改之前的数量+修改之后的数量
		goods.setNumber(goods.getNumber()-inport.getNumber()+entity.getNumber());
		this.goodsMapper.updateById(goods);
		//更新进货单
		return super.updateById(entity);
	}
	
	
	
	@Override
	public boolean deleteById(Serializable id) {
		//根据进货ID查询进货
		Inport inport = inportMapper.selectById(id);
		//根据商品ID查询商品信息
		Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
		//库存的算法  当前库存-进货单数量
		goods.setNumber(goods.getNumber()-inport.getNumber());
		this.goodsMapper.updateById(goods);
		return super.deleteById(id);
	}
	
	
	
	
}
