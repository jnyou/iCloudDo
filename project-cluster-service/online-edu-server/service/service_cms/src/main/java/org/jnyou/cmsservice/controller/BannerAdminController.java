package org.jnyou.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jnyou.cmsservice.entity.CrmBanner;
import org.jnyou.cmsservice.service.CrmBannerService;
import org.jnyou.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * 后台管理banner
 * </p>
 *
 * @author jnyou
 * @since 2020-06-25
 */
@RestController
@RequestMapping("cmsservice/adminbanner")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;


    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageBanner/{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<CrmBanner> pageParam = new Page<>(page, limit);
        bannerService.pageBanner(pageParam,null);
        return R.ok().put("items", pageParam.getRecords()).put("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("getBannerById/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getBannerById(id);
        return R.ok().put("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("saveBanner")
    public R save(@RequestBody CrmBanner banner) {
        bannerService.saveBanner(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("updateBanner")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateBannerById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("removeBannerById/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeBannerById(id);
        return R.ok();
    }

}

