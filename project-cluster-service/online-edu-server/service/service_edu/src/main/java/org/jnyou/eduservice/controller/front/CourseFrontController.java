package org.jnyou.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.commonutils.JwtUtils;
import org.jnyou.commonutils.R;
import org.jnyou.eduservice.client.OrderClient;
import org.jnyou.eduservice.entity.EduCourse;
import org.jnyou.eduservice.entity.chapter.ChapterVo;
import org.jnyou.commonutils.entity.CourseDetailsVo;
import org.jnyou.eduservice.entity.frontvo.CourseFrontQueryVo;
import org.jnyou.eduservice.service.EduChapterService;
import org.jnyou.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseFrontController
 * @Description: 前台课程列表
 * @Author: jnyou
 **/
@RestController
@Slf4j
@RequestMapping("eduservice/course/front")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "分页课程列表")
    @PostMapping("getFrontCourseListByPage/{page}/{size}")
    public R getFrontCourseListByPage(@ApiParam(name = "page", value = "当前页码",required = true)  @PathVariable long page,
                                      @ApiParam(name = "size", value = "每页记录数",required = true) @PathVariable long size,
                                      @ApiParam(name = "courseQueryVo", value = "查询对象", required = false) @RequestBody CourseFrontQueryVo courseQueryVo){
        Page<EduCourse> pageParam = new Page<EduCourse>(page, size);
        Map<String, Object> map = courseService.pageListWeb(pageParam,courseQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询课程相关的所有信息")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId, HttpServletRequest request){

        //查询课程信息和讲师信息
        CourseDetailsVo courseDetailsVo = courseService.selectCourseDetailsById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        // 远程调用判断用户是否购买课程
        boolean isBuyCourse = false;
        if(null != JwtUtils.getMemberIdByJwtToken(request)){
            isBuyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        }

        return R.ok().put("course", courseDetailsVo).put("chapterVoList", chapterVoList).put("isbuyCourse",isBuyCourse);
    }

    /**
     * 根据课程id查询课程信息
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    @GetMapping("getDto/{courseId}")
    public CourseDetailsVo getCourseInfoDto(@PathVariable String courseId){
        CourseDetailsVo courseInfoForm = courseService.selectCourseDetailsById(courseId);
        return courseInfoForm;
    }


}