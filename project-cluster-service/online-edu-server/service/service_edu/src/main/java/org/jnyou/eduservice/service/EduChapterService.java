package org.jnyou.eduservice.service;

import org.jnyou.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程章节表 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程ID查询课程章节和小节
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 判断是否有小节删除章节
     * @param chapterId
     * @return
     */
    boolean deleteChapterInfo(String chapterId);

    /**
     * 根据课程ID删除章节
     * @param courseId
     */
    void removeChapterByCourseId(String courseId);
}
