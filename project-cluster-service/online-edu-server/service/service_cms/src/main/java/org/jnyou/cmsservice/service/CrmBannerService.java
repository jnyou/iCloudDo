package org.jnyou.cmsservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jnyou.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-25
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有的轮播图
     * @return
     */
    List<CrmBanner> getAllBanner();

    void pageBanner(Page<CrmBanner> pageParam, Object o);

    CrmBanner getBannerById(String id);

    void saveBanner(CrmBanner banner);

    void updateBannerById(CrmBanner banner);

    void removeBannerById(String id);
}
