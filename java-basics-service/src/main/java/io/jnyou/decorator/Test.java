package io.jnyou.decorator;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Test
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class Test {

    public static void main(String[] args) {
        // 想要一杯豆浆饮料
        Drink drink = new SoyaBeanMilk();
        // 加糖
        SugarDecorator suga = new SugarDecorator(drink);
        // 加黑豆
        BlackBeanDecorator black = new BlackBeanDecorator(suga);

        System.out.println("点的饮料" + black.descript() + "价格为：" + black.cost());
    }

}