package io.commchina.mybatis.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * UserEntity
 *
 * @program: icoding
 * @description:
 * @author: Mr.Nanke
 * @create: 2021-11-16 11:28
 **/
@Data
public class UserEntity implements java.io.Serializable{

    private Integer id;
    private String name;
    private String age;
    private String sex;
    private String email;
    private String phoneNumber;
    private Date createTime;
}