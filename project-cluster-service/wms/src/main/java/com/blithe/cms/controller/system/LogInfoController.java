package com.blithe.cms.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.utils.StringUtil;
import com.blithe.cms.pojo.system.Loginfo;
import com.blithe.cms.service.system.LoginfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName LogIngoController
 * @Description: 登陆日志信息controller
 * @Author: 夏小颜
 * @Date: 12:29
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/log")
public class LogInfoController {

    @Autowired
    private LoginfoService loginfoService;

    /**
     * 查询 含导出功能
     * @return
     */
    @GetMapping("/list")
    public R queryLogList(Loginfo loginfo) {
        EntityWrapper wrapper = new EntityWrapper<>();
        // 条件构造b
        wrapper.like(StringUtils.isNotBlank(loginfo.getLoginname()),"loginname",loginfo.getLoginname());
        wrapper.like(StringUtils.isNotBlank(loginfo.getLoginip()),"loginip",loginfo.getLoginip());
        wrapper.ge(loginfo.getStartTime()!=null,"logintime",loginfo.getStartTime());
        wrapper.le(loginfo.getEndTime()!=null,"logintime",loginfo.getEndTime());
        wrapper.orderBy("logintime",false);
        if(!StringUtil.isNull(loginfo.getPage()) && !StringUtil.isNull(loginfo.getLimit())){
            Page page = new Page(loginfo.getPage(),loginfo.getLimit());
            this.loginfoService.selectPage(page, wrapper);
            return R.ok().put("count",page.getTotal()).put("data",page.getRecords());
        }else {
            List list = this.loginfoService.selectList(wrapper);
            return R.ok().put("data",list);
        }
    }


    @PostMapping(value = "/deleteBatch")
    public R deleteBatch(@RequestBody List<Map<String,Object>> params){
        try {
            if(CollectionUtils.isNotEmpty(params)){
                for (Map<String, Object> param : params) {
                    this.loginfoService.deleteByMap(param);
                }
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

}