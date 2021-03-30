package org.jnyou.factory;

import org.jnyou.entity.Food;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * FoodFactory
 *
 * @author: youjiannan
 * @date 03月30日 11:47
 **/
public interface FoodFactory {

    Food makeFood(String name);

}