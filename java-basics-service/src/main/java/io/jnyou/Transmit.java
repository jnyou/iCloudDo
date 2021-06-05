package io.jnyou;

import lombok.Data;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Transmit
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class Transmit {


}

/**
 * 值传递
 */
class ValueTrans{
    /**
     * 基本数据类型存储在栈中；
     */
    public static void main(String[] args) {
        int num = 10;
        method(num);
        System.out.println(num); // 10
    }

    public static void method(int num1){
        num1 = 20;
    }
}

/**
 * 引用传递
 */
class RefTrans{
    public static void main(String[] args) {
        Duck duck = new Duck(10);
        method(duck);
        System.out.println(duck.getAge()); // 20
    }

    public static void method(Duck d){
        d.setAge(20);
    }

}

/**
 * 字符串引用类型传递
 */
class StrTrans{
    public static void main(String[] args) {
        String name = "狒狒";
        method(name);
        System.out.println(name); // 狒狒
    }

    public static void method(String name2){
        name2 = "晓晓";
    }
}

/**
 * 字符串对象引用类型传递
 */
class StrTransObject{
    public static void main(String[] args) {
        Duck d1 = new Duck("甜甜");
        method(d1);
        System.out.println(d1.getName()); //
    }

    public static void method(Duck d2){
        d2.setName("恺恺");
    }
}



@Data
class Duck{
    private int age;
    private String name;

    public Duck(int age){
        this.age = age;
    }

    public Duck(String name) {
        this.name = name;
    }
}