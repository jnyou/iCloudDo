package com.blithe.cms.service.business.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.mapper.business.GoodsMapper;
import com.blithe.cms.pojo.business.Goods;
import com.blithe.cms.service.business.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/4/2
 * @Param:
 * @Return:
 **/
@Service
@Transactional
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

	
	@Override
	public boolean insert(Goods entity) {
		return super.insert(entity);
	}
	
	@Override
	public boolean updateById(Goods entity) {
		return super.updateById(entity);
	}
	
	@Override
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}
	
	@Override
	public Goods selectById(Serializable id) {
		return super.selectById(id);
	}
}
