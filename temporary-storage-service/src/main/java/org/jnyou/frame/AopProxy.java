package org.jnyou.frame;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 分类名称
 *
 * @ClassName AopProxy
 * @Description:
 * @Author: jnyou
 **/
@Aspect
@Component
public class AopProxy {

    @Before("execution(* org.jnyou.frame.AService.testAopProxy())")
    public void test(){
        System.out.println("测试切面");
    }

}