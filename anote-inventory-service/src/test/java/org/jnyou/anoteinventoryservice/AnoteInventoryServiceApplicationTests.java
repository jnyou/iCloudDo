package org.jnyou.anoteinventoryservice;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class AnoteInventoryServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        DateTime yesterDay = DateUtil.offsetDay(DateUtil.date(), -1);
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
        Date yesterdayBegin = DateUtil.beginOfDay(yesterDay);
        Date yesterdayEnd = DateUtil.endOfDay(yesterDay);
        System.out.println(DateUtil.format(yesterDay, "yyyyMM"));
        System.out.println(yesterDay);
        System.out.println(yesterdayBegin);
        System.out.println(yesterdayEnd);

        DateTime beginOfMonth = DateUtil.beginOfMonth(new Date());
        System.out.println(beginOfMonth);
        System.out.println(DateUtil.endOfMonth(new Date()));

        System.out.println(DateUtil.date());

        Date date = Convert.toDate("2020-5-21", new Date());
        System.out.println(date);


    }

}
