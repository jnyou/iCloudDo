package org.jnyou.component.optional;

import org.jnyou.entity.Personal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * 分类名称
 *
 * @ClassName OptionalDemo
 * @Description: 使用Option来改造你的代码
 * @Author: jnyou
 **/
public class OptionalEx {

    private static final Logger logger = LoggerFactory.getLogger(OptionalEx.class);

    public static void main(String[] args) throws Exception {
        /***
         * 1、输出商品的id
         */
        Personal personal = loadData();
        //改造前
        if (personal != null) {
            System.out.println(personal.getId());
        }

        //改造后
        Optional.ofNullable(personal)
                .map(Personal::getId)
                .ifPresent(System.out::println);

        Assert.hasText("==================================================过渡线==================================================");

        /***
         * 2、当用户没有年龄时，使用默认值20岁
         */
        //改造前
        int age1 = 0;
        if (personal != null) {
            if (personal.getId() == null) {
                age1 = 20;
            } else {
                age1 = personal.getAge();
            }
        }

        //改造后
        int age2 = Optional.ofNullable(personal)
                .map(Personal::getId)
                .orElse(20);

        Assert.hasText("==================================================过渡线==================================================");


        /***
         * 3、当用户的姓名为空时，抛出异常
         */

        //改造前
        if (personal != null) {
            String name = personal.getUsername();
            if (name == null) {
                throw new Exception();
            }
        }

        //改造后
        Optional.ofNullable(personal)
                .map(Personal::getUsername)
                .orElseThrow(Exception::new);


        /***
         * 4、当用户的年龄大于18岁时，输出一个其大写形式的姓名，当姓名不存在时，输出Unknown
         */

        //改造前
        if (personal != null) {
            int age = personal.getAge();
            if (age > 18) {
                String name = personal.getUsername();
                if (name != null) {
                    System.out.println(name);
                } else {
                    System.out.println("Unknown");
                }
            }
        }


        /**
         * Java9对Optional的增强：
         * public Optional<T> or(Supplier<? extends Optional<? extends T>> supplier)
         * or 方法的作用是，如果一个 Optional 包含值，则返回自己；否则返回由参数 supplier 获得的 Optional
         */
        //改造后  map简单的映射操作
//        () -> Optional.ofNullable(personal)
//                .filter(u -> u.getAge() > 18)
//                .map(Personal::getUsername)
//                .map(String::toUpperCase)
//                .or(() -> Optional.of("Unknown"))
//                .ifPresent(System.out::println);


    }



    /**
     * 总结
     * Optional对多层判空嵌套有奇效。当然，如果只是简单的单层判空，确实没有必要去引入Optional。
     *
     */


    public static Personal loadData() {
        Personal personal = new Personal();
        personal.setId(1001);
        personal.setAge(18);
        personal.setUsername("jnyou");
        personal.setPassword("shagua30");
        return personal;
    }


}