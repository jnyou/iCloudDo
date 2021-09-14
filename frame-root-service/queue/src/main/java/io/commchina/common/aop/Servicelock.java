package io.commchina.common.aop;
import java.lang.annotation.*;

/**
 * 自定义注解 同步锁
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface Servicelock { 
	 String description()  default "";
}
