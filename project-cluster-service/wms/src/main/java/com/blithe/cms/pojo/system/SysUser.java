package com.blithe.cms.pojo.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.blithe.cms.common.base.BaseVO;
import lombok.Data;

import java.util.Date;

/**
 * 用户管理
 * @author 夏小颜
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseVO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String loginname;

    private String address;

    private Integer sex;

    private String remark;

    private String pwd;

    private Integer deptid;

    private Date hiredate;

    private Integer mgr;

    private Integer available;

    private Integer ordernum;

    /**
     * 用户类型[0超级管理员1，管理员，2普通用户]
     */
    private Integer type;

    /**
     * 头像地址
     */
    private String imgpath;

    private String salt;

    private static final long serialVersionUID = 1L;
    /**
     *  部门名称
      */
    @TableField(exist = false)
    private String depeName;
    /**
     *  上级名称
     */
    @TableField(exist = false)
    private String mgrName;
}