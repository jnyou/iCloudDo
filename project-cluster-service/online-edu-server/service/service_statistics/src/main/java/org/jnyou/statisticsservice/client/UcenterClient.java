package org.jnyou.statisticsservice.client;

import org.jnyou.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 分类名称
 *
 * @ClassName UcenterClient
 * @Description: 统计注册用户
 * @Author: jnyou
 * @create 2020/08/22
 * @module 智慧园区
 **/
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    /**
     * 远程调用统计某天注册人数
     * @param date
     * @return
     * @Author jnyou
     * @Date 2020/8/22
     */
    @GetMapping("/ucenterservice/ucenter/countRegister/{date}")
    public R countRegister(@PathVariable("date") String date);

}