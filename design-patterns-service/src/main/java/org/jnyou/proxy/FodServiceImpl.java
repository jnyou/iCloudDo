package org.jnyou.proxy;

import org.jnyou.entity.Chicken;
import org.jnyou.entity.Food;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * FodServiceImpl
 *
 * @author: youjiannan
 * @date 04月01日 9:59
 **/
public class FodServiceImpl implements FodService{
    // 被代理对象，只做自己的事儿
    @Override
    public Food makeChicken() {
        Food f = new Chicken();
        f.setChicken("1kg");
        f.setSpicy("1g");
        f.setSalt("3g");
        System.out.println("鸡肉加好佐料了");
        return f;
    }
}