package org.jnyou.gmall.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.to.mq.SeckillOrderTo;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.jnyou.gmall.orderservice.vo.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 订单确认页需要的数据
     * @Author JnYou
     */
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    /**
     * 下单功能：创建订单、验令牌、验价格、锁库存
     * @param vo
     * @Author JnYou
     */
    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    OrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OrderEntity orderEntity);

    PayVo getPayData(String orderSn);

    PageUtils queryPageWithItem(Map<String, Object> params);

    String handlePayResult(PayAsyncVo vo);

    void createSeckillOrder(SeckillOrderTo order);
}

