package org.jnyou.gmall.storageservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.storageservice.entity.WareInfoEntity;
import org.jnyou.gmall.storageservice.vo.FareVo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 仓库信息
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据收货详细信息
     * @param addrId
     * @Author JnYou
     */
    FareVo getFare(Long addrId);
}

