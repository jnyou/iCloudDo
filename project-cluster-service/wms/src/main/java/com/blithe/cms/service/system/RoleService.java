package com.blithe.cms.service.system;


import com.baomidou.mybatisplus.service.IService;
import com.blithe.cms.pojo.system.Role;

import java.util.List;
import java.util.Map;


/**
 * @Author: youjiannan
 * @Description: role
 * @Date: 2020/3/27
 * @Param:
 * @Return:
 **/
public interface RoleService extends IService<Role> {

	/**
	 * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
	 * @param roleId
	 * @return
	 */
	List<Integer> queryRolePermissionIdsByRid(Integer roleId);

	/**
	 * 保存角色和菜单权限之间的关系
	 * @param roleId
	 * @param ids
	 */
	void insertBatchRolePermission(List<Map<String,Object>> params);

}
