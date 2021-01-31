package org.jnyou.gmall.orderservice.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 退货原因
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@ToString
@Data
@TableName("oms_order_return_reason")
@Accessors(chain = true)
public class OrderReturnReasonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 退货原因名
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 启用状态
	 */
	private Integer status;
	/**
	 * create_time
	 */
	private Date createTime;

}
