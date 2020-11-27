package com.blithe.cms.controller.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.exception.RbacException;
import com.blithe.cms.common.utils.StringUtil;
import com.blithe.cms.config.aliyun.AlipayConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName AlipayController
 * @Description: alipay支付接口
 * @Author: 夏小颜
 * @Date: 14:34
 * @Version: 1.0
 **/
@RestController
@RequestMapping("alipay")
public class AlipayController {


    /**
     * @Author: youjiannan
     * @Description: 
     * @Date: 2020/3/26
     * @Param: [out_trade_no, total_amount, subject, body]
     *
     * out_trade_no : 订单号，商户网站订单系统中唯一订单号，必填
     * total_amount : 付款金额，必填
     * subject : 订单名称，必填
     * body : 商品描述，可空
     *
     * @Return: com.blithe.cms.common.exception.R
     **/
    @RequestMapping("/pay")
    public R alipayMethod(@RequestParam String out_trade_no,@RequestParam String total_amount,
                          @RequestParam String subject,String body) throws AlipayApiException {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节


        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println(result);
        //返回
        try {
            if(StringUtil.isNotEmpty(result)){
                return R.ok().put("data",result);
            }
        }catch (Exception e){
            throw new RbacException(e.getMessage());
        }
        return R.ok();
    }

}