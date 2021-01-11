package org.jnyou.generics;

import org.jnyou.generics.entity.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * GlmapperGeneric
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class GlmapperGeneric<T> {

    /**
     * 表示一种具体的类型
     */
    private T t;

    public void setT(T t){
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void noSpecifyType() {
        GlmapperGeneric glmapperGeneric = new GlmapperGeneric();
        glmapperGeneric.setT("hello");
        // 没指定泛型需要强制转换
        String str = (String) glmapperGeneric.getT();
        System.out.println(str);
    }

    public void specify() {
        GlmapperGeneric<String> glmapperGeneric = new GlmapperGeneric<>();
        glmapperGeneric.setT("hello");
        // 指定了泛型，不需要强制转换
        String str = glmapperGeneric.getT();
        System.out.println(str);
    }
    
    /**
     * ？表示不确定的 java 类型
     * T (type) 表示具体的一个java类型
     * K V (key value) 分别代表java键值中的Key Value
     * E (element) 代表Element
     */

    static int countLegs(List<? extends Animal> animals){
        int retVal = 0;
        for ( Animal animal : animals )
        {
            retVal += animal.getLegs();
        }
        return retVal;
    }

    static int countLegs1 (List<Animal> animals ){
        int retVal = 0;
        for ( Animal animal : animals )
        {
            retVal += animal.getLegs();
        }
        return retVal;
    }

    public static void main(String[] args) {
        List<Animal.Dog> dogs = new ArrayList<>();
        // 不会报错
        countLegs(dogs);
        // 报错
//        countLegs1(dogs);
    }


}