package org.jnyou.gmall.storageservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.storageservice.entity.PurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
