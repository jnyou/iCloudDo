package org.jnyou.gmall.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 *
 * @className SearchController
 * @author: JnYou xiaojian19970910@gmail.com
 **/
@Controller
public class SearchController {

    @GetMapping("/list.html")
    public String listPage() {
        return "list";
    }

}