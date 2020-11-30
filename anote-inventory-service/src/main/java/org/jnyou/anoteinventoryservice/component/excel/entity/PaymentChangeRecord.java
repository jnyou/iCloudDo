package org.jnyou.anoteinventoryservice.component.excel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author jnyou
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oa_payment_change_record")
@ApiModel(value="PaymentChangeRecord对象", description="")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class PaymentChangeRecord implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    @ExcelProperty("序号")
    @TableField(exist = false)
    private Integer tableId;

    @ApiModelProperty(value = "预收款编号")
    @ExcelIgnore
    private String paymentAdvanceNumber;

    @ApiModelProperty(value = "收费通知单编号")
    @ExcelIgnore
    private String noticeNumber;

    @ApiModelProperty(value = "单位编号")
    @ExcelProperty("单位编号")
    private String tenantNumber;

    @ApiModelProperty(value = "单位名称")
    @ExcelProperty("单位名称")
    private String tenantName;

    @ApiModelProperty(value = "合同编号")
    @ExcelProperty("合同编号")
    private String contractNumber;

    @ApiModelProperty(value = "合同名称")
    @ExcelProperty("合同名称")
    private String contractName;

    @ApiModelProperty(value = "预收款类型 1：充值 2：冲抵")
    @ExcelIgnore
    private Integer paymentType;

    @ApiModelProperty(value = "操作时间")
    @ExcelIgnore
    private String operateTime;

    @ApiModelProperty(value = "上次余额")
    @ExcelIgnore
    private BigDecimal currentBalance;

    @ApiModelProperty(value = "本次充值金额")
    @ExcelIgnore
    private BigDecimal rechargeAmount;

    @ApiModelProperty(value = "冲抵余额")
    @ExcelIgnore
    private BigDecimal offsetBalance;

    @ApiModelProperty(value = "本次余额")
    @ExcelProperty("本次余额")
    private BigDecimal thisBalance;

    @ApiModelProperty(value = "收费科目")
    @ExcelIgnore
    private Integer costType;

    @ApiModelProperty(value = "备注")
    @ExcelIgnore
    private String remark;
}
