package io.commchina.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Student {

    private String name;

    public Student() {
        System.out.println("构造...");
    }

    public Student(String name) {
        super();
        this.name = name;
    }

    public void init() {
        System.out.println("init...");
    }

    public void destroy() {
        System.out.println("destroy...");
    }

    @Override
    public String toString() {
        return "Student [name=" + name + "]";
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList();
        list.add("C");
        list.add("A");
        list.add("C");
        list.add("B");
        list.add("F");
        list.add("C");
        list.add("C");

        System.out.println("未移除前" + list.toString());
        int size = list.size();
        for (int i = 0; i < size; i++) {

            if ("C".equals(list.get(i))){
                list.remove("C");
            }
        }

        System.out.println("移除后" + list.toString());
    }
}