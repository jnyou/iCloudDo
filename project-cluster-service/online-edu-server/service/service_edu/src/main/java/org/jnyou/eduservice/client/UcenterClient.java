package org.jnyou.eduservice.client;

import org.jnyou.commonutils.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 分类名称
 *
 * @ClassName UcenterClient
 * @Description: 课程评论获取评论的用户信息   微服务调用用户信息
 * @Author: jnyou
 * @create 2020/08/09
 * @module 智慧园区
 **/
@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    /***
     * 根据用户id获取用户信息
     * @param id
     * @return
     * @Author jnyou
     * @Date 2020/8/9
     */
    @GetMapping("/ucenterservice/ucenter/getInfoUc/{id}")
    public UcenterMember getUcenterPay(@PathVariable("id") String id);

}