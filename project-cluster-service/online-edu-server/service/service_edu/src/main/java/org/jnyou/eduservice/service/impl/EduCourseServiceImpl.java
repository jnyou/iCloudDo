package org.jnyou.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jnyou.commonutils.Constast;
import org.jnyou.eduservice.entity.EduCourse;
import org.jnyou.eduservice.entity.EduCourseDescription;
import org.jnyou.commonutils.entity.CourseDetailsVo;
import org.jnyou.eduservice.entity.frontvo.CourseFrontQueryVo;
import org.jnyou.eduservice.entity.vo.CourseInfoVo;
import org.jnyou.eduservice.entity.vo.CoursePublishVo;
import org.jnyou.eduservice.entity.vo.CourseQuery;
import org.jnyou.eduservice.mapper.EduCourseDescriptionMapper;
import org.jnyou.eduservice.mapper.EduCourseMapper;
import org.jnyou.eduservice.service.EduChapterService;
import org.jnyou.eduservice.service.EduCourseService;
import org.jnyou.eduservice.service.EduVideoService;
import org.jnyou.servicebase.exception.IsMeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionMapper descriptionMapper;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduCourseMapper courseMapper;

    /**
     * 添加课程基本info
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        // 1、向课程表添加基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert = this.baseMapper.insert(course);
        if(insert == 0){
            // 添加失败
            throw new IsMeException(-1,"添加课程信息失败");
        }

        // 获取添加成功之后的课程id
        String cid = course.getId();

        // 2、向课程简介表添加简介信息
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        // 课程id和课程简介的id应该一致，他们是一对一的关系
        description.setId(cid);
        int insert1 = descriptionMapper.insert(description);
        if(insert1 == 0){
            throw new IsMeException(-1,"课程简介信息保存失败");
        }
        return cid;
    }

    /**
     * 根据课程ID查询课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoByCourseId(String courseId) {
        // 课程信息
        EduCourse course = this.baseMapper.selectById(courseId);
        // 课程简介信息
        EduCourseDescription courseDescription = descriptionMapper.selectById(courseId);

        // 封装VO对象
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    /**
     * 修改课程信息
     * @return
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int n = this.baseMapper.updateById(eduCourse);
        // 修改课程简介
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        description.setId(courseInfoVo.getId());
        int m = descriptionMapper.updateById(description);
        if(n == 0){
            throw new IsMeException(-1,"修改课程信息失败");
        }
        if(m == 0){
            throw new IsMeException(-1,"修改课程简介失败");
        }
    }

    /**
     * 根据课程ID查询发布的课程信息
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishVo queryCoursePublishInfo(String courseId) {
        CoursePublishVo coursePublishVo = this.baseMapper.queryCoursePublishInfo(courseId);
        return coursePublishVo;
    }

    /**
     * 根据课程ID发布课程
     * @param id
     */
    @Override
    public boolean publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus(Constast.COURSE_NORMAL);
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    @Override
    public Page<EduCourse> queryPageCondition(Integer page, Integer limit, CourseQuery courseQuery) {
        Page<EduCourse> coursePage = new Page<>(page,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        if (null != courseQuery){
            wrapper.like(StringUtils.isNotEmpty(courseQuery.getTitle()),"title",courseQuery.getTitle());
            wrapper.eq(StringUtils.isNotEmpty(courseQuery.getTeacherId()),"teacher_id",courseQuery.getTeacherId());
            wrapper.eq(StringUtils.isNotEmpty(courseQuery.getSubjectParentId()),"subject_parent_id",courseQuery.getSubjectParentId());
            wrapper.eq(StringUtils.isNotEmpty(courseQuery.getSubjectId()),"subject_id",courseQuery.getSubjectId());
            wrapper.eq(StringUtils.isNotEmpty(courseQuery.getStatus()),"status",courseQuery.getStatus());
        }
        this.baseMapper.selectPage(coursePage,wrapper);
        return coursePage;
    }

    /**
     * 删除所有的课程信息
     * @param courseId
     */
    @Override
    public void removeCourse(String courseId) {
        // 根据课程ID删除小节、视频
        videoService.removeVideoByCourseId(courseId);
        //根据课程ID删除章节
        chapterService.removeChapterByCourseId(courseId);
        // 根据课程ID删除课程简介
        descriptionMapper.deleteById(courseId);

        //删除OSS云端封面 TODO

        // 根据课程ID删除课程基本信息
        int res = baseMapper.deleteById(courseId);
        if(res == 0){
            throw new IsMeException(-1,"删除失败");
        }
    }

    @Override
    public List<EduCourse> selectByTeacherId(String id) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("teacher_id", id);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");

        List<EduCourse> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseFrontQueryVo courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        // 一级分类
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        // 二级分类
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        // 关注度排序
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        // 最新排序
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        // 价格排序
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam,queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>(1024);
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseDetailsVo selectCourseDetailsById(String courseId) {
        return courseMapper.queryCourseBaseInfo(courseId);
    }
}
