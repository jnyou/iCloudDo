package org.jnyou.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName yjn
 * @Description: 最终发布展示课程相关信息
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    /**
     * 只用于显示
     */
    private String price;
}