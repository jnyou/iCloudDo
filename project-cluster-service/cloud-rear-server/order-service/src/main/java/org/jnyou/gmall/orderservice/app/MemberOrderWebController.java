package org.jnyou.gmall.orderservice.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MemberOrderWebController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Controller
public class MemberOrderWebController {

    @GetMapping("/memberOrder.html")
    public String memberOrderPage(){
        return "orderList";
    }

}