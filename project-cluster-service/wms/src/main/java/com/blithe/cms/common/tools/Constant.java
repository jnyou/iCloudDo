package com.blithe.cms.common.tools;

/**
 * 常量
 *
 * @author jnyou
 */
public class Constant {
	
	/** 超级管理员ID */
	public static final String SUPER_ADMIN = "1";

	/** 顶级机构ID */
	public static final int SUPER_ORG = 1;
	
	/**
	 * 缓存策略
	 * @author lujinjun
	 * @date 2017-8-3
	 *
	 */
    public enum CacheName {
    	
    	//15分钟缓存
    	EXPIRE_15_MINUTE,
    	//30分钟缓存
    	EXPIRE_30_MINUTE,
    	//1小时缓存
    	EXPIRE_1_HOUR,
    	//24小时缓存
    	EXPIRE_24_HOUR,
        //永久缓存
    	EXPIRE_NONE;
    }
    
	/**
	 * 菜单类型
	 * 
	 * @author lujinjun
	 * @email 
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author lujinjun
     * @email 
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
}
