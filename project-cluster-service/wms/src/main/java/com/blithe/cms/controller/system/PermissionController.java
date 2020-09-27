package com.blithe.cms.controller.system;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.tools.*;
import com.blithe.cms.pojo.system.Permission;
import com.blithe.cms.service.system.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: youjiannan
 * @Description: permission
 * @Date: 2020/3/21
 * @Param:
 * @Return:
 **/
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


/*****************************************permission manager start***********************************************/

    /**
     * load permission manager left dtree  JSON data
     * @return
     */
    @PostMapping("/loadPermissionLeftDtreeJson")
    public DataGridView loadPermissionLeftDtreeJson() {
        //设置只能查询权限
        List<Permission> permissionList = this.permissionService.selectList(new EntityWrapper().eq("type", Constast.TYPE_MNEU));
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission : permissionList) {
            Boolean openFlag = permission.getOpen() == 1 ? true: false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),openFlag));
        }
        return new DataGridView(treeNodes);
    }


    /**
     * query permission
     * @param Permission
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R queryPermissionList(Permission permission){
        Page page = new Page<>(permission.getPage(),permission.getLimit(),"ordernum",true);
        Wrapper wrapper = new EntityWrapper();
        wrapper.like(StringUtils.isNotBlank(permission.getTitle()),"title",permission.getTitle());
        wrapper.like(StringUtils.isNotBlank(permission.getPercode()),"percode",permission.getPercode());
        wrapper.eq("type", Constast.TYPE_PERMISSION);
        // 接受前台left参数进行渲染数据表格
        if(permission.getId()!=null){
            wrapper.eq("id",permission.getId()).or().eq("pid",permission.getId());
        }
        this.permissionService.selectPage(page, wrapper);

        return R.ok().put("count",page.getTotal()).put("data",page.getRecords());

    }


    @PostMapping("/delete")
    public R checkPermissionChildrenDel(@RequestBody String id){
        try {
            permissionService.deleteById(Integer.parseInt(id));
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * save
     * @param Permission
     * @return
     */
    @PostMapping("/permissionSaveOrUpdate")
    public R permissionSaveOrUpdate(Permission Permission){
        try {
            if(Permission.getId() == null){
                Permission.setType(Constast.TYPE_PERMISSION);
                this.permissionService.insert(Permission);
            }else{
                this.permissionService.updateById(Permission);
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * query max ordernum
     * @return
     */
    @GetMapping("/loadPermissionMaxOrderNum")
    public R loadPermissionMaxOrderNum(){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("type", Constast.TYPE_PERMISSION);
        wrapper.orderBy("ordernum",false).last("LIMIT 1");
        Permission permission = this.permissionService.selectOne(wrapper);
        if(permission != null && permission.getOrdernum()>0){
            return R.ok().put("error",true).put("value",permission.getOrdernum() + 1);
        }else{
            return R.ok().put("error",false);
        }
    }

/*****************************************permission manager end***********************************************/
    

}

