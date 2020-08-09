package org.jnyou.service.strategy.impl;

import org.jnyou.service.strategy.PayService;
import org.jnyou.service.strategy.PayType;
import org.springframework.stereotype.Service;

/**
 * 支付宝支付的具体实现
 * @author 夏小颜
 */
@Service
public class AlipyPayServiceImpl implements PayService {
    @Override
    public String price(Double money) {
        System.out.println("alilyPay");
        return "alipay";
    }

    @Override
    public PayType payType() {
        return PayType.APPLY_PAY;
    }
}