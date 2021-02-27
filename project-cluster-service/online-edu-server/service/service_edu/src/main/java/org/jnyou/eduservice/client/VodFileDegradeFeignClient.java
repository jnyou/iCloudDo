package org.jnyou.eduservice.client;

import org.jnyou.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName yjn
 * @Description: 调度接口出现异常或者错误时才调用的方法，容错方法
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteVideoAliyun(String videoSourceId) {
        return R.error().message("删除视频出错了！");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        return R.error().message("time out");
    }
}