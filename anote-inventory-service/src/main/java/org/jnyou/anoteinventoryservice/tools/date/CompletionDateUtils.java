package org.jnyou.anoteinventoryservice.tools.date;



import org.jnyou.anoteinventoryservice.constant.StringConstant;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @author jnyou
 */
public final class CompletionDateUtils {

    /**
     * 隐藏构造方法.
     */
    private CompletionDateUtils() {
    }

    /**
     * 数据库查询出来的统计数据有时候日期是不连续的.
     * 但是前端展示要补全缺失的日期.
     * 此方法返回一个给定日期期间的所有日期字符串列表.
     * 具体在业务逻辑中去判断补全.
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static List<String> completionDate(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String pattern) {
        List<String> dataList = new ArrayList<>();
        //日期格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        List<String> dateList = new ArrayList<>();
        //遍历给定的日期期间的每一天
        if(StringConstant.YYYY.equals(pattern)){
            for (int i = 0; !Duration.between(startDate.plusYears(i), endDate).isNegative(); i++) {
                //添加日期
                dateList.add(startDate.plusYears(i).format(formatter));
            }
        } else if(StringConstant.YYYY_MM.equals(pattern)){
            for (int i = 0; !Duration.between(startDate.plusMonths(i), endDate).isNegative(); i++) {
                //添加日期
                dateList.add(startDate.plusMonths(i).format(formatter));
            }
        }

        return dateList;
    }

    /**
     * main.
     *
     * @param args
     */
    public static void main(String[] args) {
        //测试过去7天
        System.out.println(completionDate(LocalDateTime.of(2020,9,1,00,00,00).minusYears(2), LocalDateTime.of(2020,9,1,00,00,00),"yyyy"));
    }
}
