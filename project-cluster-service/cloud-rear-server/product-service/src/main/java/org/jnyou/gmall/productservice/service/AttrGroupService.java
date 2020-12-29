package org.jnyou.gmall.productservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.productservice.entity.AttrGroupEntity;
import org.jnyou.gmall.productservice.vo.AttrGroupWithAttrsVo;
import org.jnyou.gmall.productservice.vo.SkuItemVo;
import org.jnyou.gmall.productservice.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params,Long catId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatId(Long catelogId);

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

