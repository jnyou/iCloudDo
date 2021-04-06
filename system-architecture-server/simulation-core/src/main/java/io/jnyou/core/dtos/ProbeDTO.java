package io.jnyou.core.dtos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sun.tracing.Probe;
import io.jnyou.core.base.BaseAsset;
import io.jnyou.core.base.BaseDevice;
import io.jnyou.core.base.Device;
import io.jnyou.core.type.ControlType;
import io.jnyou.core.type.ProbeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProbeDTO extends BaseAsset<ProbeType> implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Integer id;

    @TableField(value = "device_id")
    @ApiModelProperty(value="")
    private Integer deviceId;

    @TableField(value = "type")
    @ApiModelProperty(value="")
    private String type;

    @TableField(value = "catalog")
    @ApiModelProperty(value="")
    private Short catalog;

    @TableField(value = "name")
    @ApiModelProperty(value="")
    private String name;

    @TableField(value = "caption")
    @ApiModelProperty(value="")
    private String caption;
    /**
     * 维护在内存里
     */
    @TableField(value = "state")
    @ApiModelProperty(value="维护在内存里")
    private Integer state;

    @TableField(value = "removed")
    @ApiModelProperty(value="")
    private Byte removed;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty(value="用于分组查询")
    private Integer group;

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