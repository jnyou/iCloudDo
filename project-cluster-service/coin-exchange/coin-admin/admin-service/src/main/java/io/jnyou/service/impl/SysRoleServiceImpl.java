package io.jnyou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.SysRole;
import io.jnyou.mapper.SysRoleMapper;
import io.jnyou.service.SysRoleService;
import org.springframework.stereotype.Service;

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
}
