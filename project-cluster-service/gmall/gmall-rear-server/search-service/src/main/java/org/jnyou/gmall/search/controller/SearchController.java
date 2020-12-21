package org.jnyou.gmall.search.controller;

import org.elasticsearch.search.SearchService;
import org.jnyou.gmall.search.service.MallSearchService;
import org.jnyou.gmall.search.vo.SearchParam;
import org.jnyou.gmall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String listPage(SearchParam searchParam, Model model) {
        SearchResult searchResult = mallSearchService.search(searchParam);
        model.addAttribute("result",searchResult);

        return "list";
    }

}