package io.jnyou.auth.constant;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * LoginConstant
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class LoginConstant {

    /**
     * 管理员登录
     */
    public static final String ADMIN_TYPE = "admin_type";

    /**
     * 用户/会员登录
     */
    public static final String MEMBER_TYPE = "member_type";

    /**
     * 超级管理员的角色code
     */
    public static final String ADMIN_CODE = "ROLE_ADMIN";

    /**
     * token的刷新
     */
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    /**
     * 使用用户名查询用户
     */
    public static final String QUERY_ADMIN_SQL =
            "SELECT `id` ,`username`, `password`, `status` FROM sys_user WHERE username = ? ";

    /**
     * 查询用户的角色code
     */
    public static final String QUERY_ROLE_CODE_SQL =
            "SELECT `code` FROM sys_role LEFT JOIN sys_user_role ON sys_role.id = sys_user_role.role_id WHERE sys_user_role.user_id= ?";

    /**
     * 查询超级管理员所有的权限名称
     */
    public static final String QUERY_ALL_PERMISSIONS =
            "SELECT `name` FROM sys_privilege";

    /**
     * 查询普通管理员的权限信息
     */
    public static final String QUERY_PERMISSION_SQL =
            "SELECT `name` FROM sys_privilege LEFT JOIN sys_role_privilege ON sys_role_privilege.privilege_id = sys_privilege.id LEFT JOIN sys_user_role  ON sys_role_privilege.role_id = sys_user_role.role_id WHERE sys_user_role.user_id = ?";

    /**
     * 会员登录，通过邮箱或者手机号
     */
    public static final String QUERY_MEMBER_SQL =
            "SELECT `id`,`password`, `status` FROM `user` WHERE mobile = ? or email = ? ";

    /**
     * 使用用户的id 查询用户名称
     */
    public static  final  String QUERY_ADMIN_USER_WITH_ID = "SELECT `username` FROM sys_user where id = ?" ;

    /**
     * 使用用户的id 查询用户名称
     */
    public static  final  String QUERY_MEMBER_USER_WITH_ID = "SELECT `mobile` FROM user where id = ?" ;

}