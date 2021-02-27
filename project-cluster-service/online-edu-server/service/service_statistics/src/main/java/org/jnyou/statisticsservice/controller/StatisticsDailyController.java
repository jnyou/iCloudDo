package org.jnyou.statisticsservice.controller;


import org.jnyou.commonutils.R;
import org.jnyou.statisticsservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author jnyou
 * @since 2020-08-22
 */
@RestController
@RequestMapping("staservice/stat")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    /**
     * 获取统计数据
     * @param data
     * @return
     * @Author jnyou
     * @Date 2020/8/22
     */
    @GetMapping("{date}")
    public R createStatisticsByDate(@PathVariable String date) {
        dailyService.createStatisticsByDay(date);
        return R.ok();
    }


    /**
     * 返回echarts数据
     * @param begin
     * @param end
     * @param type
     * @return
     * @Author jnyou
     * @Date 2020/8/22
     */
    @GetMapping("show-chart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }

}

