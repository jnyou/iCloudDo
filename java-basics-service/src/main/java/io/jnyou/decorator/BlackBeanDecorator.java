package io.jnyou.decorator;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * BlackBeanDecorator -- 黑豆配料类
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class BlackBeanDecorator extends Decorator {

    public BlackBeanDecorator(Drink drink){
        super(drink);
    }

    // 加黑豆配料两元
    @Override
    public float cost() {
        return super.cost() + 2f;
    }

    // 描述
    @Override
    public String descript() {
        return super.descript() + "+黑豆";
    }
}