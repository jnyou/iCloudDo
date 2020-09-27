package com.blithe.cms.common.base;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 公共VO基础类
 * @author 夏小颜
 */
@EqualsAndHashCode(callSuper=false)
@Data
public abstract class BaseVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;


	@TableField(exist = false)
	private Integer page;

	@TableField(exist = false)
	private Integer limit;
	/**
	 * 接收多个ID
	 */
	@TableField(exist = false)
	private Integer[] ids;

	@TableField(exist = false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@TableField(exist = false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;


}
