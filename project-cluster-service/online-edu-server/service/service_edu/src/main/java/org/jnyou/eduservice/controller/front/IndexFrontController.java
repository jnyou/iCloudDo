package org.jnyou.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jnyou.commonutils.R;
import org.jnyou.eduservice.entity.EduCourse;
import org.jnyou.eduservice.entity.EduTeacher;
import org.jnyou.eduservice.service.EduCourseService;
import org.jnyou.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName yjn
 * @Description: 前台首页controller
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@RestController
@RequestMapping("eduservice/index")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询前8条热门课程，查询前4条名师
     * @return
     */
    @GetMapping("info")
    public R index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().put("courseList",eduList).put("teacherList",teacherList);
    }

}