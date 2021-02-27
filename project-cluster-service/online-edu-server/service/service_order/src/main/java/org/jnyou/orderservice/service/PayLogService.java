package org.jnyou.orderservice.service;

import org.jnyou.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-08-09
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    Map<String, Object> createNavite(String orderNo);

    /**
     * 根据订单号查询订单的状态
     * @param orderNo
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    Map<String, String> queryPayState(String orderNo);

    /**
     * 添加支付记录，更新订单状体
     * @param map
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    void updateOrdersStatus(Map<String, String> map);
}
