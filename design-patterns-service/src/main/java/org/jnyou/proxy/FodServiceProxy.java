package org.jnyou.proxy;

import org.jnyou.entity.Food;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * FodServiceProxy
 *
 * @author: youjiannan
 * @date 04月01日 10:04
 **/
public class FodServiceProxy implements FodService{
    /**
     * 代理对象，在实现类做事的前后加入相应的其他逻辑来增强方法的功能
     */
    // 内部一定要有一个真实的实现类
    private FodService fodService = new FodServiceImpl();

//    private FodServiceImpl fodServiceImpl;
//    public FodServiceProxy(FodServiceImpl fodServiceImpl){
//        this.fodServiceImpl = fodServiceImpl;
//    }

    @Override
    public Food makeChicken() {

        System.out.println("在制作鸡肉之前想要干些啥事");

        Food food = fodService.makeChicken();

        System.out.println("在制作鸡肉之后想要干些啥事");
        // 比如添加鸡的名称：
        food.setName("大公鸡");

        return food;
    }
}