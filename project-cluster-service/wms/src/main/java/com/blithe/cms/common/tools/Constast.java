package com.blithe.cms.common.tools;

/**
 * 常量接口
 * @author LJH
 *
 */
public interface Constast {
	
	/**
	 * 状态码
	 * 
	 */
	public static final Integer OK=200;
	public static final Integer ERROR=-1;
	
	/**
	 * 菜单权限类型
	 */
	public static final String TYPE_MNEU = "menu";
	public static final String TYPE_PERMISSION = "permission";
	/**
	 * 可以状态
	 */
	public static final Object AVAILABLE_TRUE = 1;
	public static final Object AVAILABLE_FALSE = 0;
	
	/**
	 * 用户类型
	 */
	public static final Integer USER_TYPE_SUPER = 0;
	public static final Integer USER_TYPE_NORMAL = 1;
	
	/**
	 * 展开类型
	 */
	public static final Integer OPEN_TRUE = 1;
	public static final Integer OPEN_FALSE = 0;


	/**
	 * 固定IP
	 */
	public static final String DEL_IP = "0:0:0:0:0:0:0:1";


	/**
	 * 默认密码
	 */
	public static final String USER_DEFAULT_PWD = "123456";
}
