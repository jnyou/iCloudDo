package org.jnyou.gmall.productservice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jnyou.gmall.productservice.entity.BrandEntity;
import org.jnyou.gmall.productservice.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {

//        brandService.save(new BrandEntity().setName("华为").setLogo("http:baidu.com").setDescript("国际品牌").setShowStatus(0));

        LambdaQueryWrapper<BrandEntity> select = Wrappers.<BrandEntity>lambdaQuery().select(BrandEntity::getName, BrandEntity::getLogo);
        System.out.println(select.getSqlSelect());

    }

}
