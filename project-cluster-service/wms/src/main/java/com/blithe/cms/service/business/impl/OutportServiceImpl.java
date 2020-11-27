package com.blithe.cms.service.business.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.common.utils.HttpContextUtils;
import com.blithe.cms.mapper.business.GoodsMapper;
import com.blithe.cms.mapper.business.InportMapper;
import com.blithe.cms.mapper.business.OutportMapper;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.pojo.business.Inport;
import com.blithe.cms.pojo.business.Outport;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.business.OutportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/4/3
 * @Param:
 * @Return:
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService {

	@Autowired
	private InportMapper inportMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private OutportMapper outportMapper;

	@Override
	public void addOutPort(Integer id, Integer number, String remark) {
		//1,根据进货单ID查询进货单信息
		Inport inport = this.inportMapper.selectById(id);
		//2,根据商品ID查询商品信息
		Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
		goods.setNumber(goods.getNumber()-number);
		this.goodsMapper.updateById(goods);
		//3,添加退货单信息
		Outport entity=new Outport();
		entity.setGoodsid(inport.getGoodsid());
		entity.setNumber(number);
		SysUser user=(SysUser) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("user");
		entity.setOperateperson(user.getName());
		entity.setOutportprice(inport.getInportprice());
		entity.setOutputtime(new Date());
		entity.setPaytype(inport.getPaytype());
		entity.setProviderid(inport.getProviderid());
		entity.setRemark(remark);
		outportMapper.insert(entity);
	}

}
