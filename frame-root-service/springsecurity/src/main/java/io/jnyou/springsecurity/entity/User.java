package io.jnyou.springsecurity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;

    private String name;

    private String username;

    private String password;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

}