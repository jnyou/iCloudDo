package org.jnyou.eduservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
public class ExcelListener extends AnalysisEventListener<ExcelData> {
    //一行一行读取excel内容
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        System.out.println("内容" + excelData);
    }

    // 读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        System.out.println("表头" +headMap);
    }

    // 读取完成后做的事情
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}