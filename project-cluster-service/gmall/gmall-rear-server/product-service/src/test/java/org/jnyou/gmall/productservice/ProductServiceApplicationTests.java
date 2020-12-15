package org.jnyou.gmall.productservice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.gmall.productservice.entity.BrandEntity;
import org.jnyou.gmall.productservice.service.BrandService;
import org.jnyou.gmall.productservice.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@Slf4j
class ProductServiceApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void contextLoads() {

//        brandService.save(new BrandEntity().setName("华为").setLogo("http:baidu.com").setDescript("国际品牌").setShowStatus(0));

        LambdaQueryWrapper<BrandEntity> select = Wrappers.<BrandEntity>lambdaQuery().select(BrandEntity::getName, BrandEntity::getLogo);
        System.out.println(select.getSqlSelect());

    }

    @Test
    void findParentsPath(){
        Long[] catIdPath = categoryService.findCatIdPath(225L);
        log.info("完整回显路径：{}" + Arrays.asList(catIdPath));
    }


    /**
     * redis test
     * @Author JnYou
     */
    @Test
    void testStringRedisTemplate(){
        stringRedisTemplate.opsForValue().set("hello","world" + UUID.randomUUID().toString());
        stringRedisTemplate.opsForValue().get("hello");
    }

    /**
     * redisson test
     * @Author JnYou
     */
    @Test
    void testRedissonClient(){
        System.out.println(redissonClient);
    }

}
