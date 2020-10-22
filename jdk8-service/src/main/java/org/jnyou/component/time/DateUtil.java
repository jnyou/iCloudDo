package org.jnyou.component.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    /***
     * 获取当前月的上个月年月
     * @Author jnyou
     */
    public static String formerDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //当前月的上个月  （-1改为1的话，为取当前月的下个月）
        cal.add(Calendar.MONTH, -1);
//        System.out.println("========"+sdf.format( cal.getTime()));
//        System.out.println("***********"+cal.getTime());
        return sdf.format(cal.getTime());
    }

    /***
     * 获取一天24小时
     * @return
     */
    public static List<String> getHourOfToday(){
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat format=new SimpleDateFormat("HH");
        String s = df.format(day);
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<String> dates = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, 1);
            date = cal.getTime();
            String s1 = format.format(date);
            dates.add(s1);
        }
        dates.add(0,dates.get(23));
        dates.remove(24);
        return dates;
    }

    /***
     * 获取一年12个月
     * @return
     */
    public static List<String> getMonthOfYear(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format=new SimpleDateFormat("MM");
        String s = df.format(new Date().getYear());
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<String> dates = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 12; i++) {
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            date = cal.getTime();
            String s1 = format.format(date);
            dates.add(s1);
        }
        //实现List集合逆序排列
        Collections.reverse(dates);
        return dates;
    }

    /***
     * 获取指定月所在的天集合
     * @return
     */
    public static List<String> getDayOfMonth(String dataStr){
        int day = getDaysOfMonth(dataStr);
        ArrayList<String> dates = new ArrayList<String>();
        for (int i = 1; i <= day; i++) {
            String tempDay = String.valueOf(i);
            if(i<10) {
                tempDay = "0"+String.valueOf(i);
            }
            dates.add(tempDay);
        }
        return dates;
    }

    //获取某月的天数
    public static int getDaysOfMonth(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(dateStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取某个时间段内所有月份
     */
    public static List<String> getMonths(String startTime, String endTime){
        List<String> datas = new ArrayList<>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sd.parse(startTime);
            dEnd = sd.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            datas.add(sd.format(calBegin.getTime()));
        }
        return datas;
    }
    /**
     * 获取某个时间段内所年
     */
    public static List<String> getYears(String startTime, String endTime){
        List<String> datas = new ArrayList<>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sd.parse(startTime);
            dEnd = sd.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.YEAR, 1);
            datas.add(sd.format(calBegin.getTime()));
        }
        return datas;
    }


    /**
     * 获取某个时间段内所有月份
     *
     * @param dBegin
     * @param dEnd
     * @return
     * @throws ParseException
     */
    public static List<Map<String, String>> findDates(Date dBegin, Date dEnd) {
        List<Map<String, String>> lDate = new ArrayList<Map<String, String>>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> startDateMap = new HashMap<String, String>();
        startDateMap.put("date", sd.format(dBegin).toString());
        lDate.add(startDateMap);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            Map<String, String> dateMap = new HashMap<>();
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            dateMap.put("date", sd.format(calBegin.getTime()));
            lDate.add(dateMap);
        }
        return lDate;
    }

    /**
     * 获取年月日时间
     *
     * @return
     */
    public static String currentDateTime() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateformat.format(System.currentTimeMillis());
        return dateStr;
    }

    /***
     * 获去上月同日期
     *
     */
    public static String getLastMonth(String dateStr) {  //2018-12-01
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            Date y = calendar.getTime();
            return sdf.format(y);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获去上年同日期
     *
     */
    public static String getLastYear(String dateStr) {  //2018-12-01
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -1);
            Date y = calendar.getTime();
            return sdf.format(y);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取同期上年数据
     *
     * @return
     */
    public static List<Map<String, String>> getLastYear(List<Map<String, String>> paramList) {  //2018-12-01
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (null != paramList && paramList.size() > 0) {
                for (int i = 0; i < paramList.size(); i++) {
                    Map<String, String> paramMap = paramList.get(i);
                    Date date = sdf.parse(paramMap.get("date"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.YEAR, -1);
                    Date y = calendar.getTime();
                    paramMap.put("lastTime", sdf.format(y));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return paramList;
    }

    public static String currentSystemTime() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateformat.format(System.currentTimeMillis());
        return dateStr;
    }

    /**
     * 获取年月时间
     *
     * @return
     */
    public static String currentMonthTime() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM");
        String dateStr = dateformat.format(System.currentTimeMillis());
        return dateStr;
    }

    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date parse = dateformat.parse(date);

        return parse;
    }

    public static Date stringToDate2(String date) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = dateformat.parse(date);
        return parse;
    }


    public static String dateFormate_exact(String date) throws ParseException {
        if (null != date && !date.equals("") && !date.equals("null")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(date);
            String format = sdf1.format(parse);
            return format;
        } else {
            return null;
        }

    }

    /**
     * 获取日期间相差的天数
     *
     * @param dataMin
     * @param dateMax
     * @return
     */
    public static long getDateDifference(Date dataMin, Date dateMax) {
        long l = dataMin.getTime() - dateMax.getTime() > 0 ? dataMin.getTime() - dateMax.getTime() :
                dateMax.getTime() - dataMin.getTime();
        System.out.println(l/1000+"秒");
        //日期bai相减得到du相差的日期
        long day = (dataMin.getTime() - dateMax.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (dataMin.getTime() - dateMax.getTime()) / (24 * 60 * 60 * 1000) :
                (dateMax.getTime() - dataMin.getTime()) / (24 * 60 * 60 * 1000);
        System.out.println("相差的日期: " +day);
        return day;
    }

    /**
     * 获取指定年月的第一天
     *
     * @param date 2020-10
     * @return 2020-10-01
     */
    public static String getFirstDayOfMonth(String date) {
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        if (null != date) {
            String[] day = date.split("-");
            int year = Integer.parseInt(day[0]);
            int month = Integer.parseInt(day[1]);
            //设置年份
            cal.set(Calendar.YEAR, year);
            //设置月份
            cal.set(Calendar.MONTH, month - 1);
            //获取某月最小天数
            int firstDay = cal.getMinimum(Calendar.DATE);
            //设置日历中月份的最小天数
            cal.set(Calendar.DAY_OF_MONTH, firstDay);
        }
        return sdf.format(cal.getTime());
    }

    /**
     * 获取指定时间的当前月最后一天时间
     *
     * @param time  new Date()
     * @return  2020-10-31
     */
    public static String getLastDate(Date time) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        // 获取到签约日期的当前月的最后一天日期
        Date lastTime = cal.getTime();
        String formatTime = new SimpleDateFormat("yyyy-MM-dd").format(lastTime);
        return formatTime;
    }

    /**
     * 获取当前日期推前2个时间点（可修改），通用，年，月，日
     * 2020，yyyy，Calendar.YEAR
     * 2020-10，yyyy-MM，Calendar.MONTH
     *
     * @param nowTime
     * @param pattern
     * @param cal
     * @return
     * @Author jnyou
     */
    public static String getTwoBefore(String nowTime, String pattern, int cal) throws ParseException {

        // 获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = dateFormat.parse(nowTime);
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(date);
        //设置为前2月，可根据需求进行修改
        calendar.add(cal, -2);
        //获取2个月前的时间
        date = calendar.getTime();

        return dateFormat.format(date);
    }

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse("2020-01-01");
        Date dEnd = sdf.parse("2020-05-02");
        System.out.println(DateUtil.findDates(dBegin,dEnd));

        String nowTime = "2020";
        String pattern = "yyyy";
        String timeBefore = getTwoBefore(nowTime, pattern, Calendar.YEAR);
        System.out.println("您当前传入时间：" + nowTime + "的两个月之前的时间为：" + timeBefore);

        System.out.println(getFirstDayOfMonth("2020-10"));
        System.out.println(getLastDate(new Date()));

        System.out.println(getLastYear("2020-10-21")); // 2019-10-21

    }
}
