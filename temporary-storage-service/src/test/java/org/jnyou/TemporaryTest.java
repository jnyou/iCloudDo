package org.jnyou;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName TemporaryTest
 * @Author: jnyou
 * <>b</>
 **/
public class TemporaryTest {

    public static void main(String[] args) {
        // 断言判空操作 通过则继续运行，不通过则抛出异常：Exception in thread "main" java.lang.AssertionError
        String str = new String("HT");
        try {
            assert str.equalsIgnoreCase("HT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(str);


        AtomicReference<BigDecimal> atomic = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
        atomic.updateAndGet(v -> v.add(BigDecimal.valueOf(10)));
        boolean bool = atomic.compareAndSet(atomic.get(), BigDecimal.valueOf(10));
        System.out.println("simpleObject  Value: " + bool);
        System.out.println(atomic.get());

    }

}