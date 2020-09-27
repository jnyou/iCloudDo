package com.blithe.cms.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: SystemController
 * @Description: 跳转Controller
 * @Author: 夏小颜
 * @Date: 9:59
 * @Version: 1.0
 **/
@Controller
public class SystemController {


    /**
     * 系统登陆页
     */
    @RequestMapping("login")
    public String toLogin(){
        return "login";
    }


    /**
     * 系统主页
     */
    @RequestMapping("index")
    public String toIndex(){
        return "index";
    }


    /**
     * main 工作空间页面
     */
    @RequestMapping("main")
    public String toMain(){
        return "main";
    }

    /**
     * 登陆日志信息页
     */
    @RequestMapping("sys/toLogInfoManager")
    public String toLogInfoManager(){
        return "system/logInfo";
    }

    /**
     * 系统公告页
     */
    @RequestMapping("sys/toNoticeManager")
    public String toNoticeManager(){
        return "system/notice";
    }
    /**
     * ICON 页面
     */
    @RequestMapping("sys/toIconManager")
    public String toIconManager(){
        return "icon";
    }

    /**
     * 部门管理页面
     * @return
     */
    @RequestMapping("sys/toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    }

    /**
     * 部门管理left页面
     * @return
     */
    @RequestMapping("sys/toDeptLeft")
    public String toDeptLeft(){
        return "system/dept/deptLeft";
    }

    /**
     * 部门管理right页面
     * @return
     */
    @RequestMapping("sys/toDeptRight")
    public String toDeptRight(){
        return "system/dept/deptRight";
    }



    /**
     * 菜单管理页面
     * @return
     */
    @RequestMapping("sys/toMenuManager")
    public String toMenuManager(){
        return "system/menu/menuManager";
    }

    /**
     * 菜单管理left页面
     * @return
     */
    @RequestMapping("sys/toMenuLeft")
    public String toMenuLeft(){
        return "system/menu/menuLeft";
    }

    /**
     * 菜单管理right页面
     * @return
     */
    @RequestMapping("sys/toMenuRight")
    public String toMenuRight(){
        return "system/menu/menuRight";
    }


    /**
     * 权限管理页面
     * @return
     */
    @RequestMapping("sys/toPermissionManager")
    public String toPermissionManager(){
        return "system/permission/permissionManager";
    }

    /**
     * 权限管理left页面
     * @return
     */
    @RequestMapping("sys/toPermissionLeft")
    public String toPermissionLeft(){
        return "system/permission/permissionLeft";
    }

    /**
     * 权限管理right页面
     * @return
     */
    @RequestMapping("sys/toPermissionRight")
    public String toPermissionRight(){
        return "system/permission/permissionRight";
    }

    /**
     * 角色管理
     * @return
     */
    @RequestMapping("sys/toRoleManager")
    public String toRoleManager(){
        return "system/role/role";
    }


    /**
     * 用户管理
     * @return
     */
    @RequestMapping("sys/toUserManager")
    public String toUserManager(){
        return "system/user/user";
    }


    /**
     * 修改密码
     * @return
     */
    @RequestMapping("toChangePwd")
    public String toChangePwd(){
        return "system/user/changePwd";
}


}