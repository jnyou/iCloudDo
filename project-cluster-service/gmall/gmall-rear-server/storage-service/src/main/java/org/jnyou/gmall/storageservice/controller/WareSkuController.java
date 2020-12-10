package org.jnyou.gmall.storageservice.controller;

import org.jnyou.common.to.SkuHasStockVo;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.storageservice.entity.WareSkuEntity;
import org.jnyou.gmall.storageservice.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 商品库存
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 查询sku是否有库存
     */
    @PostMapping("/hasStock")
    public R<List<SkuHasStockVo>> getSkuHasStock(@RequestBody List<Long> skuIds) {
        List<SkuHasStockVo> skuHasStockVos = wareSkuService.getSkuHasStock(skuIds);
        R<List<SkuHasStockVo>> ok = R.ok();
        ok.setData(skuHasStockVos);
        return ok;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("storageservice:waresku:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("storageservice:waresku:info")
    public R info(@PathVariable("id") Long id) {
        WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("storageservice:waresku:save")
    public R save(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("storageservice:waresku:update")
    public R update(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("storageservice:waresku:delete")
    public R delete(@RequestBody Long[] ids) {
        wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
