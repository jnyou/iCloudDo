package org.jnyou.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName yjn
 * @Description: 小节
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
public class VideoVo {

    private String id;

    private String title;

    @ApiModelProperty("视频id")
    private String videoSourceId;

}