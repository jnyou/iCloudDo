package org.jnyou.gmall.productservice.service.impl;

import org.jnyou.gmall.productservice.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.productservice.dao.BrandDao;
import org.jnyou.gmall.productservice.entity.BrandEntity;
import org.jnyou.gmall.productservice.service.BrandService;
import org.springframework.util.StringUtils;


/**
 * @author jnyou
 */
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<BrandEntity>();
        if(!StringUtils.isEmpty(key)){
            wrapper.eq("brand_id", key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updateDetail(BrandEntity brand) {
        // 由于电商数据过多，对表的设计有冗余的字段，此时需要在代码中进行维护冗余数据一致性
        this.updateById(brand);
        if(!StringUtils.isEmpty(brand.getName())){
            // 更新品牌与分类关联表中的品牌名称
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
        }
    }

}