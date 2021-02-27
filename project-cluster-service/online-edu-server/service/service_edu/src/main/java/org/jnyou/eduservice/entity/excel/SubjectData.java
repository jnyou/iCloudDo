package org.jnyou.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName yjn
 * @Description: 课程分类。一级分类和二级分类
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;

}