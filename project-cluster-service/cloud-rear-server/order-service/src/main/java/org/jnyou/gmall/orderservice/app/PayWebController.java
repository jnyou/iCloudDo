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
        /**
         * <form name="punchout_form" method="post" action="https://openapi.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=E2qzE71sGhyivt7UeSAt0t8Qe65cTZV4o0zlPEj3lUOGmjmfKDSOTgn%2F%2BLs6LxWnGqg%2FtpH272Ualdtf9waiCyQMgkriBO5F2HxehqYNxcEIR3q2O0camFSjXOHKgTBsJKag0LLQ%2BsvEKmNcs2z3DfxsUIIZpNigNWILAvfBlLJodX7sXbrtvOdhyIU11zWkCCaQnnNBAgbN6idMkiw3v9kjferR5garWxvC6GGjhBbSWJy%2BALadQc0EWa4XXoC8NAq%2B2ZichRB0oenXOxp4qIbqjUGOIoor3G03bOK5Qak7PM9NcGvEmjNYYM%2FkemeyV6iTDE9kphwCi23aIY7wvA%3D%3D&return_url=http%3A%2F%2Forder.gmall.com%2FmemberOrder.html&notify_url=http%3A%2F%2Fmall.free.idcfengye.com%2Fpayed%2Fnotify&version=1.0&app_id=2016101800715391&sign_type=RSA2&timestamp=2021-02-15+17%3A06%3A58&alipay_sdk=alipay-easysdk-java&format=json">
         * <input type="hidden" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;202102151706537061361240555475976194&quot;,&quot;total_amount&quot;:&quot;19402.00&quot;,&quot;subject&quot;:&quot;Apple iPhone 12 黑色全网通5G手机双卡双待 64GB&quot;,&quot;body&quot;:&quot;颜色：黑色;版本：64GB&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}">
         * <input type="submit" value="立即支付" style="display:none" >
         * </form>
         * <script>document.forms[0].submit();</script>
         */
        return result;
    }

}