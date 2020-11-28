package org.jnyou.gmall.productservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.productservice.entity.AttrEntity;
import org.jnyou.gmall.productservice.vo.AttrVo;

import java.util.Map;

/**
 * 商品属性
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params,Long catId);

    void saveAttr(AttrVo attr);
}

