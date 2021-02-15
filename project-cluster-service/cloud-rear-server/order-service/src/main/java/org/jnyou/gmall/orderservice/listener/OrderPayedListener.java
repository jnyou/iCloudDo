package org.jnyou.gmall.orderservice.listener;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.jnyou.gmall.orderservice.config.AlipayTemplate;
import org.jnyou.gmall.orderservice.service.OrderService;
import org.jnyou.gmall.orderservice.vo.PayAsyncVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * OrderPayedListener
 * 支付宝异步通知：https://opendocs.alipay.com/open/270/105902
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@RestController
public class OrderPayedListener {

    @Autowired
    OrderService orderService;
    @Autowired
    AlipayTemplate alipayTemplate;

    @PostMapping("/payed/notify")
    public String handleAlipayed(PayAsyncVo vo, HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            String value = request.getParameter(key);
            System.out.println("参数名==" + key + "===》 参数值" + value);
            /**
             * 参数名==gmt_create===》 参数值2021-02-15 17:07:20
             * 参数名==charset===》 参数值utf-8
             * 参数名==gmt_payment===》 参数值2021-02-15 17:07:27
             * 参数名==notify_time===》 参数值2021-02-15 17:07:28
             * 参数名==subject===》 参数值Apple iPhone 12 黑色全网通5G手机双卡双待 64GB
             * 参数名==sign===》 参数值YXfhWRLcNLeMzDyZmq34U5ZFhVKG4iS/gPPiiXUdqjYTTjYAXJGvJtFujY81z7Dze9xVE+L/s1x/QwhiOLz+tLwchaq6kbvCqpSgAsiu3DwtBzEt2eJRljdOdi3L5aK4g/gaVHrJswHdEpTu5Hds0jAZHNuV5IawrGQCjh99pUn6LIqT7bqQD8WtTVxmw89NFVVPtebbnkAQ6SY3bVaWu1Thy1kWtNMxSO8Jvl1z1T5h26/J1TkNy/7GpXGww/6H3Vz6NX1jPaXxBiJQuP5VNQIUONEBP6wSTFiQ8hf/AEJkPWxxpmKIW+E5zfSDzmR7K1SupRpdHZfndM7adC6ELw==
             * 参数名==buyer_id===》 参数值2088102180583897
             * 参数名==body===》 参数值颜色：黑色;版本：64GB
             * 参数名==invoice_amount===》 参数值19402.00
             * 参数名==version===》 参数值1.0
             * 参数名==notify_id===》 参数值2021021500222170728083890510664721
             * 参数名==fund_bill_list===》 参数值[{"amount":"19402.00","fundChannel":"ALIPAYACCOUNT"}]
             * 参数名==notify_type===》 参数值trade_status_sync
             * 参数名==out_trade_no===》 参数值202102151706537061361240555475976194
             * 参数名==total_amount===》 参数值19402.00
             * 参数名==trade_status===》 参数值TRADE_SUCCESS
             * 参数名==trade_no===》 参数值2021021522001483890501455117
             * 参数名==auth_app_id===》 参数值2016101800715391
             * 参数名==receipt_amount===》 参数值19402.00
             * 参数名==point_amount===》 参数值0.00
             * 参数名==app_id===》 参数值2016101800715391
             * 参数名==buyer_pay_amount===》 参数值19402.00
             * 参数名==sign_type===》 参数值RSA2
             * 参数名==seller_id===》 参数值2088102180130725
             */
        }
        System.out.println("支付宝通知" + parameterMap);

        // 验签，看是否是支付宝传递过来的。防止数据被篡改和伪造。
        Map<String, String> params = new HashMap<String, String>(124);
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type()); //调用SDK验证签名
        if (signVerified) {
            System.out.println("签名验证成功");
            String result = orderService.handlePayResult(vo);
            // 只要我们收到了支付宝的异步通知，告诉我们订单支付成功，返回success，支付宝就不会再通知了
            return result;
        } else {
            System.out.println("签名验证error");
            return "error";
        }
    }

}