package org.jnyou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分类名称
 *
 * @ClassName Student
 * @Description:
 * @Author: jnyou
 **/
@Data
@AllArgsConstructor
public class Student {

    private Long id;
    private String name;
    private String className;
    private String grade;
    private int age;
    private Integer socre;
    //家庭成员数量
    private BigDecimal familyMemberQuantity;

    //入职日期
    private Date entryDate;

    public Student(String name,int age,Integer score){
        this.name = name;
        this.age = age;
        this.socre = score;
    }

}