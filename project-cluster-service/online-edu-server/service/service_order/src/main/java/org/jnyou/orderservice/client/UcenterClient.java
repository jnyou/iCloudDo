package org.jnyou.orderservice.client;

import org.jnyou.commonutils.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 夏小颜
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    /**
     * 根据用户id远程调用获取用户信息
     * @param id
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    @GetMapping("/ucenterservice/ucenter/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id);

}
