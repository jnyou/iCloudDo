package io.jnyou.core.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jnyou
 * @since 2020-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_device")
public class DeviceDO implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parent;

    private String type;

    private Integer catalog;

    private String name;

    private String caption;

    private String vendor;

    private Date purchaseDate;

    private Date warrantyDate;

    private Integer enabled;

    private String memo;

    private String qrcode;

    private Integer removed;

    private Integer showInClient;

    @TableField("module_Id")
    private Integer moduleId;
    @TableField(exist = false)
    private Integer state;




}
