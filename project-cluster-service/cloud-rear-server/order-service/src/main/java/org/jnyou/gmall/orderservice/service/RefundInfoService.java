package org.jnyou.gmall.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.orderservice.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

