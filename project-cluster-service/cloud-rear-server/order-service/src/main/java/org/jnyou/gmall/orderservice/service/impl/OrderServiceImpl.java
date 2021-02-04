package org.jnyou.gmall.orderservice.service.impl;

import com.alibaba.fastjson.TypeReference;
import org.checkerframework.checker.units.qual.A;
import org.jnyou.common.utils.R;
import org.jnyou.common.vo.MemberResponseVo;
import org.jnyou.gmall.orderservice.feign.CartFeignClient;
import org.jnyou.gmall.orderservice.feign.MemberFeignClient;
import org.jnyou.gmall.orderservice.feign.WareFeignClient;
import org.jnyou.gmall.orderservice.interceptor.LoginUserInterceptor;
import org.jnyou.gmall.orderservice.vo.MemberAddressVo;
import org.jnyou.gmall.orderservice.vo.OrderConfirmVo;
import org.jnyou.gmall.orderservice.vo.OrderItemVo;
import org.jnyou.gmall.orderservice.vo.SkuStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.orderservice.dao.OrderDao;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.jnyou.gmall.orderservice.service.OrderService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    MemberFeignClient memberFeignClient;
    @Autowired
    CartFeignClient cartFeignClient;
    @Autowired
    ThreadPoolExecutor executor;
    @Autowired
    WareFeignClient wareFeignClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        MemberResponseVo memberResponseVo = LoginUserInterceptor.loginUser.get();
        System.out.println("主线程===" + Thread.currentThread().getId());
        // 获取浏览器老请求过来的头信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        CompletableFuture<Void> getAddressTaskFuture = CompletableFuture.runAsync(() -> {
            // 解决feign异步丢失头信息，每一个线程都来共享原来的请求信息
            RequestContextHolder.setRequestAttributes(requestAttributes);
            // 1、远程查询用户收货地址信息
            System.out.println("AddressTask主线程===" + Thread.currentThread().getId());
            List<MemberAddressVo> address = memberFeignClient.getAddress(memberResponseVo.getId());
            confirmVo.setMemberAddressVos(address);
        }, executor);

        CompletableFuture<Void> getCartItemsTaskFuture = CompletableFuture.runAsync(() -> {
            // 解决feign异步丢失头信息，每一个线程都来共享原来的请求信息
            RequestContextHolder.setRequestAttributes(requestAttributes);
            // 2、远程查询购物车选中的购物项信息
            System.out.println("CartItemsTask主线程===" + Thread.currentThread().getId());
            List<OrderItemVo> currentCartItems = cartFeignClient.getCurrentCartItems();
            confirmVo.setItems(currentCartItems);
        }, executor).thenRunAsync(() -> {
            List<OrderItemVo> items = confirmVo.getItems();
            // 远程查询库存信息
            List<Long> collect = items.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
            R r = wareFeignClient.getSkuHasStock(collect);
            if(r.getCode() == 0){
                List<SkuStockVo> data = r.getData(new TypeReference<List<SkuStockVo>>() {
                });
                if(!CollectionUtils.isEmpty(data)){
                    Map<Long, Boolean> map = data.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                    confirmVo.setStocks(map);
                }
            }
        },executor);

        // 3、用户积分
        Integer integration = memberResponseVo.getIntegration();
        confirmVo.setIntegration(integration);
        // 其他数据自动计算
        // TODO 防重令牌
        CompletableFuture.allOf(getAddressTaskFuture,getCartItemsTaskFuture).get();
        return confirmVo;
    }

}