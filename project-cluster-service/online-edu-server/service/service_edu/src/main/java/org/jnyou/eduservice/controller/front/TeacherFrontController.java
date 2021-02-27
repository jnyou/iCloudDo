package org.jnyou.eduservice.controller.front;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jnyou.commonutils.R;
import org.jnyou.eduservice.entity.EduCourse;
import org.jnyou.eduservice.entity.EduTeacher;
import org.jnyou.eduservice.service.EduCourseService;
import org.jnyou.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TeacherFrontController
 * @Description: 前台讲师列表
 * @Author: jnyou
 **/
@RestController
@RequestMapping("eduservice/teacher/front")
public class TeacherFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;


    /**
     * 分页查询前台讲师列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("getTeacherFrontPageList/{page}/{size}")
    @ApiOperation(value = "分页讲师列表")
    public R getTeacherFrontPageList( @ApiParam(name = "page", value = "当前页码", required = true)
                                          @PathVariable Long page,

                                      @ApiParam(name = "size", value = "每页记录数", required = true)
                                          @PathVariable Long size){
        Map<String, Object> teachers = teacherService.getTeacherFrontPageList(page,size);
        return R.ok().data(teachers);
    }


    @ApiOperation(value = "根据ID查询讲师信息")
    @GetMapping(value = "{id}")
    public R getTeacherInfoById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        //查询讲师信息
        EduTeacher teacher = teacherService.getById(id);

        //根据讲师id查询这个讲师的课程列表
        List<EduCourse> courseList = courseService.selectByTeacherId(id);

        return R.ok().put("teacher", teacher).put("courseList", courseList);
    }

}