package org.jnyou.gmall.storageservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.storageservice.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

