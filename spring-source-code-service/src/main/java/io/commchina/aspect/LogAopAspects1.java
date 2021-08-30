package io.commchina.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**

 * 日志切面：

 * @Before：在目标方法之前执行

 * @After：在目标方法执行之后执行

 * @AfterReturning：在目标方法返回之前执行

 * @AfterThrowing：在目标方法抛出异常之后执行，如果异常在@Around捕捉了，那么就不会执行了

 * @Around：在执行方法之前前后执行

 *
 * 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓
 * @Pointcut：点切，指定点

 */
@Aspect
@Component
public class LogAopAspects1 {

    @Pointcut(value = "execution(public int io.commchina.service.LeviService.*(..))")
    public void pointCut() {
        System.out.println("@Pointcut");
    };

    /**
     * 在目标方法开始之前执行
     */
//  @Before("execution(public boolean com.levi.spring.aop.levi02aopbase.LeviService.*(..))")
//    @Before("execution(public int com.levi.spring.aop.levi02aopbase.LeviService.*(..))")
    @Before("pointCut()")
    public void before(JoinPoint joinpoint) {

         System.out.println("@Before：" + joinpoint.getSignature().getName() + " - 参数：" + Arrays.asList(joinpoint.getArgs()));

    }


    /**
     * 在目标方法执行完成之后执行
     */
//    @After("execution(public int com.levi.spring.aop.levi02aopbase.LeviService.*(..))")
    @After("pointCut()")
    public void after(JoinPoint joinpoint) {

         System.out.println("@After：" + joinpoint.getSignature().getName() + " - 参数：" + Arrays.asList(joinpoint.getArgs()));

    }

   

    /**
     * 目标方法执行完成之后的返回
     */
//    @AfterReturning(value="execution(public int com.levi.spring.aop.levi02aopbase.LeviService.*(..))",returning = "result")
    @AfterReturning(value="pointCut()",returning = "result")
    public void afterReturning(JoinPoint joinpoint,Object result) {
         System.out.println("@AfterReturning：" + joinpoint.getSignature().getName() + " - 参数：" + Arrays.asList(joinpoint.getArgs()) + " - 结果：" + result);
    }

   

    /**
     * 目标方法抛出异常之后执行
     */
//    @AfterThrowing(value="execution(public int io.commchina.service.LeviService.myException(..))",throwing = "exception")
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void afterThrowing(JoinPoint joinpoint,Exception exception) {
         System.out.println("@AfterThrowing：" + joinpoint.getSignature().getName() + " - 参数：" + Arrays.asList(joinpoint.getArgs()));
    }

   

    /**
     * 在一个方法之前和执行后的操作
     */
//    @Around("execution(public int com.levi.spring.aop.levi02aopbase.LeviService.*(..))")
    @Around("pointCut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

         System.out.println("@Around：调用执行方法之前的执行时间：" + System.currentTimeMillis());

         try {

             proceedingJoinPoint.proceed(); //执行目标方法

         } catch (Throwable e) {

             System.out.println("@Around：执行目标方法报错");

             //抛出异常，验证@AfterThrowing

//           throw e;

         }

         System.out.println("@Around：调用执行方法之后的执行时间：" + System.currentTimeMillis());

    }

}