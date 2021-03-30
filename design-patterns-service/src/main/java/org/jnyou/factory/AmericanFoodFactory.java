package org.jnyou.factory;

import lombok.Data;
import org.jnyou.entity.Food;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * AmericanFoodFactory
 *
 * @author: youjiannan
 * @date 03月30日 11:48
 **/
public class AmericanFoodFactory implements FoodFactory{

    @Override
    public Food makeFood(String name) {
        if (name.equals("A")) {
            return new AmericanFoodA();
        } else if (name.equals("B")) {
            return new AmericanFoodB();
        } else {
            return null;
        }
    }

}

@Data
class AmericanFoodA extends Food{

}

@Data
class AmericanFoodB extends Food{

}

