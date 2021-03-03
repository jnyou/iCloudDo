package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.domain.Config;

public interface ConfigMapper extends BaseMapper<Config> {
    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);
}