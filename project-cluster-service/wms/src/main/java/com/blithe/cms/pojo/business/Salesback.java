package com.blithe.cms.pojo.business;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.blithe.cms.common.base.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 夏小颜
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bus_salesback")
public class Salesback extends BaseVO {

    private static final long serialVersionUID=1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer customerid;

    private String paytype;

    private Date salesbacktime;

    private Double salebackprice;

    private String operateperson;

    private Integer number;

    private String remark;

    private Integer goodsid;

    @TableField(exist = false)
    private String goodsname;
    @TableField(exist = false)
    private String size;
    @TableField(exist = false)
    private String customername;
}