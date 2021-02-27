package org.jnyou.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.jnyou.commonutils.R;
import org.jnyou.servicebase.exception.IsMeException;
import org.jnyou.vodservice.service.VodService;
import org.jnyou.vodservice.utils.AliyunVodSDKUtils;
import org.jnyou.vodservice.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName yjn
 * @Description: 视频点播
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@RestController
@RequestMapping("eduvod/video")
@Api(description="阿里云视频点播微服务")
public class VodServiceController {

    @Autowired
    private VodService vodService;

    /**
     * 上传文件到aliyun点播服务
     * @param file
     * @return
     */
    @PostMapping("uploadVideoAliyun")
    public R uploadVideoAliyun(@ApiParam(name = "file", value = "文件", required = true) @RequestParam("file") MultipartFile file){
        // 返回上传的ID值
        String videoId = vodService.uploadVideoAliyun(file);
        return R.ok().message("视频上传成功").put("videoId", videoId);
    }

    /**
     * 根据视频ID删除阿里云视频
     * @param videoId
     * @return
     */
    @DeleteMapping("deleteVideoAliyun/{videoSourceId}")
    public R deleteVideoAliyun(@PathVariable String videoSourceId){
        try{
            // 初始化视频点播服务对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 创建一个删除视频的request
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频ID
            request.setVideoIds(videoSourceId);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);

            return R.ok().message("视频删除成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new IsMeException(-1,"删除失败");
        }
    }

    /**
     * 批量删除云端视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("deleteBatch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List<String> videoIdList){

        vodService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId){
        // 初始化视频点播服务对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        // 创建一个获取视频凭证的request
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        //向request设置视频ID
        request.setVideoId(videoId);

        GetVideoPlayAuthResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        // 获取响应的凭证
        String playAuth = response.getPlayAuth();

        //返回结果
        return R.ok().message("获取凭证成功").put("playAuth", playAuth);
    }
}

