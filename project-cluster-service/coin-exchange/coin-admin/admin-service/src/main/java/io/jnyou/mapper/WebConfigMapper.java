package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.domain.WebConfig;

public interface WebConfigMapper extends BaseMapper<WebConfig> {
    int deleteByPrimaryKey(Long id);

    int insert(WebConfig record);

    int insertSelective(WebConfig record);

    WebConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WebConfig record);

    int updateByPrimaryKey(WebConfig record);
}