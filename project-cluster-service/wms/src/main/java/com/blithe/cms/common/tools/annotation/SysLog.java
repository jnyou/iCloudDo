package com.blithe.cms.common.tools.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author lujinjun
 * @date 2017-6-13
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
