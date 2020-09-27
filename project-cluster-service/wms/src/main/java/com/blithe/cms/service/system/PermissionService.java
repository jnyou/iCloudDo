package com.blithe.cms.service.system;


import com.baomidou.mybatisplus.service.IService;
import com.blithe.cms.common.tools.DataGridView;
import com.blithe.cms.pojo.system.Permission;

/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/3/19
 * @Param:
 * @Return:
 **/
public interface PermissionService extends IService<Permission> {
    /**
     * 菜单树
     * @param
     * @return
     */
    DataGridView loadIndexLeftMenuJson();


}
