package com.blithe.cms.service.business;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.blithe.cms.pojo.business.Provider;
import com.blithe.cms.pojo.business.Sales;

import java.io.Serializable;

/**
 * @author 夏小颜
 */
public interface SalesService extends IService<Sales> {

    @Override
    boolean insert(Sales sales);

    @Override
    boolean updateById(Sales sales);

    @Override
    boolean deleteById(Serializable serializable);
}
