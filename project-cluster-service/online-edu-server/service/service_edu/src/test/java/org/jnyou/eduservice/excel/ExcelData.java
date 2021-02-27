package org.jnyou.eduservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
public class ExcelData {

    // 设置excel表头名字

    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;


}