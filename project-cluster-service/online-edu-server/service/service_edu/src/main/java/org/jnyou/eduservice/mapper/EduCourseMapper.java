package org.jnyou.eduservice.mapper;

import org.jnyou.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jnyou.commonutils.entity.CourseDetailsVo;
import org.jnyou.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程ID查询发布的课程信息
     * @param courseId
     * @return
     */
    CoursePublishVo queryCoursePublishInfo(String courseId);

    /**
     * 根据课程ID查询课程相关的所有信息
     * @param courseId
     * @return
     */
    CourseDetailsVo queryCourseBaseInfo(String courseId);
}
