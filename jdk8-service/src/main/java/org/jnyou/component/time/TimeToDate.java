package org.jnyou.component.time;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * 分类名称
 * 使用开源时间框架joda-time计算和处理时间
 * UTC时间由于没有时区这种概念一般表示为：2019-11-23T21:59:55.232Z（表示没有时区）或者2019-11-23T21:59:55.232+08:00(如果有时区)
 * @ClassName TimeToDate
 * @Description: jdk8 time handle
 * @Author: jnyou
 **/
public class TimeToDate {

    /**
     * 把UTC时间转换为Java时间
     * @param utcDate
     * @return
     * @Author jnyou
     */
    public static Date covertUTCToDate(String utcDate) {
        try {
            DateTime dateTime = DateTime.parse(utcDate, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
            return dateTime.toDate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 把Java时间转换为UTC时间
     * @param javaDate
     * @return
     * @Author jnyou
     */
    public static String covertDateToUTC(Date javaDate) {
        DateTime dateTime = new DateTime(javaDate, DateTimeZone.UTC);
        return dateTime.toString();
    }

    /**
     * 转换为指定格式的时间
     * @param javaDate
     * @param dateFormat
     * @return
     * @Author jnyou
     */
    public static String covertDateToLocalByDateFormat(Date javaDate, String dateFormat) {
        DateTime dateTime = new DateTime(javaDate);
        return dateTime.toString(dateFormat);
    }

    public static void main(String[] args) {
        System.out.println(covertUTCToDate("2019-11-23T21:59:55.232Z"));
        System.out.println(covertDateToUTC(new Date()));
        System.out.println(covertDateToLocalByDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
    }

}