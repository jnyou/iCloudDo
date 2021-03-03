package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.domain.SysRolePrivilege;

public interface SysRolePrivilegeMapper extends BaseMapper<SysRolePrivilege> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePrivilege record);

    int insertSelective(SysRolePrivilege record);

    SysRolePrivilege selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRolePrivilege record);

    int updateByPrimaryKey(SysRolePrivilege record);
}