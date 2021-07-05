package org.jnyou.gmall.orderservice.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
 * 支付宝的异步通知属性：官方网址：https://opendocs.alipay.com/open/270/105902
 * @Author JnYou
 */
@ToString
@Data
public class PayAsyncVo {

    private String gmt_create; // 交易创建时间。该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss。
    private String charset; // 编码格式。如 utf-8、gbk、gb2312 等。
    private String gmt_payment; // 交易付款时间。该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss。
    private Date notify_time; // 通知的发送时间。格式为 yyyy-MM-dd HH:mm:ss。
    private String subject; // 订单标题
    private String sign; //签名
    private String buyer_id;//支付者的id
    private String body;//订单的信息
    private String invoice_amount;//支付金额
    private String version; // 调用的接口版本。固定为：1.0。
    private String notify_id;//通知id 通知校验 ID。
    /**
     * fundChannel 支付渠道 详见https://opendocs.alipay.com/open/270/105902
     * amount 支付金额。使用指定支付渠道支付的金额，单位为元。
     */
    private String fund_bill_list; // 支付金额信息。支付成功的各个渠道金额信息 [{“amount”:“15.00”,“fundChannel”:“ALIPAYACCOUNT”}]
    private String notify_type;//通知类型； trade_status_sync
    private String out_trade_no;//订单号  商户订单号。原支付请求的商户订单号。
    private String total_amount;//支付的总额
    /**
     * TRADE_SUCCESS 交易支付成功。WAIT_BUYER_PAY 交易创建，等待买家付款。
     * TRADE_CLOSED 未付款交易超时关闭，或支付完成后全额退款。 TRADE_FINISHED 交易结束，不可退款。
     */
    private String trade_status;//交易状态
    private String trade_no;//流水号  支付宝交易号。支付宝交易凭证号。
    private String auth_app_id;// 授权方的 appid。由于本接口暂不开放第三方应用授权，因此 auth_app_id=app_id。
    private String receipt_amount;//商家收到的款
    private String point_amount;// 集分宝金额。使用集分宝支付的金额，单位为元，精确到小数点后 2 位。
    private String app_id;//应用id  开发者的 app_id。支付宝分配给开发者的应用 ID。
    private String buyer_pay_amount;//最终支付的金额
    private String sign_type;//签名类型  签名类型。签名算法类型，目前支持 RSA2 和 RSA，推荐使用 RSA2。
    private String seller_id;//商家的id

}
