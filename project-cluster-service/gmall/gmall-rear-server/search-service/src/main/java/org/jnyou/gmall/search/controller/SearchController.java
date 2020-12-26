package org.jnyou.gmall.search.controller;

import org.elasticsearch.search.SearchService;
import org.jnyou.gmall.search.service.MallSearchService;
import org.jnyou.gmall.search.vo.SearchParam;
import org.jnyou.gmall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * @className SearchController
 * @author: JnYou xiaojian19970910@gmail.com
 **/
@Controller
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;

    @GetMapping(value = {"/list.html","/"})
    public String listPage(SearchParam searchParam, Model model, HttpServletRequest request) {
        // 获取所有的参数字符串
        searchParam.set_queryString(request.getQueryString());

        SearchResult searchResult = mallSearchService.search(searchParam);
        model.addAttribute("result",searchResult);

        return "list";
    }

}