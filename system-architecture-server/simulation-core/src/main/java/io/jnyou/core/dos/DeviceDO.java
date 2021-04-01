package io.jnyou.core.dos;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com-hc-device-entity-Device")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_device")
public class DeviceDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="保存时不填，修改时必填")
    private Integer id;

    @TableField(value = "uuid")
    @ApiModelProperty(value="保存时不填")
    private Integer uuid;

    @TableField(value = "parent")
    @ApiModelProperty(value="上一级设备id")
    private Integer parent;

    /**
     * 区分设备类型，是否需要采集等，场景是摄像头。
     */
    @TableField(value = "type")
    @ApiModelProperty(value="区分设备类型，是否需要采集等，场景是摄像头。")
    private String type;

    /**
     * 设备分类，等同标签功能
     */
    @TableField(value = "catalog",exist = false)
    @ApiModelProperty(value="设备分类，等同标签功能")
    private Short catalog;

    @TableField(value = "name")
    @ApiModelProperty(value="名称")
    private String name;

    @TableField(value = "caption")
    @ApiModelProperty(value="说明")
    private String caption;

    /**
     * 是否启用
     */
    @TableField(value = "enabled")
    @ApiModelProperty(value="是否启用 1启用 0不启用")
    private Integer enabled;

    /**
     * 维护在内存里
     */
    @TableField(value = "state",exist = false)
    @ApiModelProperty(value="维护在内存里")
    private Integer state;

    @TableField(value = "healthIndex",exist = false)
    @ApiModelProperty(value="健康指数维护在内存里")
    private String healthIndex;
    @TableField(exist = false)
    @ApiModelProperty(value="用于分组查询，不用管此字段")
    private Integer group;
    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty(value="逻辑删除")
    private Integer removed;

    /**
     * 是否在客户端显示
     */
    @TableField(value = "show_in_client")
    @ApiModelProperty(value="是否在客户端显示 1显示 0不显示")
    private Integer showInClient;

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

    @TableField(value = "expand")
    @ApiModelProperty(value="扩展属性 [{name:'名称',val:'值',desb:'描述'},{name:'名称',val:'值',desb:'描述'}]")
    private String expand;
    @ApiModelProperty(value="标签id")
    @TableField(exist = false)
    private Integer tagId;
    @ApiModelProperty(value="标签名")
    @TableField(exist = false)
    private String tagName;
    @ApiModelProperty(value="标签编码")
    @TableField(exist = false)
    private String tagCode;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_UUID = "uuid";

    public static final String COL_PARENT = "parent";

    public static final String COL_TYPE = "type";

    public static final String COL_CATALOG = "catalog";

    public static final String COL_NAME = "name";

    public static final String COL_CAPTION = "caption";

    public static final String COL_ENABLED = "enabled";

    public static final String COL_STATE = "state";

    public static final String COL_REMOVED = "removed";

    public static final String COL_SHOW_IN_CLIENT = "show_in_client";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}