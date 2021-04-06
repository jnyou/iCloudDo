package io.jnyou.core.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jnyou
 * @since 2020-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_control")
@ApiModel(value="Control对象", description="")
public class ControlDO implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parent;

    private String type;

    private Integer catalog;

    private String name;

    private String caption;

    private Integer source;

    private String timeInterval;

    private Integer refreshDelay;

    private String transform;

    private String warnCond;

    private String memo;

    private Integer removed;

    private Float minValue;

    private Float maxValue;


}
