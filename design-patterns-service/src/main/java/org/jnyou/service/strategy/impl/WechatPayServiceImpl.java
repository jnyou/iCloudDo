package org.jnyou.service.strategy.impl;

import org.jnyou.service.strategy.PayService;
import org.jnyou.service.strategy.PayType;
import org.springframework.stereotype.Service;

/**
 *
 * 微信支付的具体实现
 * @author 夏小颜
 */
@Service
public class WechatPayServiceImpl implements PayService {
    @Override
    public String price(Double money) {
        System.out.println("wechat");
        return "wechat";
    }

    @Override
    public PayType payType() {
        return PayType.WECHAT_PAY;
    }
}