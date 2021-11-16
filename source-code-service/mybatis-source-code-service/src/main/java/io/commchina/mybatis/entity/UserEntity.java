package io.commchina.mybatis.entity;

import lombok.Data;

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
public class UserEntity {

    private String username;
    private String password;
    private Integer gender;

}