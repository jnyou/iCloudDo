package org.jnyou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Goods
 * @Description:
 * @Author: jnyou
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    private Integer id;

    private String goodName;

    private double price;

    private double weight;

    private Date createTime;

    private Date expire;

    public String ine;

}