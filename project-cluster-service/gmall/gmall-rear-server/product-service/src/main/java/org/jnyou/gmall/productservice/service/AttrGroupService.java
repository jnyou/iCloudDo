package org.jnyou.gmall.productservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.productservice.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params,Long catId);
}

