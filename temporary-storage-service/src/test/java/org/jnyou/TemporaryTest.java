package org.jnyou;

/**
 * 分类名称
 *
 * @ClassName TemporaryTest
 * @Description: 测试
 * @Author: jnyou
 **/
public class TemporaryTest {

    public static void main(String[] args) {
        // 断言判空操作 通过则继续运行，不通过则抛出异常：Exception in thread "main" java.lang.AssertionError
        String str = new String("HT");
        assert str.equalsIgnoreCase("HT");
        System.out.println(str);
    }

}