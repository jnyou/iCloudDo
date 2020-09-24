package org.jnyou.gmall.productservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.productservice.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 * @date 2020-09-24 15:45:06
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

