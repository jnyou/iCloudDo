package org.jnyou.gmall.orderservice.app;

import org.jnyou.gmall.orderservice.service.OrderService;
import org.jnyou.gmall.orderservice.vo.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("confirmOrder",confirmVo);
        return "confirm";
    }

}