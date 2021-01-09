package io.jnyou.springsecurity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Menu {

    private Integer id;

    private Integer pid;

    private String name;

    private String permission;

    private String type;

    private String url;

    private String path;

    private String component;

    private String btnKey;

    private String icon;

    private Byte seq;

    private String remark;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

}