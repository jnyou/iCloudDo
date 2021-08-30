package io.commchina.test;

import io.commchina.bean.Student;
import io.commchina.config.BeanConfig;
import io.commchina.service.LeviService;
import org.aspectj.bridge.MessageUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * <p>
 * MainTest
 *
 * @version 1.0.Release
 * @author: youjiannan
 * @description MainTest
 **/
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(value = {"io.commchina.aspect","io.commchina.service"})
public class MainTest {

    public static void main(String[] args) throws Exception {
//        toXml();
//        toConfig();
        aop();
    }


    public static void toXml() {
        // 获取到xml后，调用构造初始化bean对象
        ApplicationContext cx = new ClassPathXmlApplicationContext("spring-so.xml");
        // 从容器中获取bean对象
        Student student = cx.getBean(Student.class);
        System.out.println(student.getName());
    }

    public static void toConfig() {
        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        Student bean = annotationConfigApplicationContext.getBean(Student.class);
        System.out.println(bean.getName());
    }

    /**

     * Aop编程：与传统的OOP（Object Oraentd Programming）面向对象对比，传统的OOP开发中的代码逻辑是自上而下的，

     * 在这些自上而下的代码中会产生横切面的问题，而这些横切面又与主业务逻辑关系不大，但是会散落在代码的各个地方，造成难以维护。

     * AOP的编程思想就是把这些横切性的问题和主业务逻辑进行分离，从而起到了解耦的目的。

     * 比如在每一个Service或Controller方法上加上日志打印，那么就要在每个方法都加上这个打印了，而如果用AOP就可以直接定位

     * 到这个包，只要访问这个包下的方法就会统一执行这个日志打印。

     *

     * 1、定义好AOP切面类，并写好对应的切点（execution）

     * 2、将AOP切面类和业务类放入容器中（代码中：@Component、@Service）

     * 3、基于注解开启AOP，@EnableAspectJAutoProxy

     */

    /*

     * 首次执行会发现为什么AOP没有起作用。

     * AOP是相当于一个Spring功能模块，要用这个功能模块要开启才行。

     * 要在MainConfig中加上@EnableAspectJAutoProxy

     *

     * 加了@EnableAspectJAutoProxy发现还是不行。

     * 还要在对应的AOP配置类中，标识说明这个类是AOP编程的类

     * 在LogAopAspects1中加上@Aspect

     */
    public static void aop() throws Exception {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainTest.class);
        LeviService leviService = annotationConfigApplicationContext.getBean(LeviService.class);
        leviService.myException(3);
        annotationConfigApplicationContext.close();
    }


}