package io.jnyou.decorator;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SoyaBeanMilk --豆浆饮料
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class SoyaBeanMilk implements Drink{

    /**
     * 原始豆浆的价格
     */
    @Override
    public float cost() {
        return 3f;
    }

    /**
     * 原始豆浆的描述
     */
    @Override
    public String descript() {
        return "原味豆浆";
    }
}