package org.jnyou.gmall.productservice.web;

import org.jnyou.gmall.productservice.entity.CategoryEntity;
import org.jnyou.gmall.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author JnYou
 */
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @GetMapping({"/", "index.html"})
    public String indexPage(Model model) {
        // 查出所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Category();
        model.addAttribute("categorys", categoryEntities);
        return "index";
    }
}
