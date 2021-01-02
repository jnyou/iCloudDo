package org.jnyou.anoteinventoryservice.tools.date;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理工具
 */
public class DateUtils {

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String FORMAT_yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式(yyyyMMddHHmmss)
     */
    public final static String FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    /**
     * 时间格式(yyyyMMdd)
     */
    public final static String FORMAT_yyyyMMdd = "yyyyMMdd";
    /**
     * 时间格式(yyyyMMddHHmmss)
     */
    public final static String FORMAT_HHmmss = "HHmmss";
    public final static String FORMAT_HH_mm_ss = "HH:mm:ss";

    /**
     * 格式化为：时间格式(yyyy-MM-dd)的字符串
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, FORMAT_yyyy_MM_dd);
    }

    public static String formatsfm(Date date) {
        return format(date, FORMAT_HH_mm_ss);
    }

    public static String formatTime(Date date) {
        return format(date, FORMAT_yyyy_MM_ddHHmmss);
    }

    /**
     * 格式化为：指定格式的字符串，如：时间格式(yyyy-MM-dd)
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 返回当前年份
     *
     * @return int 年份值
     */
    public static final int getCurrentYear() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 返回当前月份
     *
     * @return int 月份值
     */
    public static final int getCurrentMonth() {

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 返回当前日
     *
     * @return int 当前日
     */
    public static final int getCurrentDay() {

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 返回当前小时24小时制
     *
     * @return int 当前日
     */
    public static final int getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 日期加减方法
     */
    public static final Date addDay(Date dDate, int days) {
        return addDate(dDate, days, Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能说明：加减年，根据日期向前或向后推算N年
     *
     * @param dDate 日期
     * @param years 年份数 正整数或负整数
     * @return 返回增加年份后的日期对象
     */
    public static final Date addYear(Date dDate, int years) {
        return addDate(dDate, years, Calendar.YEAR);
    }

    /**
     * 功能说明：加减月，根据日期向前或向后推算N个月
     *
     * @param dDate  日期
     * @param months 月份数 正整数或负整数
     * @return 返回增加月份后的日期对象
     */
    public static final Date addMonth(Date dDate, int months) {
        return addDate(dDate, months, Calendar.MONTH);
    }

    /**
     * 功能说明：加减日期
     *
     * @param dDate  日期对象
     * @param field  指定是年、月、日
     * @param amount 是数量
     * @return 修改后的日期
     */
    public static Date addDate(Date dDate, int amount, int field) {
        GregorianCalendar cl = new GregorianCalendar();
        cl.setTime(dDate);
        cl.add(field, amount);

        return cl.getTime();
    }

    public static String getCurrentTimeString() {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_ddHHmmss);
        String systemTime = null;
        try {
            systemTime = sdf.format(new Date()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return systemTime;
    }

    /**
     * 返回Date型当前时间 2011/7/21 10:20:02
     *
     * @return 当前时间
     * @throws ParseException 异常
     */
    public static Date getCurrentTime() {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_ddHHmmss);
        String systemTime = sdf.format(new Date()).toString();
        Date returnDate = null;
        try {
            returnDate = sdf.parse(systemTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 把给定的日字符串转换成Date类型
     *
     * @param sdate 日期
     * @return Date
     * @throws ParseException 异常
     */
    public static Date convertStringToDate(String sdate) throws ParseException {

        return convertStringToDate(sdate, FORMAT_yyyy_MM_dd);
    }

    /**
     * 字符串类型转日期类型
     *
     * @param sdate
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String sdate, String format) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(sdate);
    }

    /**
     * 功能说明：计算两个日期之间相差的天数
     *
     * @param dDate1 ：较小日期
     * @param dDate2 ：较大日期
     * @return 相差天数
     */
    public static int dateDiff(Date dDate1, Date dDate2) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_dd);
        try {
            dDate1 = sdf.parse(sdf.format(dDate1));
            dDate2 = sdf.parse(sdf.format(dDate2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(dDate2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 功能说明：通过只算年份和月份，获取月份差，不计四舍五入
     *
     * @param dDate1 Date：起始月日期
     * @param dDate2 Date：终止月日期
     * @return int：dDate2-dDate1的月份差
     */
    public static int monthDiff(Date dDate1, Date dDate2) {
        int year, month;
        GregorianCalendar cld = new GregorianCalendar();

        cld.setTime(dDate2);
        year = cld.get(Calendar.YEAR);
        month = cld.get(Calendar.MONTH);

        cld.setTime(dDate1);
        year -= cld.get(Calendar.YEAR);
        month -= cld.get(Calendar.MONTH);

        return year * 12 + month;
    }

    /**
     * 功能说明：计算当前10位时间戳入库
     *
     * @return Integer时间戳
     */
    public static Integer getCurrentTimestamp() {
        return Integer.valueOf((System.currentTimeMillis() + "").substring(0, 10));
    }

    /**
     * 功能说明：依据类型返回日期中的元素
     */
    public static int getDateItems(Date dDate, int field) {
        GregorianCalendar cl = new GregorianCalendar();
        cl.setTime(dDate);

        return cl.get(field);
    }

    /**
     * 功能说明：返回给定日期的具体年份
     *
     * @param dDate 日期
     * @return
     */
    public static int getYear(Date dDate) {
        return getDateItems(dDate, Calendar.YEAR);
    }

    /**
     * 功能说明：返回给定日期的具体月份
     *
     * @param dDate 日期
     * @return int 月
     */
    public static int getMonth(Date dDate) {
        return getDateItems(dDate, Calendar.MONTH) + 1;
    }

    /**
     * 功能说明：返回给定日期为当月中具体日
     *
     * @param dDate 日期
     * @return int 日
     */
    public static int getDay(Date dDate) {
        return getDateItems(dDate, Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能说明：返回给定日期为星期几的标识位
     *
     * @param dDate 日期
     * @return 星期几标识位 2：星期一 3：星期二 4：星期三 5：星期四 6：星期五 7：星期六 1：星期日
     */
    public static int getWeekDay(Date dDate) {
        return getDateItems(dDate, Calendar.DAY_OF_WEEK);
    }

    /**
     * 功能说明：返回给定日期当年天数
     *
     * @param dDate 日期
     * @return int 年天数；平年365，闰年366。
     */
    public static int getYearDays(Date dDate) {
        return getDateItems(dDate, Calendar.DAY_OF_YEAR);
    }

    /**
     * 取当前日期对应的上一月末最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayBM(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 当前日期去年月末最后一天
     * @param date
     * @return
     */
    public static Date getCurrLastMonthDayBM(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_yyyy_MM_ddHHmmss);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        df.format(cal.getTime());
        return cal.getTime();
    }

    /**
     * 获取月份最后一天的日期 例如：date = 2011-1-23 getMonthLastDay(date) = 2011-1-31
     */
    public static Date getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /**
     *  获取月份第一天的日期 例如：date = 2011-1-23 getMonthLastDay(date) = 2011-1-1
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /**
     * 功能说明：返回月末日
     *
     * @param year  年
     * @param month 月
     * @return 指定年、月的月末日
     */
    public static int endOfMonth(int year, int month) {

        return new GregorianCalendar(year, month - 1, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取当前日期对应的上一年末最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayBY(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 功能说明：将指定格式的时间格式化为10位时间戳
     *
     * @return Integer时间戳
     * @Param time 时间字符串
     * @Param format 时间格式
     */
    public static Integer stringToTimestamp(String time, String format, String type) throws ParseException {
        if (time == null || "".equals(time)) {
            throw new RuntimeException(type + "时间不合法");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(time);
        if (date.getTime() > System.currentTimeMillis()) {
            throw new RuntimeException(type + "时间不合法");
        }
        return Integer.valueOf((date.getTime() + "").substring(0, 10));
    }

    /**
     * 功能说明：验证所给时间是否和当前时间相差超过指定时间
     *
     * @return Boolean
     * @Param time 时间字符串
     * @Param diff 指定时间毫秒
     */
    public static Boolean measure(String time, Integer diff) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_yyyyMMddHHmmss);
        Date date = simpleDateFormat.parse(time);
        long dif = System.currentTimeMillis() - date.getTime();
        //允许比当前时间大10S
        if (dif < -10000) {
            return false;
        }
        dif = diff - dif;
        if (dif > 0) {
            return true;
        }
        return false;
    }

    /**
     * 功能说明：获取指定季度的时间范围
     *
     * @param quarter 1:第一季度，2：第二季度……以此类推
     * @return HahsMap startCalendar季度开始时间，endCalendar 季度结束时间
     */
    public static Map<String, Date> getLastQuarter(int quarter) {
        if (quarter > 4) {
            quarter = 4;
        }
        if (quarter < 1) {
            quarter = 1;
        }
        int startMonth = quarter * 3 - 2;
        int endMonth = quarter * 3;
        return getLastQuarter(startMonth - 1, endMonth - 1);
    }

    /**
     * 功能说明：获取上个季度的时间范围
     *
     * @return HahsMap startCalendar 季度开始时间，endCalendar 季度结束时间
     */
    public static Map<String, Date> getLastQuarter() {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        int startMonth = (startCalendar.get(Calendar.MONTH) / 3 - 1) * 3;
        int endMonth = (endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2;
        return getLastQuarter(startMonth, endMonth);
    }

    /**
     * 以某个时间为准，获取它的上一个季度时间范围
     *
     * @param date 日期
     */
    public static Map<String, Date> getLastQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -3);

        int q = cal.get(Calendar.MONTH) / 3 + 1;
        int startMonth = q * 3 - 2 - 1;
        int endMonth = q * 3 - 1;
        return getLastQuarter(cal.getTime(), startMonth, endMonth);
    }

    /**
     * 功能说明：以当前时间为准，获取某两个月份之间的时间范围
     *
     * @param startMonth 开始月份
     * @param endMonth   结束月份
     * @return HahsMap startCalendar季度开始时间，endCalendar 季度结束时间
     */
    public static Map<String, Date> getLastQuarter(int startMonth, int endMonth) {
        return getLastQuarter(null, startMonth, endMonth);
    }

    /**
     * 功能说明：以某个时间为准，获取某两个月份之间的时间范围
     *
     * @param startMonth 开始月份
     * @param endMonth   结束月份
     * @return HahsMap startCalendar季度开始时间，endCalendar 季度结束时间
     */
    public static Map<String, Date> getLastQuarter(Date date, int startMonth, int endMonth) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.DAY_OF_MONTH, 1);
        if (date != null) {
            startCalendar.setTime(date);
            endCalendar.setTime(date);
        }
        startCalendar.set(Calendar.MONTH, startMonth);

        setMinTime(startCalendar);
        endCalendar.set(Calendar.MONTH, endMonth);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        Map<String, Date> mapCalendar = new HashMap<>();
        mapCalendar.put("startCalendar", startCalendar.getTime());
        mapCalendar.put("endCalendar", endCalendar.getTime());
        return mapCalendar;
    }

    /**
     * 功能说明：设置日历最小时间（HOUR_OF_DAY，MINUTE，SECOND，MILLISECOND）
     *
     * @param calendar 需要设置时间的对象
     */
    private static void setMinTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 功能说明：设置日历最大时间（HOUR_OF_DAY，MINUTE，SECOND，MILLISECOND）
     *
     * @param calendar 需要设置时间的对象
     */
    private static void setMaxTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }

    /**
     * 代表数组里的年、月、日
     */
    private static final int Y = 0, M = 1, D = 2;

    private static transient int gregorianCutoverYear = 1582;

    /**
     * 闰年中每月天数
     */
    private static final int[] DAYS_P_MONTH_LY = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 非闰年中每月天数
     */
    private static final int[] DAYS_P_MONTH_CY = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 获取两个日期的时间间隔中的每一天
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<String> getEveryday(String beginDate, String endDate) {
        long days = countDay(beginDate, endDate);
        int[] ymd = splitYMD(beginDate);
        List<String> everyDays = new ArrayList<String>();
        everyDays.add(beginDate);
        for (int i = 0; i < days; i++) {
            ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);
            everyDays.add(formatYear(ymd[Y]) + "-" + formatMonthDay(ymd[M]) + "-" + formatMonthDay(ymd[D]));
        }
        return everyDays;
    }

    /**
     * 计算两个日期相隔天数
     *
     * @param begin
     * @param end
     * @return
     */
    public static long countDay(String begin, String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate, endDate;
        long day = 0;
        try {
            beginDate = format.parse(begin);
            endDate = format.parse(end);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 将代表日期的字符串分割为代表年月日的整形数组
     *
     * @param date
     * @return
     */
    public static int[] splitYMD(String date) {
        date = date.replace("-", "");
        int[] ymd = {0, 0, 0};
        ymd[Y] = Integer.parseInt(date.substring(0, 4));
        ymd[M] = Integer.parseInt(date.substring(4, 6));
        ymd[D] = Integer.parseInt(date.substring(6, 8));
        return ymd;
    }

    /**
     * 检查传入的参数代表的年份是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return year >= gregorianCutoverYear ?
                ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) : (year % 4 == 0);
    }

    /**
     * 功能说明：返回指定日期区间段呢包含2月29日的个数。
     *
     * @param sDate：起始日期（含）
     * @param eDate：结束日期（含）
     * @return 2月29日的个数
     * @throws Exception
     */
    public static int getLeapYears(Date sDate, Date eDate) throws Exception {
        int leapDays = 0;
        Date dDate;
        for (int i = getYear(sDate); i <= getYear(eDate); i++) {
            if (isLeapYear(i)) {
                dDate = convertStringToDate(i + "-02-29");
                if (dateDiff(dDate, sDate) <= 0 && dateDiff(dDate, eDate) >= 0) {
                    leapDays++;
                }
            }
        }
        return leapDays;
    }

    /**
     * 日期加1天
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static int[] addOneDay(int year, int month, int day) {
        if (isLeapYear(year)) {
            day++;
            if (day > DAYS_P_MONTH_LY[month - 1]) {
                month++;
                if (month > 12) {
                    year++;
                    month = 1;
                }
                day = 1;
            }
        } else {
            day++;
            if (day > DAYS_P_MONTH_CY[month - 1]) {
                month++;
                if (month > 12) {
                    year++;
                    month = 1;
                }
                day = 1;
            }
        }
        int[] ymd = {year, month, day};
        return ymd;
    }

    /**
     * 将不足两位的月份或日期补足为两位
     *
     * @param decimal
     * @return
     */
    public static String formatMonthDay(int decimal) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(decimal);
    }

    /**
     * 将不足四位的年份补足为四位
     *
     * @param decimal
     * @return
     */
    public static String formatYear(int decimal) {
        DecimalFormat df = new DecimalFormat("0000");
        return df.format(decimal);
    }

    /**
     * 日期字符串转为日期（yyyy-MM-dd HH:mm:ss）如果出错就返回空
     *
     * @param dateStr
     * @return
     */
    public static Date StringToDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_yyyy_MM_ddHHmmss);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前日期格式为时间格式(yyyy-MM-dd) 2017-10-17
     *
     * @return
     */
    public static String getCurrentDateFormat() {
        return format(getCurrentTime(), FORMAT_yyyy_MM_dd);
    }

    /**
     * 获取当前日期格式为yyyyMMdd 20170930
     *
     * @return
     */
    public static String getCurrentDate() {
        return format(getCurrentTime(), FORMAT_yyyyMMdd);
    }

    /**
     * 获取当前日期格式为HHmmss 141054
     *
     * @return
     */
    public static String getCurrentTimeFormat() {
        return format(getCurrentTime(), FORMAT_HHmmss);
    }

    /**
     * 获取当天指定时间的时间戳
     *
     * @param time 时间（12点，14点等）
     * @return
     */
    public static Long getAppointTime(Double time) {
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        Double zeroD = Double.valueOf(zero);
        Double timeD = time * 60 * 60 * 1000;
        Double currentTime = zeroD + timeD;
        long twelve = currentTime.longValue();
        return twelve;
    }

    /**
     * 获取当天零点
     *
     * @return
     */
    public static String getCurrentDateForZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_yyyy_MM_ddHHmmss);
        String d = format.format(zero);
        return d;
    }

    /**
     * 获取当天指定时间的日期
     *
     * @param time 时间（12点，14点等）
     * @return
     */
    public static String getAppointChar(Integer time) {
        Long currenttime = getAppointTime(Double.valueOf(time));
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_yyyy_MM_ddHHmmss);
        String d = format.format(currenttime);
        return d;
    }

    /**
     * 获取当前日期周一的零点日期
     *
     * @return
     */
    public static String getMondayChar() {
        Date date = new Date();
        Integer week = DateUtils.getWeekDay(date);
        //起始日与当天的差额
        Integer disparity = 0;
        if (week == 1) {
            disparity = 6;
        } else {
            disparity = week - 2;
        }
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        long twelve = zero - disparity * 24 * 60 * 60 * 1000;
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_yyyy_MM_ddHHmmss);
        String d = format.format(twelve);
        return d;
    }

    /**
     * 取当前日期对应的上N年末最后一天 格式 2014-12-31
     *
     * @param n
     * @return
     */
    public static String getLastDayBYN(int n) {

        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, n);//加一个年
        lastDate.set(Calendar.MONTH, 11);
        lastDate.set(Calendar.DAY_OF_MONTH, 31);

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 取指定日期对应的上N年末最后一天 格式 2014-12-31:23:59:59
     * @param date
     * @param n
     * @return
     */
    public static String getLastDayBYN(Date date,int n) {
        String formatEnd = "yyyy-MM-dd 23:59:59";
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatEnd);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, n);//加一个年
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        str = sdf.format(cal.getTime());
        return str;
    }



    /**
     * 取当前日期对应的上N年前第一天 格式 2014-12-31
     *
     * @param n
     * @return
     */
    public static String getFirstDayBYN(int n) {

        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, n);//加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 取指定日期对应的上N年前第一天 格式 2014-12-31 00:00:00
     * @param date
     * @param n
     * @return
     */
    public static String getFirstDayBYN(Date date,int n) {
        String formatStart = "yyyy-MM-dd 00:00:00";
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStart);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, n);//加一个年
        cal.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(cal.getTime());
        return str;
    }

    /**
     * 取指定日期对应的上N年前第一天 格式 2014-12-31
     *
     * @param n
     * @return
     */
    public static String getFirstDayBYNAndPoint(int n, String timeStr) {
        String str = "";
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(timeStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.add(Calendar.YEAR, n);//加一个年
            calendar.set(Calendar.DAY_OF_YEAR, 1);

            str = sdf.format(calendar.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取当年第一天
     *
     * @return 2017-1-1
     */
    public static String getFirstDayBY() {

        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DAY_OF_YEAR, 1);

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 把yyyymmdd转成yyyy-MM-dd格式
     *
     * @param str
     * @return
     */
    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return sfstr;
    }

    /**
     * 把yyyy-MM-dd转成yyyymmdd格式
     *
     * @param str
     * @return
     */
    public static String formatDateConverse(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return sfstr;
    }


    /**
     * 判断是否为今天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean isToday(Date day) throws ParseException {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = day;
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param day
     * @return 是否为历史时间
     * @throws ParseException
     */
    public static boolean isHisDay(Date day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date  s_day =sdf.parse(sdf.format(day));
        Date date = new Date();
        Date  s_date =sdf.parse(sdf.format(date));
        //在日期字符串非空时执行
        //将字符串转为日期格式，如果此处字符串为非合法日期就会抛出异常。
        //调用Date里面的before方法来做判断
        boolean flag = s_day.before(s_date);
        if (flag) {
            return  true;
        }
        return false;
    }

    /**
     *
     * @param day
     * @return 是否为未来时间
     * @throws ParseException
     */
    public static boolean isPreDay(Date day) throws ParseException {
        Date nowDate = new Date();
        //调用Date里面的before方法来做判断
        boolean flag = day.before(nowDate);
        if (!flag) {
            return  true;
        }
        return false;
    }

    /**
     *
     * @param
     * @return 是否在某个时间点的后面
     * @throws ParseException
     */
    public static boolean isAfterTime(String time) throws ParseException {
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowString = sf2.format(nowDate);
        String preString  = sdf.format(nowDate)+" "+time;
        //调用Date里面的before方法来做判断
        nowDate = sf2.parse(nowString);
        Date mDate = sf2.parse(preString);
        boolean flag =nowDate.after(mDate);
        if (flag) {
            return  true;
        }
        return false;
    }

    /*
     * 将时间戳(毫秒，14位)转换为时间
     * 如 1558409364000
     */
    public static Date stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        //2019-05-21 11:00:00
        //res = simpleDateFormat.format(date);
        return date;
    }

    /**
     * 获取当前时间离一天结束剩余秒数
     * @param currentDate
     * @return
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        Calendar midnight=Calendar.getInstance();
        midnight.setTime(currentDate);
        midnight.add(Calendar.DAY_OF_MONTH,1);
        midnight.set(Calendar.HOUR_OF_DAY,0);
        midnight.set(Calendar.MINUTE,0);
        midnight.set(Calendar.SECOND,0);
        midnight.set(Calendar.MILLISECOND,0);
        Integer seconds=(int)((midnight.getTime().getTime()-currentDate.getTime())/1000);
        return seconds;
    }

    /**
     * 获取指定日前的前一天
     * @strData：参数格式：yyyy-MM-dd
     * @return：返回格式：yyyy-MM-dd
     */
    public static Date getPreDateByDate(String strData) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(strData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 - 1);
        return c.getTime();
    }
    public static Date getPreDateByDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 - 1);
        return c.getTime();
    }
    /**
     * 比较两日期大小
     *
     * @return 比较两日期大小
     */
    public static int compareDate(String date1, String date2, String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
//                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
//                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    public static void main(String args[]) {

      /*  System.out.println(format(getCurrentTime(), FORMAT_HHmmss));
        String s = "20170912";
        try {
            Date s1 = convertStringToDate(formatDate(s));

            Map firstDayBYN = getLastQuarter(s1);
            Date endCalendar = (Date) firstDayBYN.get("endCalendar");
            String format = format(endCalendar);
            System.out.print(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        String a = DateUtils.getAppointChar(11);
        long b = DateUtils.getAppointTime(11.49);
        Date c = DateUtils.stampToDate(String.valueOf(b));
        Date d = new Date();
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);

    }

}
