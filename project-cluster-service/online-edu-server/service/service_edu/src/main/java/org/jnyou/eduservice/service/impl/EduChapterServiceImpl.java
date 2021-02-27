package org.jnyou.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.eduservice.entity.EduChapter;
import org.jnyou.eduservice.entity.EduVideo;
import org.jnyou.eduservice.entity.chapter.ChapterVo;
import org.jnyou.eduservice.entity.chapter.VideoVo;
import org.jnyou.eduservice.mapper.EduChapterMapper;
import org.jnyou.eduservice.mapper.EduVideoMapper;
import org.jnyou.eduservice.service.EduChapterService;
import org.jnyou.servicebase.exception.IsMeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程章节表 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoMapper videoMapper;

    /**
     * 根据课程ID查询课程大纲列表
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        // 根据课程ID查询课程章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = this.baseMapper.selectList(wrapper);
        // 根据课程ID查询所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideos = videoMapper.selectList(wrapperVideo);

        // 返回的最终章节小节树
        List<ChapterVo> finalTree = new ArrayList<>();

        // 存放小节的集合
        List<VideoVo> videoVos = new ArrayList<>();
        // 遍历所有的章节
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalTree.add(chapterVo);
            // 遍历小节
            for (EduVideo eduVideo : eduVideos) {
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        }
        return finalTree;
    }

    @Override
    public boolean deleteChapterInfo(String chapterId) {
        // 如果章节有小节，就不让删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        Integer count = videoMapper.selectCount(wrapper);
        if(count > 0){
            throw new IsMeException(-1,"该章节下含有小节，不能删除");
        } else {
            // 删除章节
            int result = this.baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    /**
     * 根据课程ID删除章节
     * @param courseId
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        this.baseMapper.delete(wrapper);
    }
}
