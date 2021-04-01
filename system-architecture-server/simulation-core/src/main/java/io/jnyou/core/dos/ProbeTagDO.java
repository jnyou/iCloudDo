package io.jnyou.core.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
    * 传感器标签表
    */
@ApiModel(value="com-hc-device-entity-TProbeTag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_probe_tag")
public class ProbeTagDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 传感器id
     */
    @TableField(value = "probe_id")
    @ApiModelProperty(value="传感器id")
    private Integer probeId;

    /**
     * 标签id
     */
    @TableField(value = "tag_id")
    @ApiModelProperty(value="标签id")
    private Integer tagId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PROBE_ID = "probe_id";

    public static final String COL_TAG_ID = "tag_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}