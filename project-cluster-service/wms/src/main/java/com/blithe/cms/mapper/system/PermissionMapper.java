package com.blithe.cms.mapper.system;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.blithe.cms.pojo.system.Permission;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/3/19
 * @Param:
 * @Return:
 **/
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 删除了菜单，权限就没了，所以应该也删除对应的角色id
     * @param id
     * @return
     */
    void deleteRolemissionByPid(@Param("pid") Serializable pid);
}
