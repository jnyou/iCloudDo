package org.jnyou.gmall.productservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jnyou.gmall.productservice.config.MyThreadConfig;
import org.jnyou.gmall.productservice.entity.SkuImagesEntity;
import org.jnyou.gmall.productservice.entity.SpuInfoDescEntity;
import org.jnyou.gmall.productservice.service.*;
import org.jnyou.gmall.productservice.vo.SkuItemSaleAttrVo;
import org.jnyou.gmall.productservice.vo.SkuItemVo;
import org.jnyou.gmall.productservice.vo.SpuItemAttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.productservice.dao.SkuInfoDao;
import org.jnyou.gmall.productservice.entity.SkuInfoEntity;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        LambdaQueryWrapper<SkuInfoEntity> lambdaQueryWrapper = Wrappers.<SkuInfoEntity>lambdaQuery().and(!StringUtils.isEmpty(params.get("key")), (k) -> {
            k.eq(SkuInfoEntity::getSkuId, params.get("key")).or().like(SkuInfoEntity::getSkuName, params.get("key"));
        })
                .eq(!StringUtils.isEmpty(params.get("brandId")) && !"0".equalsIgnoreCase((String) params.get("brandId")), SkuInfoEntity::getBrandId, params.get("brandId"))
                .eq(!StringUtils.isEmpty(params.get("catelogId")) && !"0".equalsIgnoreCase((String) params.get("catelogId")), SkuInfoEntity::getCatalogId, params.get("catelogId"))
                .ge(!StringUtils.isEmpty(params.get("min")), SkuInfoEntity::getPrice, params.get("min"))
                .le(!StringUtils.isEmpty(params.get("max")) && new BigDecimal((String) params.get("max")).compareTo(new BigDecimal("0")) == 1, SkuInfoEntity::getPrice, params.get("max"));

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                lambdaQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkuInfoBySpuId(Long spuId) {
        return this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
    }

    /**
     * 商品详情页数据
     * @param skuId
     * @Author JnYou
     */
    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {

        SkuItemVo skuItemVo = new SkuItemVo();

        // 使用线程池开启异步编排任务执行
        CompletableFuture<SkuInfoEntity> infofuture = CompletableFuture.supplyAsync(() -> {
            // 1.sku基本信息获取pms_sku_info
            SkuInfoEntity skuBaswInfo = getById(skuId);
            skuItemVo.setInfo(skuBaswInfo);
            return skuBaswInfo;
        }, executor);

        CompletableFuture<Void> saleAttrFuture = infofuture.thenAcceptAsync((res) -> {
            //3.获取spu的销售属性组合
            List<SkuItemSaleAttrVo> skuItemSaleAttrVos = skuSaleAttrValueService.getSaleAttrBySpuId(res.getSpuId());
            skuItemVo.setSaleAttr(skuItemSaleAttrVos);
        }, executor);

        CompletableFuture<Void> descFuture = infofuture.thenAcceptAsync((res) -> {
            //4.获取spu的介绍
            SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDescp(spuInfoDesc);
        }, executor);

        CompletableFuture<Void> baseInfoFuture = infofuture.thenAcceptAsync((res) -> {
            //5.获取spu的规格参数信息
            List<SpuItemAttrGroupVo> spuItemAttrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(),res.getCatalogId());
            skuItemVo.setGroupAttrs(spuItemAttrGroupVos);
        }, executor);

        CompletableFuture<Void> imagesFuture = CompletableFuture.runAsync(() -> {
            // 2.sku的图片信息 pms_sku_imgs
            List<SkuImagesEntity> skuImagesEntities = skuImagesService.getImmageBySkuId(skuId);
            skuItemVo.setImages(skuImagesEntities);
        }, executor);

        // 等待所有异步任务执行完成后返回result；阻塞式操作
        CompletableFuture.allOf(saleAttrFuture,descFuture,baseInfoFuture,imagesFuture).get();

        return skuItemVo;
    }

}