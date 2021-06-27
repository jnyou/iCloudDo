package io.jnyou.decorator;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SugarDecorator  -- 糖配料类
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class SugarDecorator extends Decorator {

    // 看你需要给哪种饮料进行加配料
    public SugarDecorator(Drink drink) {
        super(drink);
    }

    // 糖配料1元
    @Override
    public float cost() {
        return super.cost() + 1f;
    }

    // 描述
    @Override
    public String descript() {
        return super.descript() + "+糖";
    }
}