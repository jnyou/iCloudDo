package org.jnyou.messagepush.controller;

import org.jnyou.messagepush.server.WebSocketServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desciption:api接口 (含数据推送)
 */
@Controller
public class AccessApi {

    /**
      * @description:访问页面index
    */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
      * @description:可视化数据
    */
    @ResponseBody
    @RequestMapping("/visual")
    @Scheduled(fixedRate = 3000)
    public List<Integer> visual(){
        List<Integer> collect = Arrays.stream("30,5,20,40,23,50".split(",")).map(element -> (int)(Math.random() * 50 + 1)).collect(Collectors.toList());
        //数据推送
        push(collect.toString().replace("[","").replace("]",""));
        return collect;
    }


    /**
      * @description:数据推送服务
    */
    private void push(String message) {
        WebSocketServer.batchSendInfo(message);
    }

}
