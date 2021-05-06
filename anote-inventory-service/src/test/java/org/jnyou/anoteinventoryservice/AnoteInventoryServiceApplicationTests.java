package org.jnyou.anoteinventoryservice;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class AnoteInventoryServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        DateTime date = DateUtil.date();
        Date uploadTimeBegin = DateUtil.beginOfDay(date);
        System.out.println(date);
        System.out.println(uploadTimeBegin);
    }

}
