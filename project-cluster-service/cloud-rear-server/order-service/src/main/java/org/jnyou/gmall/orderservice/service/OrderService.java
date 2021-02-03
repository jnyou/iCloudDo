package org.jnyou.gmall.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.jnyou.gmall.orderservice.vo.OrderConfirmVo;

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
}

