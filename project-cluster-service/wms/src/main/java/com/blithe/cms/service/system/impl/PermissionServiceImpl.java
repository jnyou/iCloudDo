package com.blithe.cms.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.common.tools.*;
import com.blithe.cms.mapper.system.PermissionMapper;
import com.blithe.cms.mapper.system.RoleMapper;
import com.blithe.cms.mapper.system.SysUserMapper;
import com.blithe.cms.pojo.system.Permission;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.system.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/3/19
 * @Param:
 * @Return:
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 删除了菜单，权限就没了，所以应该也删除对应的角色id
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Serializable id) {
//        PermissionMapper baseMapper = this.baseMapper;
        // 删除与角色关联的数据
        permissionMapper.deleteRolemissionByPid(id);
        // 删除权限表的数据
        return super.deleteById(id);
    }


    @Override
    public DataGridView loadIndexLeftMenuJson(){
        //查询所有菜单
        EntityWrapper<Permission> queryWrapper=new EntityWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type", Constast.TYPE_MNEU);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);

        SysUser user = (SysUser) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("user");
        List<Permission> list=null;
        // 判断用户类型
        if (Constast.USER_TYPE_SUPER.equals(user.getType())) {
            // 超级管理员登陆
            list = permissionMapper.selectList(queryWrapper);
        } else {
            // 普通用户登陆
            //根据用户ID+角色+权限去查询
            Integer uid = user.getId();
            List<Integer> rids = userMapper.selectRidByUid(uid);
            //去重查询权限和菜单id
            Set<Object> dinSet = new HashSet<>();
            for (Integer rid : rids) {
                dinSet.addAll(roleMapper.queryRolePermissionIdsByRid(rid));
            }
            if(CollectionUtils.isNotEmpty(dinSet)){
                queryWrapper.in("id",dinSet);
                list = permissionMapper.selectList(queryWrapper);
            }
        }

        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission p : list) {
            Integer id=p.getId();
            Integer pid=p.getPid();
            String title=p.getTitle();
            String icon=p.getIcon();
            String href=p.getHref();
            Boolean spread=p.getOpen().equals(Constast.OPEN_TRUE)?true:false;
            treeNodes.add(new TreeNode(id, pid, title, icon, href, spread));
        }
        //构造层级关系
        List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes, 1);
        return new DataGridView(list2);
    }
}
