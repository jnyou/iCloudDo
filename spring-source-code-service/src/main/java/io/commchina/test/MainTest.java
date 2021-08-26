package io.commchina.test;

import io.commchina.bean.Student;
import io.commchina.config.BeanConfig;
import org.aspectj.bridge.MessageUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
public class MainTest {

    public static void main(String[] args) {
//        toXml();
        toConfig();
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

}