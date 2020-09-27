package com.blithe.cms.service.system.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.mapper.system.PermissionMapper;
import com.blithe.cms.mapper.system.RoleMapper;
import com.blithe.cms.pojo.system.Role;
import com.blithe.cms.service.system.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: youjiannan
 * @Description: role
 * @Date: 2020/3/27
 * @Param:
 * @Return:
 **/
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public boolean deleteById(Serializable id) {
		//根据角色ID删除sys_role_permission
		roleMapper.deleteRolePermissionByRid(id);
		//根据角色ID删除sys_role_user
		roleMapper.deleteRoleUserByRid(id);
		return super.deleteById(id);
	}

	/**
	 * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
	 */
	@Override
	public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {
		return roleMapper.queryRolePermissionIdsByRid(roleId);
	}

	/**
	 * 保存角色和菜单权限之间的关系
	 */
	@Override
	public void insertBatchRolePermission(List<Map<String,Object>> params) {
		//根据rid删除sys_role_permission
		if(CollectionUtils.isNotEmpty(params) && params.size() > 0){
			roleMapper.deleteRolePermissionByRid((Serializable) params.get(0).get("rid"));
			roleMapper.insertBatchRolePermission(params);
		}
	}
}
