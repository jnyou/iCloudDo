package org.jnyou.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jnyou.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.commonutils.entity.CourseDetailsVo;
import org.jnyou.eduservice.entity.frontvo.CourseFrontQueryVo;
import org.jnyou.eduservice.entity.vo.CourseInfoVo;
import org.jnyou.eduservice.entity.vo.CoursePublishVo;
import org.jnyou.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程信息
     * @param courseInfoVo
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程ID查询课程信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfoByCourseId(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     * @return
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程ID查询发布的课程信息
     * @param courseId
     * @return
     */
    CoursePublishVo queryCoursePublishInfo(String courseId);

    /**
     * 根据课程ID发布课程
      * @param id
     * @return
     */
    boolean publishCourseById(String id);

    /**
     * 分页条件查询课程列表
     * @param page
     * @param limit
     * @param courseQuery
     * @return
     */
    Page<EduCourse> queryPageCondition(Integer page, Integer limit, CourseQuery courseQuery);

    /**
     * 删除课程所有信息
     * @param courseId
     */
    void removeCourse(String courseId);

    /**
     * 根据讲师ID查询讲师的所有课程
     * @param id
     * @return
     */
    List<EduCourse> selectByTeacherId(String id);

    /**
     * 分页条件查询前台课程列表
     * @param pageParam
     * @param courseQueryVo
     * @return
     */
    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseFrontQueryVo courseQueryVo);

    /**
     * 根据课程id查询课程相关的基本信息
     * @param courseId
     * @return
     */
    CourseDetailsVo selectCourseDetailsById(String courseId);
}
