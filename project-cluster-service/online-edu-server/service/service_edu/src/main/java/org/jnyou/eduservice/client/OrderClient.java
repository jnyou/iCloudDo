package org.jnyou.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 夏小颜
 */
@Component
@FeignClient(name = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient {

    /**
     * 远程调用判断是否购买课程
     * @param courseId
     * @param memberId
     * @return
     * @Author jnyou
     * @Date 2020/8/22
     */
    @GetMapping("/orderservice/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);

}
