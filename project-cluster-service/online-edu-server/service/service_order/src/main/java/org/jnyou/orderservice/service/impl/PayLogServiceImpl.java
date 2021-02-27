package org.jnyou.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import org.jnyou.orderservice.utils.HttpClient;
import org.jnyou.orderservice.entity.Order;
import org.jnyou.orderservice.entity.PayLog;
import org.jnyou.orderservice.mapper.OrderMapper;
import org.jnyou.orderservice.mapper.PayLogMapper;
import org.jnyou.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.servicebase.exception.IsMeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-08-09
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map<String, Object> createNavite(String orderNo) {
        try {
            // 1、根据订单号查询订单信息
            Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", orderNo));
            // 2、使用map设置生成二维码需要的参数
            Map<String, String> m = new HashMap<>(124);
            //1、设置支付参数
            // 关联的公众号appid
            m.put("appid", "wx74862e0dfcf69954");
            // 商户号
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            // 课程标题
            m.put("body", order.getCourseTitle());
            // 课程号
            m.put("out_trade_no", orderNo);

            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            // 支付ip地址
            m.put("spbill_create_ip", "127.0.0.1");
            // 回调地址
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");

            // 3、发送httpclient请求，传递参数xml格式，微信支付提供的固定地址(HTTPClient来根据URL访问第三方接口并且传递参数)
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            // 设置xml格式的参数  添加商户key
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            // 执行post请求发送请求
            client.post();

            // 4、得到发送请求得返回结果  结果也是xml格式
            String xml = client.getContent();

            // 将xml格式转换成map集合，返回map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //4、封装返回结果集

            Map<String, Object> map = new HashMap<>(1024);
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            //返回二维码的状态码 如200
            map.put("result_code", resultMap.get("result_code"));
            // 二维码地址
            map.put("code_url", resultMap.get("code_url"));

            return map;
        } catch (Exception e) {
            throw new IsMeException(-1,"生成微信支付二维码失败");
        }
    }

    @Override
    public Map<String, String> queryPayState(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>(1024);
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //5、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        if(order.getStatus().intValue() == 1) return;
        order.setStatus(1);
        orderMapper.updateById(order);

        //记录支付日志
        PayLog payLog=new PayLog();
        //支付订单号
        payLog.setOrderNo(order.getOrderNo());
        payLog.setPayTime(new Date());
        //支付类型
        payLog.setPayType(1);
        //总金额(分)
        payLog.setTotalFee(order.getTotalFee());
        //支付状态
        payLog.setTradeState(map.get("trade_state"));
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        //插入到支付日志表
        baseMapper.insert(payLog);
    }
}
