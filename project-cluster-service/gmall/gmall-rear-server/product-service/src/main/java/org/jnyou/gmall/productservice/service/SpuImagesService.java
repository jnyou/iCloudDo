package org.jnyou.gmall.productservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.productservice.entity.SpuImagesEntity;

import java.util.Map;

/**
 * spu图片
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 * @date 2020-09-24 15:45:06
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

