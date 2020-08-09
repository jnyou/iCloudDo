package org.jnyou.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName User
 * @Description:
 * @Author: jnyou
 **/
@Data
public class User implements Serializable {

    private Integer id;

    private String name;

    private String email;

    private Date createTime;

    private Date updateTime;
}