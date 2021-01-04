package org.jnyou.anoteinventoryservice.tools.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author jnyou
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
