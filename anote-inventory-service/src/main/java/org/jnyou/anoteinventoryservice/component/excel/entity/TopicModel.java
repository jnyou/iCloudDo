package org.jnyou.anoteinventoryservice.component.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TopicModel
 * @Description: xx导入Excel实体类
 * @Author: jnyou
 **/
@Data
public class TopicModel extends BaseRowModel implements Serializable {

    @ExcelProperty(value = "题目标题",index = 0)
    private String title;

    @ExcelProperty(value = "题目类型 1、单选 2、判断 3、填空",index = 1)
    private Integer type;

    @ExcelProperty(value = "选项A",index = 2)
    private String option1;

    @ExcelProperty(value = "选项B",index = 3)
    private String option2;

    @ExcelProperty(value = "选项C",index = 4)
    private String option3;

    @ExcelProperty(value = "选项D",index = 5)
    private String option4;

    @ExcelProperty(value = "选项E",index = 6)
    private String option5;

    @ExcelProperty(value = "选项F",index = 7)
    private String option6;

    // 判断题答案 0对 ，1错
    @ExcelProperty(value = "每一个题的答案",index = 8)
    private String answer;

    @ExcelProperty(value = "每道题的分数",index = 9)
    private Float score;

    @ExcelProperty(value = "试题解析",index = 10)
    private String remark;

}