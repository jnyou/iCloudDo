package org.jnyou.gmall.orderservice.service.impl;

import org.jnyou.common.vo.MemberResponseVo;
import org.jnyou.gmall.orderservice.feign.CartFeignClient;
import org.jnyou.gmall.orderservice.feign.MemberFeignClient;
import org.jnyou.gmall.orderservice.interceptor.LoginUserInterceptor;
import org.jnyou.gmall.orderservice.vo.MemberAddressVo;
import org.jnyou.gmall.orderservice.vo.OrderConfirmVo;
import org.jnyou.gmall.orderservice.vo.OrderItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.orderservice.dao.OrderDao;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.jnyou.gmall.orderservice.service.OrderService;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    MemberFeignClient memberFeignClient;
    @Autowired
    CartFeignClient cartFeignClient;
    @Autowired
    ThreadPoolExecutor executor;

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
        CompletableFuture<Void> getAddressTaskFuture = CompletableFuture.runAsync(() -> {
            // 1、远程查询用户收货地址信息
            List<MemberAddressVo> address = memberFeignClient.getAddress(memberResponseVo.getId());
            confirmVo.setMemberAddressVos(address);
        }, executor);

        CompletableFuture<Void> getCartItemsTaskFuture = CompletableFuture.runAsync(() -> {
            // 2、远程查询购物车选中的购物项信息
            List<OrderItemVo> currentCartItems = cartFeignClient.getCurrentCartItems();
            confirmVo.setItems(currentCartItems);
        }, executor);

        // 3、用户积分
        Integer integration = memberResponseVo.getIntegration();
        confirmVo.setIntegration(integration);
        // 其他数据自动计算
        // TODO 防重令牌
        CompletableFuture.allOf(getAddressTaskFuture,getCartItemsTaskFuture).get();
        return confirmVo;
    }

}