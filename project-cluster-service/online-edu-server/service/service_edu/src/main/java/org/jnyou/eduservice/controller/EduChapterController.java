package org.jnyou.eduservice.controller;


import org.jnyou.commonutils.R;
import org.jnyou.eduservice.entity.EduChapter;
import org.jnyou.eduservice.entity.chapter.ChapterVo;
import org.jnyou.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程章节表 前端控制器
 * </p>
 *
 * @author jnyou
 * @since 2020-06-07
 */
@RestController
@RequestMapping("eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 课程大纲列表
     * @return
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R queryChapterTree(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().put("list",list);
    }

    @PostMapping("addChapterInfo")
    public R addChapterInfo(@RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return R.ok().message("课程章节添加成功");
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().put("chapter",chapter);
    }

    @PostMapping("updateChapterInfo")
    public R updateChapterInfo(@RequestBody EduChapter chapter){
        chapterService.updateById(chapter);
        return R.ok().message("修改章节添加成功");
    }

    @DeleteMapping("deleteChapterInfo/{chapterId}")
    public R deleteChapterInfo(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapterInfo(chapterId);
        if(flag){
            return R.ok().message("删除章节成功");
        }else {
            return R.error().message("删除章节失败");
        }
    }

}

