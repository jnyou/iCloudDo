package org.jnyou.eduservice.client;

import io.swagger.annotations.ApiParam;
import org.jnyou.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName yjn
 * @Description: 定义调用方法的路径和接口
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class) // 调用的服务名称 失败后调用的方法
public interface VodClient {

    /**
     * 根据视频ID删除阿里云视频
     * @PathVariable 一定要指定参数名称，否则出错
     * @param videoSourceId
     * @return
     */
    @DeleteMapping("eduvod/video/deleteVideoAliyun/{videoSourceId}")
    public R deleteVideoAliyun(@PathVariable("videoSourceId") String videoSourceId);

    /**
     * 批量删除云端视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("eduvod/video/deleteBatch")
    public R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);

}