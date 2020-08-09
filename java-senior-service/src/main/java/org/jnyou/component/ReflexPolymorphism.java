package org.jnyou.component;

import org.jnyou.component.abstractbase.AbstractOperate;
import org.jnyou.component.abstractbase.SubOperate;

/**
 * 分类名称
 *
 * @ClassName ReflexPolymorphism
 * @Description: 多态+反射机制干掉switch，把对应操作的逻辑分离出来，使得代码耦合度降低
 * @Author: jnyou
 * @create 2020/07/28
 * @module 智慧园区
 **/
public class ReflexPolymorphism {

    /** 测试 **/
    public static void main(String[] args) {
        System.out.println(getResult(1,2,SubOperate.class));
    }


    /** -------------------- before -------------------- **/

    public static int getResult(int numberA, int numberB, String operate) {
        int result = 0;
        switch (operate) {
            case "+":
                result = numberA + numberB;
                break;
            case "-":
                result = numberA - numberB;
                break;
            case "*":
                result = numberA * numberB;
                break;
            case "/":
                result = numberA / numberB;
                break;
            default:
                break;
        }
        return result;
    }

    /** -------------------- after (多态方式+反射机制)  -------------------- **/
    public static <T extends AbstractOperate> int getResult(int numberA, int numberB, Class<T> clz){
        int result = 0;
        try {
            return clz.newInstance().compute(numberA, numberB);
        }catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 根据传入 class 参数，然后生成对应 Opearte处理类， 多态方式+反射机制，
     * 使得代码耦合度大大降低，如果在增加平方根，平方等计算方式。
     * 我们只需要 新增一个 class 继承 Opearte 即可，getResult 不用做任何修改。

     需要注意的是，不是所有switch语句都需要这样替换, 在面对简单的 switch语句，就不必要了， 避免过度设计的嫌疑。
     **/


}