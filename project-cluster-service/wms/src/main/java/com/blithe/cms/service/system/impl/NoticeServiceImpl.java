package com.blithe.cms.service.system.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.mapper.system.NoticeMapper;
import com.blithe.cms.pojo.system.Notice;
import com.blithe.cms.service.system.NoticeService;
import org.springframework.stereotype.Service;

/**
 * @Author: youjiannan
 * @Description: 系统公告模块
 * @Date: 2020/3/19
 * @Param:
 * @Return:
 **/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}
