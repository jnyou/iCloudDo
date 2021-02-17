package org.jnyou.gmall.couponservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.gmall.couponservice.dao.SeckillSessionDao;
import org.jnyou.gmall.couponservice.entity.SeckillSessionEntity;
import org.jnyou.gmall.couponservice.entity.SeckillSkuRelationEntity;
import org.jnyou.gmall.couponservice.service.SeckillSessionService;
import org.jnyou.gmall.couponservice.service.SeckillSkuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {

    @Autowired
    SeckillSkuRelationService seckillSkuRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SeckillSessionEntity> seckillSessionService() {
        List<SeckillSessionEntity> list = this.list(Wrappers.<SeckillSessionEntity>lambdaQuery().between(SeckillSessionEntity::getStartTime, startTime(), endTime()));
        if (!CollectionUtils.isEmpty(list)) {
            List<SeckillSessionEntity> collect = list.stream().map(session -> {
                List<SeckillSkuRelationEntity> relationEntities = seckillSkuRelationService.list(Wrappers.<SeckillSkuRelationEntity>lambdaQuery().eq(SeckillSkuRelationEntity::getPromotionSessionId, session.getId()));
                session.setEntities(relationEntities);
                return session;
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }


    private String startTime() {
        LocalDate now = LocalDate.now();
        LocalDateTime startTime = LocalDateTime.of(now, LocalTime.MIN);
        String format = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("扫描需要上架的商品开始时间：" + format);
        return format;
    }

    private String endTime() {
        LocalDate now = LocalDate.now();
        LocalDate date = now.plusDays(2);
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
        String format = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("扫描需要上架的商品结束时间：" + format);
        return format;
    }

}