package io.jnyou.core.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 标签实体类
 * 
 * @author wuguiyu
 *
 */
@ApiModel
@Data
@TableName(autoResultMap = true,value = "t_tag")
public class TagDO {
	@ApiModelProperty("id 保存时不传，修改时必填")
	@TableId
	private Integer id;
	@ApiModelProperty("父类id")
	@TableField(value = "parent")
	private Integer parent;
	@ApiModelProperty("编码")
	@TableField(value = "code")
	private String code;
	@ApiModelProperty("名称")
	@TableField(value = "name")
	private String name;
	@ApiModelProperty("索引")
	@TableField(value = "indexes")
	private String indexes;
	@ApiModelProperty("标签类型1:系统标签，不可删除 0:用户标签")
	@TableField(value = "type")
	private Integer type;
	@ApiModelProperty("创建时间，不传此字段")
	@TableField(value = "create_time")
	private Date createTime;
	@ApiModelProperty("修改时间，不传此字段")
	@TableField(value = "update_time")
	private Date updateTime;
}
