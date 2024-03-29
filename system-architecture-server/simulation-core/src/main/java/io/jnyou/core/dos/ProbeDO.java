package io.jnyou.core.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="t_probe",autoResultMap = true)
public class ProbeDO implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parent;

    private String templateId;
    
    private Integer catalog;

    private String name;

    private String caption;

    private Integer source;

    private String timeInterval;

    private String savingInterval;

    private String transform;

    private String warnCond;

    private String memo;

    private Float minValue;

    private Float maxValue;

    private Integer removed;

    private Integer jobId;

    private Integer state;
    
    //用于分组查询
    @TableField(exist = false)
    private Integer group;
    
    @TableField(exist = false)
    private ServiceDO service;
    
}
