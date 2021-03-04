package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.domain.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 根据用户id查询用户角色code
     * @param userId
     */
    String getUserRoleCode(Long userId);
}