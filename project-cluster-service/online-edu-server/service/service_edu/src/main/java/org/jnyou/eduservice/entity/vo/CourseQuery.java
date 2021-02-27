package org.jnyou.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName yjn
 * @Description: 课程列表查询条件封装
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
public class CourseQuery implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

}