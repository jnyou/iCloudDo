package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.domain.SysUserLog;

public interface SysUserLogMapper extends BaseMapper<SysUserLog> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserLog record);

    int insertSelective(SysUserLog record);

    SysUserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserLog record);

    int updateByPrimaryKey(SysUserLog record);
}