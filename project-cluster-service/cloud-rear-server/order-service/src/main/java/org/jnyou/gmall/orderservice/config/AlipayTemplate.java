package org.jnyou.gmall.orderservice.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.Data;
import org.jnyou.gmall.orderservice.vo.PayVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private String app_id = "2016101800715391";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCirXnllOzhfhUfuuJJG3Pin9RNrLBoAiC5VwH1veNhw8ZSYqSMhdpbSRC4oVxCKJ9nzOLZRz7cLuV7dmIL0YcYnx8tABQznyetcbrDuTNQ4kKtlAV4EYYvi1T2o1LlJouRNVGFEjDlmWHNOhqM57CoUoHk0pwlww2EZkP0o8usI5fk/3jjwbpYHW6T1AEWH3MQF4XeLrjFFDLwxkJZmOXAK7kEpg2wCp3isx+HaxyvnNxlgK1WpDxiKlNyyJfBiNtkfyU3Br/1HURv/T+vwnbuA+A1RD3KumKg7hLvI1QCYiZiRdy8iMdGFAiCU2weXvXGmEt5WPvvVvKxJVDxFqx5AgMBAAECggEBAI9S366RwxH4D4xhDrTyZusnfdjhaJp6JXKlYaisoASmg7haCDd2RqdefEjugWYYfDjrnoFn783102EvL7rR44Ch0MnI5AOHGWIKbU27gaalcbGUVC0w41RIIZca1hHmH9jTbPscx8/BjxVNN88k4dOqvZ5ooAH1E5eusfh7xxnk6Jq0ngLyPVCay0fWYnR3eb7vFM9CSJqQU9UUbgPOk8gnYXDx+N6xvPOpiLh1CGcrUfooNF2LiFjaHVpPejujb2TgBIMeTrclncEuIVtaYjw1ByN2ZkK7+MdXeE915aYiKfYnWXoUMh9xtvKDh8Npp/v5UgUt5CzR5Qhrtyq1qbECgYEA4gDq15Cthi9doXg5HuK9Xm60KEsDfFrNHGk2JLrGtU7ky5R6Q5YXYm9btMGkN8aOtu3iQQLKzGw8v5psdPc7aDiw3/hNE1+ccnrL3YfPEx65ijLMuGqKcW8+wBiCQJ2bvEo8byueuKypEd37wHueIqhsvF5LgKG0+mt+QeAmu5MCgYEAuETjhQonIB/GZiV50OWSkzpB4jO+HXtsSOPINuizeX9Kic2nMm52Rnmt8V6x+y8pYbke/QRYZDf2BvWDFC5IgZaJHenRIQX+JactuAVQ6l5yquK3CfkM3XnmO08pd9xFqQhoIf4HP1X0srMNHxxjmJ8mDWstpXbbTDxJDJcdN0MCgYEAqQcUnOqIzT3pohUfa3kbNmBHf4cy8TOcCe6qXugOK5X4ykWe96Nuk+FDTjoxTsHDBQ9/Uy36bM9a09Zz3Tr3BGi56Jh1VNMCWCB9Do8/EUa+b5y0vgx4xIP2A4eRCzCVREqlTmPqTqZbwJAsdHY//B3JTUyc3XSTvWLdx/kUHt8CgYBvihOGlmy0f0di0VB5aF1fDSaQO+3afrk/LdIlmpn481o1K+0HE+I4KrNlpYJqn1OXOa1OTnEKyOz9rWMYtiNhfbOmDcBvEueR9y4vc9hU5bLXScCuIrjs3zrDr9lUGlt4RAR0fwX4CpnCCavppw+KdNtdyACrZdS8wskc++PNbQKBgQDNqoobpaFXxmHKnvfz/Fi0w6YDYjJrfURWWsqHA6SQxS/rjpIMRRZGobcqdeXLfrADjDWN96TsE6K/MuMyAHtJBfENWTqhUJQiBN35auv6E6+xwd3qfXOOk1RWrSbcWHHSM0NDx6XyOhjUSg/2X2B9BdYstwrBbfF40FTrOlQngw==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlNJdZGAWLMEHqGgIS7XLrGQ648TTy36XVKjb9QXkrDT8mwHB/QWHdtLD44wlf0cM+OPaLhkSMqr7jH85TbFTWnJNWESEkabBXNxEGaGQN0ruHCSecosEwsENO7kxC7/VKxyySNCGhWBJ9Pjsd/l5iLe6o1ZXnlmeyTsVz8g8/tqHPWLLocTLT20jqNNwuLa6F5iwTY1BDqiNHL0/Hwtm5ZPHQ3gbEKJQ7v6kVNEdy5+vRweiZm4SKpZIbQCK8f57NAnDFPg7eEzBoYUebvY7b+b5SAaBzz5XpHoUVCG3ALHpLWQGNoMKpzCHe2VOYXNFwrkxc7Ks0/3rFUNNifoKiQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private String notify_url = "http://mall.free.idcfengye.com/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private String return_url = "http://order.gmall.com/memberOrder.html";

    // 签名方式
    private String sign_type = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";

    private String timeout = "30m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout + "\"," // 支付宝一分钟收单，一分钟不支付将过期
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应：" + result);

        return result;

    }
}
