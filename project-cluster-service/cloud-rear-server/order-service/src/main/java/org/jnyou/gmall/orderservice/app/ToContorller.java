package org.jnyou.gmall.orderservice.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ToContorller
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Controller
public class ToContorller {

    @GetMapping("/{page}.html")
    public String toPage(@PathVariable("page") String page) {
        return page;
    }

}