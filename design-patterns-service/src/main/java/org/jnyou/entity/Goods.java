package org.jnyou.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Goods
 * @Description:
 * @Author: jnyou
 **/
@Data
public class Goods implements Serializable {

    private Integer id;

    private String goodName;

    private Date createTime;

    private Date expire;

}