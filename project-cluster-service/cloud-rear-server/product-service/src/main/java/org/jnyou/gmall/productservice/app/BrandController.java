package org.jnyou.gmall.productservice.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jnyou.common.valid.AddGroup;
import org.jnyou.common.valid.UpdateGroup;
import org.jnyou.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.jnyou.gmall.productservice.entity.BrandEntity;
import org.jnyou.gmall.productservice.service.BrandService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.R;


/**
 * 品牌
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("productservice:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 查询指定的品牌ID数据
     */
    @RequestMapping("/infos")
    public R info(@RequestParam("brandIds") List<Long> brandIds){
        List<BrandEntity> brands = brandService.getBrandsByIds(brandIds);
        return R.ok().put("data", brands);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("productservice:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("productservice:brand:save")
    // BindingResult：校验结果
    public R save(@Validated(value = {AddGroup.class}) @RequestBody BrandEntity brand/*, BindingResult result*/){
        // 全局异常进行捕获
//        if(result.hasErrors()){
//            Map<String,String> map = new HashMap<>(124);
//            // 获取校验的结果：
//            result.getFieldErrors().forEach(item -> {
//                // 获取到校验消息
//                String defaultMessage = item.getDefaultMessage();
//                // 获取发生错误的属性名称
//                String field = item.getField();
//                map.put(field,defaultMessage);
//            });
//            return R.error(400,"提交的数据不合法").put("data",map);
//        }else {
//            brandService.save(brand);
//        }
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("productservice:brand:update")
    public R update(@Validated(value = UpdateGroup.class) @RequestBody BrandEntity brand){
		brandService.updateDetail(brand);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated(value = UpdateStatusGroup.class) @RequestBody BrandEntity brand){
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("productservice:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }
}
