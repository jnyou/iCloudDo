package org.jnyou.mall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.common.constant.CartConstant;
import org.jnyou.common.utils.R;
import org.jnyou.mall.cart.feign.ProductFeignClient;
import org.jnyou.mall.cart.interceptor.InvokeInterceptor;
import org.jnyou.mall.cart.service.CartService;
import org.jnyou.mall.cart.vo.CartItemVo;
import org.jnyou.mall.cart.vo.CartVo;
import org.jnyou.mall.cart.vo.SkuInfoVo;
import org.jnyou.mall.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * CartServiceImpl
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public CartItemVo addToCart(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();

        // 添加新商品到购物车
        try {
            CartItemVo cartItem = new CartItemVo();
            String res = (String) cartOps.get(skuId.toString());
            if (StringUtils.isEmpty(res)) {
                // 购物车无此商品，则新增商品
                CompletableFuture<Void> getSkuInfoTask = CompletableFuture.runAsync(() -> {
                    // 远程调用商品服务查询当前要添加的商品信息
                    R r = productFeignClient.info(skuId);
                    SkuInfoVo data = r.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                    });
                    cartItem.setCheck(true);
                    cartItem.setImage(data.getSkuDefaultImg());
                    cartItem.setTitle(data.getSkuTitle());
                    cartItem.setSkuId(skuId);
                    cartItem.setCount(num);
                    cartItem.setPrice(data.getPrice());
                }, executor);

                CompletableFuture<Void> getAttrsInfoTask = CompletableFuture.runAsync(() -> {
                    // 远程调用商品服务查询sku的组合属性信息
                    List<String> value = productFeignClient.getSkuSaleAttrValue(skuId);
                    cartItem.setSkuAttrValues(value);
                }, executor);
                CompletableFuture.allOf(getSkuInfoTask, getAttrsInfoTask).get();
                // 进行手动序列化后存入Redis中，除去了让Redis保存时将对象序列化的工作
                String json = JSON.toJSONString(cartItem);
                cartOps.put(skuId.toString(), json);
                return cartItem;
            } else {
                // 购物车中有此商品，修改数量即可
                CartItemVo cartItemVo = JSON.parseObject(res, CartItemVo.class);
                cartItemVo.setCount(cartItemVo.getCount() + num);
                // 重新保存商品到Redis中
                cartOps.put(skuId.toString(), JSON.toJSONString(cartItemVo));
                return cartItemVo;
            }
        } catch (Exception e) {
            log.error("remote invoke method fail.", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CartItemVo getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String json = (String) cartOps.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(json, CartItemVo.class);
        return cartItemVo;
    }

    @Override
    public CartVo getCart() {
        CartVo cart = new CartVo();
        // 区分登录还是不登录状态
        UserInfoTo userInfoTo = InvokeInterceptor.threadLocal.get();
        if (null != userInfoTo.getUserId()) {
            // 登录了 需要合并临时购物车的数据
            String cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserId();
            // 判断临时购物车  临时购物车还没有进行合并
            String tempCartKey = CartConstant.CART_PREFIX + userInfoTo.getUserKey();
            List<CartItemVo> tempCartItem = getCartItem(tempCartKey);
            if (!CollectionUtils.isEmpty(tempCartItem)) {
                // 临时购物车有数据，需要合并
                for (CartItemVo cartItemVo : tempCartItem) {
                    addToCart(cartItemVo.getSkuId(), cartItemVo.getCount());
                }
                // 合并完成后，清空临时购物车数据
                clearCart(tempCartKey);
            }
            // 临时购物车添加完成后，在重新获取登录后的购物车（包括登录的购物车和临时购物车数据）
            List<CartItemVo> result = getCartItem(cartKey);
            cart.setItems(result);
        } else {
            // 未登录
            String cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserKey();
            // 获取临时购物车的所有项
            List<CartItemVo> cartItem = getCartItem(cartKey);
            cart.setItems(cartItem);
        }
        return cart;
    }

    /**
     * 获取到需要操作的购物车
     *
     * @param skuId
     * @Author JnYou
     */
    private BoundHashOperations<String, Object, Object> getCartOps() {
        // 获取用户信息
        UserInfoTo userInfoTo = InvokeInterceptor.threadLocal.get();
        // 将加入的购物车的商品信息存入Redis中，类型：hash，格式：key1：mall:cart:userId，key2：skuId，value：CartItemVo
        String cacheKey;
        if (null != userInfoTo.getUserId()) {
            // 用户登录了，将商品信息存入登录用户的购物车中
            cacheKey = CartConstant.CART_PREFIX + userInfoTo.getUserId();
        } else {
            // 临时用户
            cacheKey = CartConstant.CART_PREFIX + userInfoTo.getUserKey();
        }
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(cacheKey);
        return operations;
    }


    private List<CartItemVo> getCartItem(String cartKey) {
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(cartKey);
        // 获取所有的值
        List<Object> values = hashOps.values();
        if (!CollectionUtils.isEmpty(values)) {
            List<CartItemVo> collect = values.stream().map(obj -> {
                String json = obj.toString();
                CartItemVo cartItemVo = JSON.parseObject(json, CartItemVo.class);
                return cartItemVo;
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

    @Override
    public void clearCart(String cartKey) {
        redisTemplate.delete(cartKey);
    }

    @Override
    public void checkItem(Long skuId, Integer check) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItemVo cartItem = getCartItem(skuId);
        cartItem.setCheck(check == 1 ? true : false);
        String json = JSON.toJSONString(cartItem);
        cartOps.put(skuId.toString(),json);
    }

    @Override
    public void countItem(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItemVo cartItem = getCartItem(skuId);
        cartItem.setCount(num);
        String json = JSON.toJSONString(cartItem);
        cartOps.put(skuId.toString(),json);
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId.toString());
    }
}