package org.jnyou.factory;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Test
 *
 * @author: youjiannan
 * @date 03月30日 18:31
 **/
public class Test {
    public static void main(String[] args) {
        // 先选择一个具体的工厂，其实就是先选择一个接口，接口内部有一个生产东西的方法；
        FoodFactory foodFactory = new AmericanFoodFactory();
        // 看你需要具体生产那种产品
        foodFactory.makeFood("A");
    }
}