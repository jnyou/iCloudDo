package com.blithe.cms.service.business.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.mapper.business.CustomerMapper;
import com.blithe.cms.pojo.business.Customer;
import com.blithe.cms.service.business.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/4/2
 * @Param:
 * @Return:
 **/
@Service
@Transactional
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

	
	@Override
	public boolean insert(Customer entity) {
		return super.insert(entity);
	}
	
	@Override
	public boolean updateById(Customer entity) {
		return super.updateById(entity);
	}
	
	@Override
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}
	@Override
	public Customer selectById(Serializable id) {
		return super.selectById(id);
	}
	
	@Override
	public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
		return super.deleteBatchIds(idList);
	}
	
}
