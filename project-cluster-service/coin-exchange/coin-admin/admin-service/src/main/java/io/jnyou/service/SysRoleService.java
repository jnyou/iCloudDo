package io.jnyou.service;

import io.jnyou.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysRoleService extends IService<SysRole>{


    /**
     * 判断某个用户是否是超级管理员
     * @param userId
     */
    boolean isSuperAdmin(Long userId);
}
