package org.jnyou.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jnyou.commonutils.R;
import org.jnyou.eduservice.entity.EduTeacher;
import org.jnyou.eduservice.entity.vo.TeacherQuery;
import org.jnyou.eduservice.service.EduTeacherService;
import org.jnyou.servicebase.exception.IsMeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author jnyou
 * @since 2020-05-30
 */
@RestController
@RequestMapping("eduservice/teacher")
@Api(description = "讲师管理")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询所有讲师  测试整体集成 rest风格
     * @return
     */
    @ApiOperation(value = "查询所有讲师的信息")
    @GetMapping("findAll")
    public R findAllTeacher(){
        return R.ok().put("data",teacherService.list(null));
    }

    /**
     * 逻辑删除讲师信息
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师信息")
    @DeleteMapping("{id}")
    public R logicDel(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

    /**
     *
     * @param currentPage
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("{currentPage}/{limit}")
    public R pageQueryList(@PathVariable long currentPage,@PathVariable long limit){
        /*
          自定义异常测试。。。
        */
//        try {
//            int i = 10 / 0;
//        }catch (Exception e){
//            throw  new IsMeException(-1,"执行自定义的异常处理。。。");
//        }
        Page<EduTeacher> page = new Page<>(currentPage,limit);

        teacherService.page(page,null);

        // 获得总记录数
        long total= page.getTotal();
        // 获得结果集
        List<EduTeacher> records = page.getRecords();

        return R.ok().put("data",records).put("count",total);
    }

    /**
     *
     * @param currentPage
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "根据条件分页查询")
    @PostMapping("pageCondition/{currentPage}/{limit}")
    public R pageQueryByCondition(@PathVariable long currentPage,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(currentPage,limit);

        // 构建条件表达式
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(teacherQuery.getName()),"name",teacherQuery.getName());
        wrapper.eq(!StringUtils.isEmpty(teacherQuery.getLevel()),"level",teacherQuery.getLevel());
        wrapper.ge(!StringUtils.isEmpty(teacherQuery.getBegin()),"gmt_create",teacherQuery.getBegin());
        wrapper.le(!StringUtils.isEmpty(teacherQuery.getEnd()),"gmt_create",teacherQuery.getEnd());
        // 降序排序
        wrapper.orderByDesc("gmt_create");

        teacherService.page(page,wrapper);


        // 获得总记录数
        long total= page.getTotal();
        // 获得结果集
        List<EduTeacher> records = page.getRecords();

        return R.ok().put("data",records).put("count",total);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据讲师id查询信息")
    public R queryTeacherById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().put("teacher",teacher);
    }

    /**
     *
     * @param teacher
     * @return
     */
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){

        boolean flag = teacherService.save(teacher);
        if(flag){
            return R.ok();
        }else{
            return R.error().message("添加失败");
        }
    }

    /**
     *
     * @param teacher
     * @return
     */
    @ApiOperation(value = "更新讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return R.ok();
        }else {
            return R.error().message("修改失败");
        }
    }


}

