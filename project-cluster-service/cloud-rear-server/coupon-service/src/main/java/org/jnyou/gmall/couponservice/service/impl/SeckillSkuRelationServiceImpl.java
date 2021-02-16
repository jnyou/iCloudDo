package org.jnyou.gmall.couponservice.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.couponservice.dao.SeckillSkuRelationDao;
import org.jnyou.gmall.couponservice.entity.SeckillSkuRelationEntity;
import org.jnyou.gmall.couponservice.service.SeckillSkuRelationService;
import org.springframework.util.StringUtils;


@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String promotionSessionId = (String) params.get("promotionSessionId");
        IPage<SeckillSkuRelationEntity> page = this.page(
                new Query<SeckillSkuRelationEntity>().getPage(params),
                Wrappers.<SeckillSkuRelationEntity>lambdaQuery().eq(!StringUtils.isEmpty(promotionSessionId),SeckillSkuRelationEntity::getPromotionSessionId,promotionSessionId)
        );

        return new PageUtils(page);
    }

}