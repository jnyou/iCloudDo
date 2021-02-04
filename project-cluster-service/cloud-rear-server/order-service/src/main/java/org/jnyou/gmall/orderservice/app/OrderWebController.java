package org.jnyou.gmall.orderservice.app;

import org.jnyou.gmall.orderservice.service.OrderService;
import org.jnyou.gmall.orderservice.vo.OrderConfirmVo;
import org.jnyou.gmall.orderservice.vo.OrderSubmitVo;
import org.jnyou.gmall.orderservice.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.ExecutionException;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * OrderWebController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Controller
public class OrderWebController {

    @Autowired
    OrderService orderService;

    /**
     * 去结算确认页
     * @param model
     * @Author JnYou
     */
    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("confirmOrder", confirmVo);
        return "confirm";
    }

    /**
     * 下单功能
     * @param vo
     * @Author JnYou
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo) {
        System.out.println("订单提交的数据：" + vo);
        // 创建订单、验令牌、验价格、锁库存
        SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);
        if(responseVo.getCode() == 0){
            // 下单成功  去支付选择页
            return "pay";
        } else {
            // 下单失败：去确认页
            return "redirect://http://order.gmall.com/toTrade";
        }
    }

}