package org.jnyou.gmall.productservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.productservice.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

