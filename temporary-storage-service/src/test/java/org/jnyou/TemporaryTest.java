package org.jnyou;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName TemporaryTest
 * @Author: jnyou
 * <>b</>
 **/
public class TemporaryTest {

    public static void main(String[] args) {
        // 断言判空操作 通过则继续运行，不通过则抛出异常：Exception in thread "main" java.lang.AssertionError

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);

    }

}