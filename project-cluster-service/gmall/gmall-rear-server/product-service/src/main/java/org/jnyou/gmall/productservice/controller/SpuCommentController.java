package org.jnyou.gmall.productservice.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.jnyou.gmall.productservice.entity.SpuCommentEntity;
import org.jnyou.gmall.productservice.service.SpuCommentService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.R;



/**
 * 商品评价
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 * @date 2020-09-24 16:19:42
 */
@RestController
@RequestMapping("productservice/spucomment")
public class SpuCommentController {
    @Autowired
    private SpuCommentService spuCommentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("productservice:spucomment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuCommentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("productservice:spucomment:info")
    public R info(@PathVariable("id") Long id){
		SpuCommentEntity spuComment = spuCommentService.getById(id);

        return R.ok().put("spuComment", spuComment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("productservice:spucomment:save")
    public R save(@RequestBody SpuCommentEntity spuComment){
		spuCommentService.save(spuComment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("productservice:spucomment:update")
    public R update(@RequestBody SpuCommentEntity spuComment){
		spuCommentService.updateById(spuComment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("productservice:spucomment:delete")
    public R delete(@RequestBody Long[] ids){
		spuCommentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
