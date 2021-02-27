package org.jnyou.orderservice.service;

import org.jnyou.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-08-09
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     * @param courseId
     * @param ucenterId
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    String createOrders(String courseId, String ucenterId);
}
