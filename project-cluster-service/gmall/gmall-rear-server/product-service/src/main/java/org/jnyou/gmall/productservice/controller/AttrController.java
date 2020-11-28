package org.jnyou.gmall.productservice.controller;

import java.util.Arrays;
import java.util.Map;

import org.jnyou.gmall.productservice.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.jnyou.gmall.productservice.entity.AttrEntity;
import org.jnyou.gmall.productservice.service.AttrService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.R;



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

    /**
     * 列表 /product/attr/${type}/list/${this.catId}
     */
    @RequestMapping("/base/list/{catId}")
    //@RequiresPermissions("productservice:attr:list")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("catId") Long catId){
        PageUtils page = attrService.queryPage(params,catId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("productservice:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("productservice:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("productservice:attr:update")
    public R update(@RequestBody AttrEntity attr){
		attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("productservice:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
