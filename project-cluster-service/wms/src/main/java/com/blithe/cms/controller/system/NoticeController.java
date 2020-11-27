package com.blithe.cms.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.tools.HttpContextUtils;
import com.blithe.cms.pojo.system.Notice;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.system.NoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName NoticeController
 * @Description: 系统公告模块
 * @Author: 夏小颜
 * @Date: 18:38
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public R queryNoticeList(Notice notice){
        Page<Notice> page = new Page<Notice>(notice.getPage(),notice.getLimit(),"createtime",false);
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.like(StringUtils.isNotBlank(notice.getTitle()),"title",notice.getTitle());
        wrapper.like(StringUtils.isNotBlank(notice.getOpername()),"opername",notice.getOpername());
        wrapper.ge(notice.getStartTime()!=null,"createtime",notice.getStartTime());
        wrapper.le(notice.getEndTime()!=null,"createtime",notice.getEndTime());
        noticeService.selectPage(page, wrapper);
        return R.ok().put("count",page.getTotal()).put("data",page.getRecords());
    }

    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestParam("params") String params){
        try {
            if(params.length() > 0){
                String[] ids = params.split(",");
                noticeService.deleteBatchIds(Arrays.asList(ids));
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }

        return R.ok();
    }


    @PostMapping("/noticeSaveOrUpdate")
    public R noticeSaveOrUpdate(Notice notice){
        try {
            if(notice.getId() == null){
                notice.setCreatetime(new Date());
                SysUser user = (SysUser) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("user");
                notice.setOpername(user.getName());
                this.noticeService.insert(notice);
            }else{
                this.noticeService.updateById(notice);
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

}