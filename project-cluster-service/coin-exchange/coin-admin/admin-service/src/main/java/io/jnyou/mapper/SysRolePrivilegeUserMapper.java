package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.domain.SysRolePrivilegeUser;

public interface SysRolePrivilegeUserMapper extends BaseMapper<SysRolePrivilegeUser> {
    int deleteByPrimaryKey(Long id);

    int insert(SysRolePrivilegeUser record);

    int insertSelective(SysRolePrivilegeUser record);

    SysRolePrivilegeUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePrivilegeUser record);

    int updateByPrimaryKey(SysRolePrivilegeUser record);
}