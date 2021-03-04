package io.jnyou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jnyou.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysRoleService extends IService<SysRole>{


    /**
     * 判断某个用户是否是超级管理员
     * @param userId
     */
    boolean isSuperAdmin(Long userId);

    /**
     * 使用角色的名称模糊分页角色查询
     * @param page
     * 分页数据
     * @param name
     *  角色的名称
     * @return
     */
    Page<SysRole> findByPage(Page<SysRole> page, String name);
}
