package org.jnyou.gmall.orderservice.app;

import com.alipay.api.AlipayApiException;
import org.jnyou.gmall.orderservice.config.AlipayTemplate;
import org.jnyou.gmall.orderservice.service.OrderService;
import org.jnyou.gmall.orderservice.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * PayWebController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Controller
public class PayWebController {

    @Autowired
    OrderService orderServicel;

    @Autowired
    AlipayTemplate alipayTemplate;

    /**
     * 给支付宝相应HTML网页数据
     * @param orderSn
     * @Author JnYou
     */
    @ResponseBody
    @GetMapping(value = "/aliPayOrder",produces = "text/html")
    public String aliPay(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        PayVo payVo = orderServicel.getPayData(orderSn);
        // result 返回的是一个页面，将此页面直接交给浏览器就行
        String result = alipayTemplate.pay(payVo);
        return result;
    }

}