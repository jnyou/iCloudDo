package io.commchina.config;

import io.commchina.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * BeanConfig
 *
 * @version 1.0.Release
 * @author: youjiannan
 * @description BeanConfig
 **/
@Configuration
public class BeanConfig {

    @Bean
    public Student student(){
        Student student = new Student();
        student.setName("abc");
        return student;
    }

}