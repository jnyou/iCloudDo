package org.jnyou.aclservice.controller;


import io.swagger.annotations.ApiOperation;
import org.jnyou.aclservice.entity.Permission;
import org.jnyou.aclservice.service.PermissionService;
import org.jnyou.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 权限 菜单管理
 * @author 夏小颜
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "递归算法查询所有菜单")
    @GetMapping
    public R selectAllPermission() {
        List<Permission> list =  permissionService.selectAllPermission();
        return R.ok().put("children",list);
    }


    @ApiOperation(value = "查询所有菜单")
    @GetMapping("indexAllPermission")
    public R indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return R.ok().put("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return R.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().put("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

