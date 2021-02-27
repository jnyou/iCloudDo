package org.jnyou.statisticsservice.client;

import org.jnyou.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * 分类名称
 *
 * @ClassName UcenterClient
 * @Description: 统计注册用户
 * @Author: jnyou
 * @create 2020/08/22
 * @module 智慧园区
 **/
@Component
public class UcenterClientImpl implements UcenterClient{

    @Override
    public R countRegister(String date) {
        return R.error().message("超时了.");
    }
}