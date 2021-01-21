package org.jnyou.mall.cart.service;

import org.jnyou.mall.cart.vo.CartItemVo;

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
    CartItemVo addToCart(Long skuId, Integer num);
}