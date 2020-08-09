package org.jnyou.service.strategy;

/**
 * @author 夏小颜
 */
public interface PayService {

    /**
     * 一个支付方式的选择的策略接口
     * @param money
     * @return
     */
    String price(Double money);

    /**
     * 策略选择的类型
     * @return
     */
    PayType payType();

}
