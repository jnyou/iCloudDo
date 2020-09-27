package com.blithe.cms.mapper.business;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.blithe.cms.pojo.business.Sales;

/**
 * @author 夏小颜
 */
public interface SalesMapper  extends BaseMapper<Sales> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Sales record);

    Sales selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sales record);

    int updateByPrimaryKey(Sales record);
}