package org.jnyou.orderservice.client;

import org.jnyou.commonutils.entity.CourseDetailsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 夏小颜
 */
@Component
@FeignClient("service-edu")
public interface CourseClient {

    /**
     * 根据id远程调用获取课程信息
     * @param courseId
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    @GetMapping("/eduservice/course/front/getDto/{courseId}")
    public CourseDetailsVo getCourseInfoDto(@PathVariable("courseId") String courseId);

}
