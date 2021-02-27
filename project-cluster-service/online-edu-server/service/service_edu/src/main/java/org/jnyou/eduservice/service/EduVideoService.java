package org.jnyou.eduservice.service;

import org.jnyou.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程ID删除小节
     * @param courseId
     */
    void removeVideoByCourseId(String courseId);
}
