package org.jnyou.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.jnyou.commonutils.R;
import org.jnyou.eduservice.entity.EduCourse;
import org.jnyou.eduservice.entity.vo.CourseInfoVo;
import org.jnyou.eduservice.entity.vo.CoursePublishVo;
import org.jnyou.eduservice.entity.vo.CourseQuery;
import org.jnyou.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
@RestController
@RequestMapping("eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 添加课程信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@ApiParam(name = "courseInfoVo", value = "课程基本信息", required = true) @RequestBody CourseInfoVo courseInfoVo){
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        if(!StringUtils.isEmpty(courseId)){
            return R.ok().message("添加课程信息成功").put("courseId", courseId);
        }else{
            return R.error().message("添加课程信息失败");
        }
    }

    /**
     * 根据课程ID查询课程相关信息，包括课程简介
     * @param courseId
     * @return
     */
    @GetMapping("queryCourseInfoByCourseId/{courseId}")
    public R queryCourseInfoByCourseId(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoByCourseId(courseId);
        return R.ok().put("courseInfo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok().message("修改课程信息成功");
    }

    /**
     * 根据课程ID查询发布的信息
     * @param courseId
     * @return
     */
    @GetMapping("queryCoursePublishInfo/{courseId}")
    public R queryCoursePublishInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.queryCoursePublishInfo(courseId);
        return R.ok().put("coursePublish",coursePublishVo);
    }

    @PostMapping("publishCourse/{id}")
    @ApiOperation(value = "根据id发布课程")
    public R publishCourse(@ApiParam(name = "id", value = "课程ID", required = true) @PathVariable String id){
        courseService.publishCourseById(id);
        return R.ok().message("课程信息发布成功");
    }

    /**
     * 分页条件查询课程列表
     * @param page
     * @param limit
     * @param courseQuery
     * @return
     */
    @GetMapping("pageCondition/{page}/{limit}")
    @ApiOperation(value = "分页课程列表")
    public R queryCourseInfoListByPage(@PathVariable Integer page,@PathVariable Integer limit,@ApiParam(name = "courseQuery", value = "查询对象", required = false) CourseQuery courseQuery){
        Page<EduCourse> coursePage = courseService.queryPageCondition(page, limit, courseQuery);
        long count = coursePage.getTotal();
        List<EduCourse> list = coursePage.getRecords();
        return R.ok().put("date",list).put("count",count);
    }

    /**
     * 根据课程ID删除该课程的所有信息，包括课程基本信息，课程简介，课程章节，小节，小节视频，OSS云端课程封面
     * @param courseId
     * @return
     */
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();

    }

}

