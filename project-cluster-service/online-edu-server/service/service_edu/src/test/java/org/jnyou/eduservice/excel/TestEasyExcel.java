package org.jnyou.eduservice.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName yjn
 * @Description: EasyExcel写测试
 * @Author: 夏小颜
 * @Version: 1.0
 **/
public class TestEasyExcel {

    public static void main(String[] args) {
        // 实现excel写操作
        // 设置写入文件夹地址和excel文件名称
//        String filename = "O:\\write.xlsx";

        // 调用easy excel里面的方法实现写操作
//        EasyExcel.write(filename,ExcelData.class).sheet("学生表").doWrite(getData());


        // 实现excel读操作
        String filename = "O:\\write.xlsx";
        EasyExcel.read(filename,ExcelData.class,new ExcelListener()).sheet().doRead();
    }

    private static List<ExcelData> getData() {
        List<ExcelData> list = new ArrayList<ExcelData>();
        for (int i=0;i<9;i++){
            ExcelData data = new ExcelData();
            data.setSno(i);
            data.setSname("欧明" + i);
            list.add(data);
        }
        return list;
    }


}