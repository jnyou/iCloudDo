package org.jnyou.gmall.productservice.annotation;

import java.lang.annotation.*;

/**
 * @author jnyou
 */
@Target({ElementType.METHOD}) // 该注解作用于修饰方法
@Retention(RetentionPolicy.RUNTIME) // 注解生命周期
@Documented // 表明这个注解应该被 javadoc 工具记录
public @interface EagleEye {

    /**
     * 接口描述
     */
    String desc() default "";

}
