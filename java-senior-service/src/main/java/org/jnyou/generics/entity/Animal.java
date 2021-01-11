package org.jnyou.generics.entity;

import lombok.Data;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Animals
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class Animal {

    private Integer legs;

    private Float height;


    @Data
    public static
    class Dog extends Animal{
        private String name;
        private Integer age;
    }

}