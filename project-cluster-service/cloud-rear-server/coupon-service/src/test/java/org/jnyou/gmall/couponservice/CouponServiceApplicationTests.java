package org.jnyou.gmall.couponservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.support.TestPropertySourceUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//@SpringBootTest
class CouponServiceApplicationTests {

    @Test
    void contextLoads() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        LocalDate date = now.plusDays(2);
        System.out.println(date);

        LocalTime min = LocalTime.MIN;
        LocalTime max = LocalTime.MAX;
        System.out.println(min);
        System.out.println(max);

        LocalDateTime startTime = LocalDateTime.of(now, min);
        LocalDateTime endTime = LocalDateTime.of(date, max);
        System.out.println(startTime);
        System.out.println(endTime);

    }

}
