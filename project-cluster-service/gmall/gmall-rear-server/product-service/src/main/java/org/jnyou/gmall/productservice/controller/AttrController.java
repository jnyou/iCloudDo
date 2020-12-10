package org.jnyou.gmall.productservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.productservice.entity.AttrEntity;
import org.jnyou.gmall.productservice.entity.ProductAttrValueEntity;
import org.jnyou.gmall.productservice.service.AttrService;
import org.jnyou.gmall.productservice.service.ProductAttrValueService;
import org.jnyou.gmall.productservice.vo.AttrRespVo;
import org.jnyou.gmall.productservice.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 商品属性
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 列表 /product/attr/${type}/list/${this.catId}
     */
    @RequestMapping("/{type}/list/{catId}")
    //@RequiresPermissions("productservice:attr:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("type") String type, @PathVariable("catId") Long catId) {
        PageUtils page = attrService.queryPage(params, catId, type);

        return R.ok().put("page", page);
    }

    /**
     * 获取spu规格 /product/attr/base/listforspu/{spuId}
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R list(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> list = productAttrValueService.baseAttrList(spuId);

        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("productservice:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrRespVo attr = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("productservice:attr:save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("productservice:attr:update")
    public R update(@RequestBody AttrVo attr) {
        attrService.updateAttr(attr);
        return R.ok();
    }

    /**
     * 修改商品规格
     */
    @PostMapping("/update/{spuId}")
    public R update(@PathVariable Long spuId,@RequestBody List<ProductAttrValueEntity> productAttrValueEntities) {
        productAttrValueService.updateSpuAttr(spuId,productAttrValueEntities);
        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("productservice:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
