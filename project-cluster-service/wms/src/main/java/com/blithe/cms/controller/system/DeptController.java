package com.blithe.cms.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.utils.DataGridView;
import com.blithe.cms.pojo.system.Dept;
import com.blithe.cms.service.system.DeptService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @ClassName DeptController
 * @Description: 部门管理
 * @Author: 夏小颜
 * @Date: 19:12
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/dept")
public class DeptController {
    
    @Autowired
    private DeptService deptService;

    /**
     * 加载部门管理dtree JSON数据
     * @return
     */
    @PostMapping("/loadDeptLeftDtreeJson")
    public DataGridView loadDeptLeftDtreeJson(){
        return deptService.loadDeptLeftDtreeJson();
    }


    /**
     *  查询部门
     * @param dept
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R queryDeptList(Dept dept){
        Page page = new Page<>(dept.getPage(),dept.getLimit(),"ordernum",true);
        Wrapper wrapper = new EntityWrapper();
        wrapper.like(StringUtils.isNotBlank(dept.getTitle()),"title",dept.getTitle());
        wrapper.like(StringUtils.isNotBlank(dept.getAddress()),"address",dept.getAddress());
        wrapper.ge(dept.getStartTime()!=null,"createtime",dept.getStartTime());
        wrapper.le(dept.getEndTime()!=null,"createtime",dept.getEndTime());

        // 接受前台left参数进行渲染数据表格
        if(dept.getId()!=null){
            wrapper.eq("id",dept.getId()).or().eq("pid",dept.getId());
        }
        this.deptService.selectPage(page, wrapper);

        return R.ok().put("count",page.getTotal()).put("data",page.getRecords());

    }


    @PostMapping("/checkDeptChildrenDel")
    public R checkDeptChildrenDel(@RequestBody String id){
        try {
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("pid",id);
            List list = this.deptService.selectList(wrapper);
            if(CollectionUtils.isNotEmpty(list)){
                return R.ok().put("value",true);
            }else {
                this.deptService.deleteById(Integer.parseInt(id));
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 保存
     * @param dept
     * @return
     */
    @PostMapping("/deptSaveOrUpdate")
    public R deptSaveOrUpdate(Dept dept){
        try {
            if(dept.getId() == null){
                dept.setCreatetime(new Date());
                this.deptService.saveDept(dept);
            }else{
                this.deptService.updateDeptById(dept);
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 查询最大顺序码
     * @return
     */
    @GetMapping("/loadDeptMaxOrderNum")
    public R loadDeptMaxOrderNum(){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.orderBy("ordernum",false).last("LIMIT 1");
        Dept dept = this.deptService.selectOne(wrapper);
        if(dept != null && dept.getOrdernum()>0){
            return R.ok().put("error",true).put("value",dept.getOrdernum() + 1);
        }else{
            return R.ok().put("error",false);
        }
    }


}