package com.blithe.cms.log;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.aspectj.lang.annotation.RequiredTypes;

import java.lang.annotation.*;

/**
 * @ClassName yjn
 * @Description: 系统日志元注解
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Target(value = {ElementType.METHOD})  // 注解放在方法上面
@Retention(RetentionPolicy.RUNTIME)    // 运行时
@Documented
public @interface SysLog {

    String value() default "";

}