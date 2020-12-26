package org.jnyou.gmall.productservice.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.productservice.entity.BrandEntity;
import org.jnyou.gmall.productservice.entity.CategoryBrandRelationEntity;
import org.jnyou.gmall.productservice.service.CategoryBrandRelationService;
import org.jnyou.gmall.productservice.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 品牌分类关联
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("productservice:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 关联分类查询
     */
    @GetMapping("/catelog/list")
    //@RequiresPermissions("productservice:attrattrgrouprelation:list")
    public R catelogList(@RequestParam("brandId") Long brandId){
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
        return R.ok().put("data", data);
    }

    /**
     * 分类关联的品牌查询。  /product/categorybrandrelation/brands/list
     */
    @GetMapping("/brands/list")
    public R categorybrandrelation(@RequestParam(value = "catId",required = true) Long catId){
        List<BrandEntity> brandEntities =  categoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> brandVos = brandEntities.stream().map(item -> {
            BrandVo brandVo = new BrandVo().setBrandId(item.getBrandId()).setBrandName(item.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data", brandVos);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("productservice:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("productservice:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("productservice:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("productservice:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
