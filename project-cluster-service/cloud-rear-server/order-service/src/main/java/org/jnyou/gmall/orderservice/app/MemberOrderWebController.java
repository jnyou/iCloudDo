package org.jnyou.gmall.orderservice.app;

import com.alibaba.fastjson.JSON;
import lombok.experimental.Accessors;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    OrderService orderServicel;

    @GetMapping("/memberOrder.html")
    public String memberOrderPage(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model){
        Map<String,Object> map = new HashMap<>();
        map.put("page",pageNum.toString());
        PageUtils pageUtils = orderServicel.queryPageWithItem(map);
        System.out.println(JSON.toJSONString(pageUtils));
        model.addAttribute("orders",pageUtils);
        return "orderList";
    }

}