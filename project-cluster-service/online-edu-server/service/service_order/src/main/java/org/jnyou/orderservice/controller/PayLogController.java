package org.jnyou.orderservice.controller;


import org.jnyou.commonutils.R;
import org.jnyou.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author jnyou
 * @since 2020-08-09
 */
@RestController
@RequestMapping("/orderservice/log")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码接口
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    @GetMapping("createNative/{orderNo}")
    public R createNavite(@PathVariable String orderNo){

        // 返回相关信息，包括二维码地址、其他信息等
        Map<String,Object> map = payLogService.createNavite(orderNo);
        System.out.println("二维码map集合" + map);
        // 二维码map集合{code_url=weixin://wxpay/bizpayurl?pr=5ZmR5I3, out_trade_no=20200815183234668, course_id=18, total_fee=0.01, result_code=SUCCESS}
        return R.ok().put("map",map);

    }

    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayState(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayState(orderNo);
        System.out.println("支付状态的map集合" + map);
        // 支付状态的map集合  未支付的情况 ： {nonce_str=bww9AJL4Q9ltyjBR, device_info=, out_trade_no=20200815193525574, trade_state=NOTPAY, appid=wx74862e0dfcf69954, total_fee=1, sign=D1B4751B33F155E1731B01025A7C39AE, trade_state_desc=订单未支付, return_msg=OK, result_code=SUCCESS, mch_id=1558950191, return_code=SUCCESS}
        // 支付状态的map集合  已经支付的情况{transaction_id=4200000704202008152716732404, nonce_str=XD1LQPUfDI0sUK1R, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuC3OFNqFkvbVg6ccF7-wgIQ, sign=86F68EED923DE80D480BCA0270EF42D0, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=20200815193116172, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20200815193149, is_subscribe=N, return_code=SUCCESS}
        if(CollectionUtils.isEmpty(map)){
            return R.error().message("支付出错了");
        }
        // 如果返回map不为空，说明支付成功，通过map获取订单的状态
        if(map.get("trade_state").equals("SUCCESS")){
            // 添加记录到支付表中，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

}

