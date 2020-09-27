package com.blithe.cms.service.business.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.mapper.business.ProviderMapper;
import com.blithe.cms.pojo.business.Provider;
import com.blithe.cms.service.business.GoodsService;
import com.blithe.cms.service.business.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/4/2
 * @Param:
 * @Return:
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {

	@Autowired
	private GoodsService goodsService;

	@Override
	public boolean insert(Provider entity) {
		return super.insert(entity);
	}
	@Override
	public boolean updateById(Provider entity) {
		return super.updateById(entity);
	}
	
	@Override
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}
	
	@Override
	public Provider selectById(Serializable id) {
		return super.selectById(id);
	}
	
	@Override
	public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
		return super.deleteBatchIds(idList);
	}

	/**
	 * 删除供应商需要删除供应商下面的所有商品
	 * @param columnMap
	 * @return
	 */
	@Override
	public boolean deleteByMap(Map<String, Object> columnMap) {
		EntityWrapper entityWrapper = new EntityWrapper();
		entityWrapper.eq("providerid",columnMap.get("id"));
		goodsService.delete(entityWrapper);
		return super.deleteByMap(columnMap);

	}
}
