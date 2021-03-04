package io.jnyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.SysRole;
import io.jnyou.mapper.SysRoleMapper;
import io.jnyou.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public boolean isSuperAdmin(Long userId) {
        String roleCode = this.baseMapper.getUserRoleCode(userId);
        if (null != roleCode && "ROLE_ADMIN".equals(roleCode)) {
            return true;
        }
        return false;
    }

    /**
     * 使用角色的名称模糊分页角色查询
     *
     * @param page 分页数据
     * @param name 角色的名称
     * @return
     */
    @Override
    public Page<SysRole> findByPage(Page<SysRole> page, String name) {
        return page(page, new LambdaQueryWrapper<SysRole>().like(
                !StringUtils.isEmpty(name),
                SysRole::getName,
                name
        ));
    }
}
