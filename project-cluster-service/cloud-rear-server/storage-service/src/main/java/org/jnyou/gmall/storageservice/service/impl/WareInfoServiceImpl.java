package org.jnyou.gmall.storageservice.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.storageservice.dao.WareInfoDao;
import org.jnyou.gmall.storageservice.entity.WareInfoEntity;
import org.jnyou.gmall.storageservice.feign.MemberFeignClient;
import org.jnyou.gmall.storageservice.service.WareInfoService;
import org.jnyou.gmall.storageservice.vo.MemberAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Autowired
    MemberFeignClient memberFeignClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<WareInfoEntity> lambdaQueryWrapper = Wrappers.<WareInfoEntity>lambdaQuery();

        if (!StringUtils.isEmpty(params.get("key"))) {
            lambdaQueryWrapper.eq(WareInfoEntity::getId, params.get("key"))
                    .or()
                    .like(WareInfoEntity::getName, params.get("key"))
                    .or()
                    .like(WareInfoEntity::getAddress, params.get("key"))
                    .or()
                    .like(WareInfoEntity::getAreacode, params.get("key"));
        }

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                lambdaQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public BigDecimal getFare(Long addrId) {
        R r = memberFeignClient.info(addrId);
        if(r.getCode() == 0){
            MemberAddressVo data = r.getData("memberReceiveAddress",new TypeReference<MemberAddressVo>() {
            });
            if(!Objects.isNull(data)){
                // TODO 远程调用第三方物流服务
                // 模拟
                String phone = data.getPhone();
                String fare = phone.substring(phone.length() - 1, phone.length());
                return new BigDecimal(fare);
            }
        }
        return null;
    }

}