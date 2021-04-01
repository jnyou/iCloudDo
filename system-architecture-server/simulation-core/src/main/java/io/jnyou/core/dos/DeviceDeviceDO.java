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
    * 设备标签表
    */
@ApiModel(value="com-hc-device-entity-DeviceDeviceDO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_device_device")
public class DeviceDeviceDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 设备uuid
     */
    @TableField(value = "device_uuid")
    @ApiModelProperty(value="设备uuid")
    private Integer deviceUuid;

    /**
     * 关联设备uuid
     */
    @TableField(value = "relation_device_uuid")
    @ApiModelProperty(value="关联设备uuid")
    private Integer relationDeviceUuid;

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

    public static final String COL_DEVICE_UUID = "device_uuid";

    public static final String COL_RELATION_DEVICE_UUID = "relation_device_uuid";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}