package org.jnyou.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jnyou.cmsservice.entity.CrmBanner;
import org.jnyou.cmsservice.mapper.CrmBannerMapper;
import org.jnyou.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-25
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    private CrmBannerMapper bannerMapper;

    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> getAllBanner() {
        // 需求：显示排序后前两条的banner图
        // 根据id进行降序排序，显示前两条
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last(" limit 2");
        List<CrmBanner> banners = bannerMapper.selectList(wrapper);
        return banners;
    }

    @Override
    public void pageBanner(Page<CrmBanner> pageParam, Object o) {
        baseMapper.selectPage(pageParam,null);
    }

    @Override
    public CrmBanner getBannerById(String id) {
        return baseMapper.selectById(id);
    }

    @CachePut(value = "banner", key = "'saveBanner'")
    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }
}
