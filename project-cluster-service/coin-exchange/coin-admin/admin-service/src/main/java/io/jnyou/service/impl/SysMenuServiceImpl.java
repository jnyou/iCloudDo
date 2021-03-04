package io.jnyou.service.impl;

import io.jnyou.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.SysMenu;
import io.jnyou.mapper.SysMenuMapper;
import io.jnyou.service.SysMenuService;
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Autowired
    SysRoleService roleService;

    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        // 如果该用户是超级管理员。拥有所有的菜单
        if(roleService.isSuperAdmin(userId)){
            return list();
        }
        return this.baseMapper.selectMenusByUserId(userId);
    }
}
