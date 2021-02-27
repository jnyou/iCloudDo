package org.jnyou.cmsservice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jnyou.cmsservice.entity.CrmBanner;
import org.jnyou.cmsservice.service.CrmBannerService;
import org.jnyou.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * 前台管理banner
 * </p>
 *
 * @author jnyou
 * @since 2020-06-25
 */
@RestController
@RequestMapping("cmsservice/frontbanner")
@Api(description = "网站首页Banner列表")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.getAllBanner();
        return R.ok().put("bannerList", list);
    }

}

