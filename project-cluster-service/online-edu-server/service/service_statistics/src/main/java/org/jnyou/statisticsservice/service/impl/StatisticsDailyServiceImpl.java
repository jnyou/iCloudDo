package org.jnyou.statisticsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.jnyou.commonutils.R;
import org.jnyou.statisticsservice.client.UcenterClient;
import org.jnyou.statisticsservice.entity.StatisticsDaily;
import org.jnyou.statisticsservice.mapper.StatisticsDailyMapper;
import org.jnyou.statisticsservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-08-22
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String date) {

        //删除已存在的统计对象
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", date);
        baseMapper.delete(dayQueryWrapper);

        //获取统计信息
        Integer registerNum = (Integer) ucenterClient.countRegister(date).getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(date);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        // 只查询查询出类型和时间
        dayQueryWrapper.select(type, "date_calculated");
        dayQueryWrapper.between("date_calculated", begin, end);
        List<StatisticsDaily> stats = baseMapper.selectList(dayQueryWrapper);

        // 需要返回两部分数据，日期和数量，echarts需要返回数组结构，所以创建两个list集合封装返回
        // 创建日期的list集合
        List<String> dateList = new ArrayList<>();

        // 创建数量的list集合
        List<Integer> dataList = new ArrayList<>();

        stats.forEach(daily -> {
            // 封装日期集合
            dateList.add(daily.getDateCalculated());
            // 封装数量
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        });
        Map<String, Object> map = new HashMap<>(1024);
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        return map;
    }
}
