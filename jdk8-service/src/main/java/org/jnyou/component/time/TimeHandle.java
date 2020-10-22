package org.jnyou.component.time;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * 分类名称
 *
 * @ClassName TimeHandle
 * @Description: 时间处理机制
 * @Author: jnyou
 **/
public class TimeHandle {


    public static void main(String[] args) {
        // 获取今天的日期
        now();
        // 获取年、月、日信息
        yearMonthDay();
        System.out.println("\n");
        // 处理特定(自定义)时间
        specificTime();
        // 判断两个日期是否相等
        boolTime();
        //获取周期性的时间
        monthDay();
        // 使用近亲的localtime来获取当前的时间
        localTime();
        // 操作时间
        opeartorTime();
        // 判断时间是在某个日期的前或者后面
        boolAfterOrBefore();
        // 固定日期（比如二月是28天还是29天）
        yearMonth();

    }

    /**
     * 获取今天的日期
     *
     * @return
     * @Author jnyou
     */
    public static void now() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
    }


    /**
     * 获取年、月、日信息
     *
     * @Author jnyou
     */
    public static void yearMonthDay() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();
        System.out.println("当前是" + year + "年 " + monthValue + "月 " + dayOfMonth + "日 ");
        System.out.printf("year = %d, month = %d, day = %d", year, monthValue, dayOfMonth);
    }

    /**
     * 处理特定(自定义)时间
     *
     * @Author jnyou
     */
    public static void specificTime() {
        LocalDate specificTime = LocalDate.of(2020, 01, 02);
        System.out.println(specificTime);
    }

    /**
     * 判断两个日期是否相等
     *
     * @Author jnyou
     */
    public static void boolTime() {
        LocalDate date = LocalDate.now();
        LocalDate of = LocalDate.of(2020, 10, 21);
        if (date.equals(of)) {
            System.out.println("是同一天");
        } else {
            System.out.println("不是同一天");
        }
    }

    /**
     * 获取周期性时间
     *
     * @Author jnyou
     */
    public static void monthDay() {
        LocalDate now = LocalDate.now();

        LocalDate dateOfBirth = LocalDate.of(2018, 06, 20);

        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());

        MonthDay currentMonthDay = MonthDay.from(now);

        if (currentMonthDay.equals(birthday)) {

            System.out.println("Happy Birthday");

        } else {

            System.out.println("Sorry, today is not your birthday");

        }
    }

    /**
     * 使用近亲localtime来获取当前时间
     *
     * @Author jnyou
     */
    public static void localTime() {
        // 格式为： hh:mm:ss:nnn
        LocalTime now = LocalTime.now();
        System.out.println(now);
    }

    /**
     * 时间操作（增减等 ）
     *
     * @Author jnyou
     */
    public static void opeartorTime() {
        // 两小时后
        LocalTime localTime = LocalTime.now().plusHours(2);
        System.out.println(localTime);

        //三天后
        LocalDate afterThreeDays = LocalDate.now().plusDays(3);
        //三天前
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        System.out.println("三天后是" + afterThreeDays);
        System.out.println("三天前是" + threeDaysAgo);

        // 相差天数
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.parse("2020-01-30");
        long day1 = now.toEpochDay();
        long day2 = date.toEpochDay();
        //相差多少天
        long day = day2 - day1;
        System.out.println(day);

        // 相差月数
        LocalDate date1 = LocalDate.of(2020, Month.MARCH, 20);
        Period period = Period.between(now, date1);
        System.out.println("离下个时间还有" + period.getMonths() + " 个月");

        // 一周后的日期
        LocalDate localDate = LocalDate.now().plusWeeks(1);
        LocalDate plus = LocalDate.now().plus(1, ChronoUnit.WEEKS);
        System.out.println(localDate);
        System.out.println(plus);

        // 一年前
        LocalDate minusDate = LocalDate.now().minusYears(1);
        LocalDate.now().minus(1, ChronoUnit.YEARS);
        // 一年后
        LocalDate plusDate = LocalDate.now().plusYears(1);
        LocalDate.now().plus(1, ChronoUnit.YEARS);
        System.out.println(minusDate);
        System.out.println(plusDate);
    }

    /**
     * 如何用 Java 判断日期是早于还是晚于另一个日期
     *
     * @Author jnyou
     */
    public static void boolAfterOrBefore() {

        LocalDate tomorrow = LocalDate.of(2020, 10, 23);

        LocalDate now = LocalDate.now();

        if (tomorrow.isAfter(now)) {
            System.out.println("Tomorrow comes after today");
        }

        LocalDate yesterday = now.minus(1, ChronoUnit.DAYS);

        if (yesterday.isBefore(now)) {
            System.out.println("Yesterday is day before today");
        }
    }

    /**
     * 判断固定日期（比如二月是28天还是29天）
     * @Author jnyou
     */
    public static void yearMonth() {
        YearMonth currentYearMonth = YearMonth.now();

        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());

        YearMonth creditCardExpiry = YearMonth.of(2018, Month.FEBRUARY);

        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
    }

    /** 检查闰年 **/
    public static void leapYear() {
        boolean leapYear = LocalDate.now().isLeapYear();
        System.out.println(leapYear);
    }



}