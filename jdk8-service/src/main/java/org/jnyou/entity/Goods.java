package org.jnyou.entity;

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
@NoArgsConstructor
public class Goods implements Serializable {

    private Integer id;

    private String goodName;

    private double price;

    private double weight;

    private Date createTime;

    private Date expire;

    public Goods(Integer id, String goodName, Date createTime, Date expire) {
        this.id = id;
        this.goodName = goodName;
        this.createTime = createTime;
        this.expire = expire;
    }
}