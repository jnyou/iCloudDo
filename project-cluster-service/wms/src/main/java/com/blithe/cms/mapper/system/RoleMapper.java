package com.blithe.cms.mapper.system;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.blithe.cms.pojo.system.Role;
import org.apache.ibatis.annotations.Param;

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
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 根据角色ID删除sys_role_permission
	 * @param id
	 */
	void deleteRolePermissionByRid(Serializable id);

	/**
	 * 根据角色ID删除sys_role_user
	 * @param id
	 */
	void deleteRoleUserByRid(Serializable id);

	/**
	 * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
	 * @param roleId
	 * @return
	 */
	List<Integer> queryRolePermissionIdsByRid(Integer roleId);

	/**
	 * 保存角色和菜单权限之间的关系
	 * @param rid
	 * @param pid
	 */
	void insertBatchRolePermission(List<Map<String,Object>> params);

}
