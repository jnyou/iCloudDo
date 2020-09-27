package com.blithe.cms.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.tools.Constast;
import com.blithe.cms.common.tools.DataGridView;
import com.blithe.cms.common.tools.StringUtil;
import com.blithe.cms.common.tools.TreeNode;
import com.blithe.cms.mapper.system.PermissionMapper;
import com.blithe.cms.pojo.system.Permission;
import com.blithe.cms.pojo.system.Role;
import com.blithe.cms.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleController
 * @Description: role
 * @Author: 夏小颜
 * @Date: 16:08
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/role")
public class RoleController {
    
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionMapper permissionMapper;


    /*****************************************role manager start***********************************************/


    /**
     * query role
     * @param Role 含导出功能
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R queryRoleList(Role role){
        Wrapper wrapper = new EntityWrapper();
        wrapper.like(StringUtils.isNotBlank(role.getName()),"name",role.getName());
        wrapper.ge(role.getStartTime()!=null,"createtime",role.getStartTime());
        wrapper.le(role.getEndTime()!=null,"createtime",role.getEndTime());
        wrapper.orderBy("createtime",false);
        if(!StringUtil.isNull(role.getPage()) && !StringUtil.isNull(role.getLimit())){
            Page page = new Page<>(role.getPage(),role.getLimit());
            roleService.selectPage(page, wrapper);
            return R.ok().put("count",page.getTotal()).put("data",page.getRecords());
        }else {
            List list = roleService.selectList(wrapper);
            return R.ok().put("data",list);
        }
    }


    @PostMapping("/delete")
    public R delete(@RequestBody String id){
        try {
            roleService.deleteById(Integer.parseInt(id));
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }


    /**
     * save
     * @param Role
     * @return
     */
    @PostMapping("/roleSaveOrUpdate")
    public R roleSaveOrUpdate(Role role){
        try {
            if(role.getId() == null){
                role.setCreatetime(new Date());
                this.roleService.insert(role);
            }else{
                this.roleService.updateById(role);
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }
/*****************************************role manager end***********************************************/


/*****************************************role permission manager start**********************************/
    /**
     * 权限分配构造树
     * @return
     */
    @RequestMapping("/permissionAssignment")
    public DataGridView permissionAssignment(Integer roleId){

        // 全部可用权限，查询出可用的permission
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Permission> allAvailablePermission = permissionMapper.selectList(entityWrapper);
        // 通过rid获取到对应的pid
        List<Integer> pidList = roleService.queryRolePermissionIdsByRid(roleId);
        // 当前角色拥有的权限          拿到pid到权限表查询拥有的权限
        List<Permission> havePermission = null;
        if(pidList.size()>0){
            entityWrapper.in("id",pidList);
            havePermission = permissionMapper.selectList(entityWrapper);
        }else {
            havePermission = new ArrayList<>();
        }
        /**
         * builder tree ： return data format
         *
         * {
         * "status":{"code":200,"message":"操作成功"},
         * "data": [{
         *   "id":"001",
         *   "title": "湖南省",
         *   "parentId": "0",
         *   "checkArr": "0",
         *   "children":[]
         * }]
         * }
         */
        List<TreeNode> treeNode = new ArrayList<>();
        // 选中状态,默认为0不选中,当allAvailablePermission和havePermission这两个集合中都有的就修改checkArr为1选中状态,以查询所有的权限为准
        for (Permission p1 : allAvailablePermission){
            String checkArr = "0";
            for (Permission p2 : havePermission){
                if(p1.getId().equals(p2.getId())){
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread =  p1.getOpen()==null || p1.getOpen() == 1 ? true : false;
            treeNode.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, checkArr));
        }
        return new DataGridView(treeNode);
    }


    @RequestMapping("/insertBatchRolePermission")
    public R insertBatchRolePermission(@RequestBody List<Map<String,Object>> params){
        roleService.insertBatchRolePermission(params);
        return R.ok();
    }


/*****************************************role permission manager end**********************************/
}