package org.jnyou.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jnyou.eduservice.client.VodClient;
import org.jnyou.eduservice.entity.EduVideo;
import org.jnyou.eduservice.mapper.EduVideoMapper;
import org.jnyou.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    /**
     * 根据课程ID删除小节,删除阿里云视频
     * @param courseId
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 根据课程ID查询所有的视频ID
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);
        List<String> videoIds = new ArrayList<>();
        // 将List<EduVideo>变成List<String>
        for (EduVideo eduVideo : eduVideos) {
            if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }
        //调用vod服务删除远程视频
        if(videoIds.size() > 0){
            // 删除多个视频
            vodClient.removeVideoList(videoIds);
        }

        // 删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        this.baseMapper.delete(wrapper);
    }
}
