package com.blithe.cms.common.base;

import com.blithe.cms.pojo.system.Permission;
import com.blithe.cms.pojo.system.Role;
import com.blithe.cms.pojo.system.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ActiverUser
 * @Description: 需要shiro管理
 * @Author: 夏小颜
 * @Date: 12:07
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private SysUser sysUser;

    private List<String> roles;

    private List<String> permissions;
}