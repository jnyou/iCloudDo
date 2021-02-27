package org.jnyou.eduservice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jnyou.commonutils.R;
import org.jnyou.eduservice.entity.subject.OneSubjectCategory;
import org.jnyou.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author jnyou
 * @since 2020-06-06
 */
@RestController
@RequestMapping("eduservice/subject")
@Api(description = "课程管理")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 通过读取excel添加分类
     * @param mpf
     * @return
     */
    @PostMapping("addSubjectCategory")
    @ApiOperation(value = "添加分类")
    public R addSubjectCategory(MultipartFile file){
        try{
            subjectService.saveSubject(file,subjectService);
            return R.ok().message("导入课程分类成功");
        }catch (Exception e){
            return R.error().message("导入课程分类失败");
        }
    }

    @GetMapping("subjectCategoryListTree")
    @ApiOperation(value = "课程分类列表")
    public R getSubjectCategoryListTree(){
        try{
            List<OneSubjectCategory> listTree = subjectService.getSubjectCategoryListTree();
            return R.ok().put("list",listTree);
        }catch (Exception e){
            return R.error();
        }
    }

}

