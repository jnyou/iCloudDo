package org.jnyou.gmall.productservice.app;

import java.util.Arrays;
import java.util.Map;

import org.jnyou.gmall.productservice.annotation.EagleEye;
import org.jnyou.gmall.productservice.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.jnyou.gmall.productservice.entity.SpuInfoEntity;
import org.jnyou.gmall.productservice.service.SpuInfoService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.R;


/**
 * spu信息
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    @GetMapping("/skuId/{id}")
    public R getSpuInfoBySkuId(@PathVariable("id") Long skuId) {
        SpuInfoEntity spuInfoEntity = spuInfoService.getSpuInfoBySkuId(skuId);
        return R.ok().setData(spuInfoEntity);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("productservice:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * 商品上架
     */
    @PostMapping("/{spuId}/up")
    @EagleEye(desc = "商品上架")
    public R spuUp(@PathVariable("spuId") Long spuId) {
        spuInfoService.up(spuId);
        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("productservice:spuinfo:info")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @EagleEye(desc = "商品信息存储")
    public R save(@RequestBody SpuSaveVo spuSaveVo) {
        spuInfoService.saveSpuInfo(spuSaveVo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("productservice:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("productservice:spuinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
