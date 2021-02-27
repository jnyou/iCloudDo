package org.jnyou.eduservice.entity.chapter;

import lombok.Data;

import java.util.List;

/**
 * @ClassName yjn
 * @Description: 章节
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
public class ChapterVo {

    private String id;

    private String title;
    /**
     * 一个章节下有多个小节，一对多
     */
    private List<VideoVo> children;

}