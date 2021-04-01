package io.jnyou.core.dos;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value="com-hc-device-entity-Probe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_probe")
public class ProbeDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="id，保存时不传，修改时必填")
    private Integer id;

    @TableField(value = "device_id")
    @ApiModelProperty(value="设备id 保存时必填")
    private Integer deviceId;

    @TableField(value = "type")
    @ApiModelProperty(value="类型")
    private String type;

    @TableField(value = "catalog")
    @ApiModelProperty(value="分类")
    private Integer catalog;

    @TableField(value = "name")
    @ApiModelProperty(value="名称")
    private String name;

    @TableField(value = "caption")
    @ApiModelProperty(value="说明")
    private String caption;

    /**
     * 维护在内存里
     */
    @TableField(exist = false)
    @ApiModelProperty(value="维护在内存里",hidden = true)
    private Integer state;

    @TableLogic
    @ApiModelProperty(value="",hidden = true)
    private Integer removed;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间，不用管此字段")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间，不用管此字段")
    private Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty(value="用于分组查询，不用管此字段")
    private Integer group;

    @TableField(exist = false)
    @ApiModelProperty(value="关联标签id列表")
    private List<Integer> tagList;


    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DEVICE_ID = "device_id";

    public static final String COL_TYPE = "type";

    public static final String COL_CATALOG = "catalog";

    public static final String COL_NAME = "name";

    public static final String COL_CAPTION = "caption";

    public static final String COL_STATE = "state";

    public static final String COL_REMOVED = "removed";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}