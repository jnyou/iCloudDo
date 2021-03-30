package org.jnyou.factory;

import lombok.Data;
import org.jnyou.entity.Food;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ChineseFoodFactory
 *
 * @author: youjiannan
 * @date 03月30日 11:48
 **/
public class ChineseFoodFactory implements FoodFactory{
    @Override
    public Food makeFood(String name) {
        if("A".equals(name)){
            return new ChineseMakeFoodA();
        } else if("B".equals(name)){
            return new ChineseMakeFoodB();
        } else {
            return null;
        }
    }

}

@Data
class ChineseMakeFoodA extends Food{
    private double niceNum;
}

@Data
class ChineseMakeFoodB extends Food{
    private double niceNum;
}