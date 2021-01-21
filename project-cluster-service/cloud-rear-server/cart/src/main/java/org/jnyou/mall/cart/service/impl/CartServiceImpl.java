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
import org.jnyou.mall.cart.vo.SkuInfoVo;
import org.jnyou.mall.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

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

        CartItemVo cartItem = new CartItemVo();
        try {
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
            CompletableFuture.allOf(getSkuInfoTask,getAttrsInfoTask).get();
            // 进行手动序列化后存入Redis中，除去了让Redis保存时将对象序列化的工作
            String json = JSON.toJSONString(cartItem);
            cartOps.put(skuId.toString(), json);
        } catch (Exception e) {
            log.error("remote invoke method fail.", e);
            e.printStackTrace();
        }
        return cartItem;
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
}