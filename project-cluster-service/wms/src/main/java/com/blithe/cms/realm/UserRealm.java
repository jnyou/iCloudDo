package com.blithe.cms.realm;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.blithe.cms.common.base.ActiverUser;
import com.blithe.cms.common.tools.Constast;
import com.blithe.cms.pojo.system.Permission;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.system.PermissionService;
import com.blithe.cms.service.system.RoleService;
import com.blithe.cms.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * @ClassName UserRealm
 * @Description: 认证 授权
 * @Author: 夏小颜
 * @Date: 12:06
 * @Version: 1.0
 **/
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private SysUserService sysUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 认证(验证用户身份)
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     * @description: 在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的
     * 在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        //获取用户名密码 第一种方式
        //String username = (String) token.getPrincipal().toString();
        //String password = new String((char[]) token.getCredentials());

        //获取用户名 密码 第二种方式
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());

        //        System.out.println("代理对象:" + sysUserService.getClass().getSimpleName()); // 通过懒加载交给代理对象 代理对象:$Proxy85
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("loginname", token.getPrincipal().toString());
        SysUser sysUser = sysUserService.selectOne(entityWrapper);
        if (null != sysUser) {
            // 加盐
            ByteSource salt = ByteSource.Util.bytes(sysUser.getSalt());
            AuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, sysUser.getPwd(), salt, this.getName());
            return info;
        }
        return null;
    }


    /**
     * 授权
     *
     * 授权用户权限
     * 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的
     * 它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示
     * 如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
     *
     * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
     * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
     *
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加。
     * authorizationInfo.addRole(role.getRole()); authorizationInfo.addStringPermission(p.getPermission());
     *
     * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
     * authorizationInfo.setRoles(roles); authorizationInfo.setStringPermissions(stringPermissions);
     *
     * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");
     * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
     *
     * 如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");
     * 就说明访问/add这个链接必须要有 "权限添加" 这个权限和具有 "100002" 这个角色才可以访问
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        log.info("user AuthorizationInfo is use!");
        //获取用户
        SysUser user = (SysUser) principal.getPrimaryPrincipal();
        //获取角色
        List<String> roles = null;
        //获取用户权限
        List<Permission> permissions = null;
        List<Integer> pid = null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 超级管理员
        if(Constast.USER_TYPE_SUPER.equals(user.getType())){
            info.addStringPermission("*:*");
        }else{
            List<Integer> pids = new ArrayList<>();
            EntityWrapper wrapper = new EntityWrapper<Object>();
            EntityWrapper wrapper1 = new EntityWrapper<Object>();
            // 通过用户名称查询用户角色
            List<Integer> rids = sysUserService.selectRidByUid(user.getId());
            if(CollectionUtil.isNotEmpty(rids)){
                wrapper.in("id",rids);
                roles = roleService.selectList(wrapper);
                // 根据rids查询pids
                for (Integer rid : rids) {
                    pid = roleService.queryRolePermissionIdsByRid(rid);
                    pids.addAll(pid);
                }
                if(CollectionUtil.isNotEmpty(pids)){
                    wrapper1.in("id",pids);
                    permissions = permissionService.selectList(wrapper1);
                }
            }
            // 添加角色
            if(CollectionUtil.isNotEmpty(roles)){
                info.addRoles(roles);
            }
            // 添加权限
            if(CollectionUtil.isNotEmpty(permissions)){
                // 用户权限列表
                Set<String> permsSet = new HashSet<String>();
                for (Permission perms : permissions) {
                    if (StringUtils.isEmpty(perms.getPercode())) {
                        continue;
                    }
                    permsSet.addAll(Arrays.asList(perms.getPercode().trim().split(",")));
                }
                if(CollectionUtil.isNotEmpty(permsSet)){
                    info.addStringPermissions(permsSet);
                }
            }
        }
        return info;
    }

}