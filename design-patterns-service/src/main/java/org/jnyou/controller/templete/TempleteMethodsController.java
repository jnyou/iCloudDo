package org.jnyou.controller.templete;

import lombok.extern.slf4j.Slf4j;
import org.jnyou.entity.User;
import org.jnyou.service.templete.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 分类名称
 *
 * @ClassName TempleteMethodsController
 * @Description: 模板方法模式接口
 * @Author: jnyou
 * @create 2020/07/27
 * @module 智慧园区
 **/
@RestController
@Slf4j
@RequestMapping("templete")
public class TempleteMethodsController {

    @Autowired
    private UserService userService;

    @GetMapping("selectTemplete")
    public Map<String,Object> selectTemplete(){
        Map<String,Object> maps = new HashMap<>(124);

        User userByIdTemplate = userService.getUserByIdTemplate(1);
        System.out.println(userByIdTemplate);
        maps.put("data",userByIdTemplate);
        return maps;
    }

}