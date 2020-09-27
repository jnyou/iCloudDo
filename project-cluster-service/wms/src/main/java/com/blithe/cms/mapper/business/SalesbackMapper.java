package com.blithe.cms.mapper.business;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.blithe.cms.pojo.business.Provider;
import com.blithe.cms.pojo.business.Salesback;

/**
 * @author 夏小颜
 */
public interface SalesbackMapper  extends BaseMapper<Salesback> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Salesback record);

    Salesback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Salesback record);

    int updateByPrimaryKey(Salesback record);
}