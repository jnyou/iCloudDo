package org.jnyou.mall.cart.service;

import org.jnyou.mall.cart.vo.CartItemVo;
import org.jnyou.mall.cart.vo.CartVo;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * CartService
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public interface CartService {
    /**
     * 添加购物车
     * @param skuId
     * @param num
     * @Author JnYou
     */
    CartItemVo addToCart(Long skuId, Integer num);

    /**
     * 购物项的获取
     * @param skuId
     * @Author JnYou
     */
    CartItemVo getCartItem(Long skuId);

    /**
     * 获取整个购物车数据
     * @Author JnYou
     */
    CartVo getCart();

    /**
     * 清空购物车
     * @param cartKey
     * @Author JnYou
     */
    void clearCart(String cartKey);

    /**
     * 勾选购物项
     * @param skuId
     * @param check
     * @Author JnYou
     */
    void checkItem(Long skuId, Integer check);
}