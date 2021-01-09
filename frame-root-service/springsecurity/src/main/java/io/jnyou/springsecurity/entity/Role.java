package io.jnyou.springsecurity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Role {

    private Integer id;

    private String name;

    private String remark;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

}