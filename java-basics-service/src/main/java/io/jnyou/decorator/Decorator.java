package io.jnyou.decorator;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Decorator
 * 装饰者模式 --装饰者基类
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public abstract class Decorator implements Drink{

    private Drink drink; // 要装饰的类
    public Decorator(Drink drink){
        this.drink = drink;
    }

    @Override
    public float cost() {
        return drink.cost();
    }

    @Override
    public String descript() {
        return drink.descript();
    }
}