package org.jnyou.eduservice.controller;


import org.apache.commons.lang3.StringUtils;
import org.jnyou.commonutils.R;
import org.jnyou.eduservice.client.VodClient;
import org.jnyou.eduservice.entity.EduVideo;
import org.jnyou.eduservice.service.EduVideoService;
import org.jnyou.servicebase.exception.IsMeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程小节  视频 前端控制器
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
@RestController
@RequestMapping("eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient client;

    /**
     * 添加小节
     * @param video
     * @return
     */
    @PostMapping("addVideoInfo")
    public R addVideoInfo(@RequestBody EduVideo video){
        videoService.save(video);
        return R.ok().message("课程小节添加成功");
    }

    /**
     * 修改
     * @param video
     * @return
     */
    @PutMapping("updateVideoInfo")
    public R updateVideoInfo(@RequestBody EduVideo video){
        videoService.updateById(video);
        return R.ok().message("课程小节修改成功");
    }

    /**
     * 根据ID查询对象
     * @param videoId
     * @return
     */
    @GetMapping("getVideoInfoById/{videoId}")
    public R getVideoInfoById(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().put("video",video);
    }

    /**
     * 删除小节，使用feign删除阿里云相关视频
     * @param videoId
     * @return
     */
    @DeleteMapping("deleteVideoInfoById/{videoId}")
    public R deleteVideoInfoById(@PathVariable String videoId){
        // 通过小节ID查询到视频的ID
        EduVideo video = videoService.getById(videoId);
        // 通过视频ID删除小节aliyun视频
        if(!StringUtils.isEmpty(video.getVideoSourceId())){
            R result = client.deleteVideoAliyun(video.getVideoSourceId());
            if(result.getCode() == -1){
                throw new IsMeException(-1,"删除视频失败，熔断器。。。!");
            }
        }
        //删除小节
        videoService.removeById(videoId);
        return R.ok().message("课程小节删除成功");
    }

}

