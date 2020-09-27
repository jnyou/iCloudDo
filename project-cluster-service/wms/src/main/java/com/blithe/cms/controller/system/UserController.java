package com.blithe.cms.controller.system;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.exception.RbacException;
import com.blithe.cms.common.tools.Constast;
import com.blithe.cms.common.tools.DataGridView;
import com.blithe.cms.common.tools.PinyinUtils;
import com.blithe.cms.pojo.system.Dept;
import com.blithe.cms.pojo.system.Role;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.system.DeptService;
import com.blithe.cms.service.system.RoleService;
import com.blithe.cms.service.system.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2020-03-19 09:55:08
 */
@RestController
@RequestMapping("sysUser")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private SysUserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    /**
     *  查询用户
     * @param user
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R querySysUserList(SysUser sysUser){

        Page page = new Page<>(sysUser.getPage(),sysUser.getLimit(),"ordernum",true);
        EntityWrapper wrapper = new EntityWrapper();
        // 用户名或者登录名
        wrapper.like(StringUtils.isNotBlank(sysUser.getName()), "loginname", sysUser.getName()).or().like(StringUtils.isNotBlank(sysUser.getName()), "name", sysUser.getName());
        wrapper.eq(StringUtils.isNotBlank(sysUser.getAddress()), "address", sysUser.getAddress());
        // 查询系统用户
        wrapper.eq("type", Constast.USER_TYPE_NORMAL);
        // 部门id
        wrapper.eq(sysUser.getDeptid()!=null,"deptid",sysUser.getDeptid());
        this.userService.selectPage(page, wrapper);

        List<SysUser> sysUsers = page.getRecords();
        // 处理部门名称和上级名称
        if(CollectionUtils.isNotEmpty(sysUsers)){
            for (SysUser user : sysUsers) {
                Integer deptid = user.getDeptid();
                if(deptid != null){
                    Dept dept = deptService.selectById(deptid);
                    user.setDepeName(dept.getTitle());
                }
                Integer mgr = user.getMgr();
                if(mgr != null){
                    user.setMgrName(this.userService.selectById(mgr).getName());
                }
            }
        }
        return R.ok().put("count",page.getTotal()).put("data",sysUsers);

    }


    @PostMapping("/deleteUser")
    public R deleteUser(Integer id){
        try {
            this.userService.deleteById(id);
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 保存
     * @param user
     * @return
     */
    @PostMapping("/userSaveOrUpdate")
    public R userSaveOrUpdate(SysUser user){
        try {
            if(user.getId() == null){
                //设置类型
                user.setType(Constast.USER_TYPE_NORMAL);
                user.setHiredate(new Date());
                String salt= IdUtil.simpleUUID().toUpperCase();
                //设置盐
                user.setSalt(salt);
                //设置密码
                user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, 2).toString());
                this.userService.insert(user);
            }else{
                this.userService.updateById(user);
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
    @GetMapping("/loadUserMaxOrderNum")
    public R loadUserMaxOrderNum(){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.orderBy("ordernum",false).last("LIMIT 1");
        SysUser sysUser = this.userService.selectOne(wrapper);
        if(sysUser != null && sysUser.getOrdernum()>0){
            return R.ok().put("error",true).put("value",sysUser.getOrdernum() + 1);
        }else{
            return R.ok().put("error",false);
        }
    }

    /**
     * 根据部门ID查询用户
     */
    @RequestMapping("/loadUsersByDeptId")
    public R loadUsersByDeptId(Integer deptid) {
        EntityWrapper<SysUser> wrapper=new EntityWrapper<>();
        wrapper.eq(deptid!=null, "deptid", deptid);
        wrapper.eq("available", Constast.AVAILABLE_TRUE);
        wrapper.eq("type", Constast.USER_TYPE_NORMAL);
        List<SysUser> list = this.userService.selectList(wrapper);
        return R.ok().put("data",list);
    }

    /**
     * 根据用户ID查询一个用户
     */
    @RequestMapping("/loadUserById")
    public DataGridView loadUserById(Integer id) {
        return new DataGridView(this.userService.selectById(id));
    }

    /**
     * 把用户中文名转成拼音
     */
    @RequestMapping("/changeChineseToPinyin")
    public R changeChineseToPinyin(String username){
        if(null!=username) {
            return R.ok().put("value", PinyinUtils.getPingYin(username));
        }else {
            return R.ok().put("value", "");
        }
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPwd")
    public R resetPwd(Integer id){

        SysUser user = new SysUser();
        user.setId(id);
        String salt = IdUtil.simpleUUID().toUpperCase();
        user.setSalt(salt);
        user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, 2).toString());
        try {
            userService.updateById(user);
        }catch (Exception e){
            throw new RbacException(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 显示所有角色并显示已经拥有的角色
     */
    @GetMapping("/initRoleByUserId")
    public R initRoleByUserId(Integer uid){
        /**
         * 查询所有可用的角色
         */
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("available", 1);
        List<Map<String, Object>> selectMapsPage = roleService.selectMaps(wrapper);
        /**
         * 根据userid查询对应的已经勾选的角色
         */
        // 先查询拥有的所有角色
        List<Integer> rids = userService.selectRidByUid(uid);
        // 根据角色id查询拥有的所有角色
        List<Role> roleList = null;
        if(CollectionUtils.isNotEmpty(rids)){
            wrapper.in("id",rids);
            roleList = roleService.selectList(wrapper);
        }else{
            roleList = new ArrayList<>();
        }
        for (Map<String,Object> r1 : selectMapsPage) {
            Boolean LAY_CHECKED=false;
            for (Role r2 : roleList) {
                if(r1.get("id").equals(r2.getId())){
                    LAY_CHECKED = true;
                }
            }
            r1.put("LAY_CHECKED",LAY_CHECKED);
        }

        return R.ok().put("data",selectMapsPage).put("count",selectMapsPage.size());

    }


    /**
     * 批量保存用户id和角色id
     * @param params
     * @return
     */
    @RequestMapping("/insertBatchUidAndRid")
    public R insertBatchUidAndRid(@RequestBody List<Map<String,Object>> params){
        userService.insertBatchUidAndRid(params);
        return R.ok();
    }


    /**
     * 修改密码
     */
    @PostMapping("/changePwd")
    public R changePwd(@RequestParam Map<String,Object> params){
        SysUser sysUser = userService.selectById((Serializable) params.get("id"));
        String oldPwd = new Md5Hash(params.get("pwd"), sysUser.getSalt(), 2).toString();
        if(!oldPwd.equals(sysUser.getPwd())){
            return R.ok().put("msg","旧密码不正确");
        }else {
            SysUser user = new SysUser();
            user.setId(Integer.parseInt(params.get("id").toString()));
            String newPwd = new Md5Hash(params.get("newPwd"), sysUser.getSalt(), 2).toString();
            user.setPwd(newPwd);
            boolean flag = userService.updateById(user);
            if(flag){
                return R.ok().put("msg","密码修改成功");
            }else {
                return R.error();
            }
        }
    }
}