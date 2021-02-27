package org.jnyou.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.aclservice.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author jnyou
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取全部菜单
     */
    List<Permission> queryAllMenu();

    /**
     * 根据角色获取菜单
     *
     */
    List<Permission> selectAllMenu(String roleId);

    /**
     * 给角色分配权限
     *
     */
    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);

    /**
     * 递归删除菜单
     *
     */
    void removeChildById(String id);

    /**
     * 根据用户id获取用户菜单
     *
     */
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    /**
     * 获取全部菜单
     *
     */
    List<Permission> queryAllMenuGuli();

    /**
     * 递归删除菜单
     *
     */
    void removeChildByIdGuli(String id);

    /**
     * 给角色分配权限
     *
     */
    void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionId);

    /**
     * 递归算法查询所有菜单
     * @return
     * @Author jnyou
     * @Date 2020/8/29
     */
    List<Permission> selectAllPermission();

}
