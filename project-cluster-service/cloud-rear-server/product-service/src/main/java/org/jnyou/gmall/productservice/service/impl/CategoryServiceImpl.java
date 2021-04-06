package org.jnyou.gmall.productservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.gmall.productservice.dao.CategoryDao;
import org.jnyou.gmall.productservice.entity.CategoryEntity;
import org.jnyou.gmall.productservice.service.CategoryBrandRelationService;
import org.jnyou.gmall.productservice.service.CategoryService;
import org.jnyou.gmall.productservice.vo.web.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author jnyou
 */
@Service("categoryService")
@Slf4j
@SuppressWarnings(value = "unchecked")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> all = baseMapper.selectList(null);

        // 找到一级分类
        List<CategoryEntity> level1List = all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((categoryEntity -> {
                    categoryEntity.setChildren(getChildrens(categoryEntity, all));
                    return categoryEntity;
                }))
                .sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())).collect(Collectors.toList());
        // 找到一级分类下的子分类
        return level1List;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        // TODO 检查当前删除的菜单，是否被别的地方引用

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatIdPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> resultPath = findParentsPath(catelogId, paths);
        // 将结果的数组倒叙
        Collections.reverse(resultPath);
        return resultPath.toArray(new Long[resultPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     * @return
     * @CacheEvict:失效模式，allEntries:删除某个分区下的所有数据
     * @Caching：同时进行多种缓存操作
     */
//    @CacheEvict(value = {"category"},key = "'getLevel1Category'")
//    @Caching(evict = {
//            @CacheEvict(value = {"category"},key = "'getLevel1Category'"),
//            @CacheEvict(value = {"category"},key = "'getCatelogJson'")
//    })
    @CacheEvict(value = {"category"}, allEntries = true)
//    @CachePut // 双写模式，修改了在添加一份新数据到缓存中
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            // 维护关联冗余数据一致性
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    /**
     * 缓存的分区（推荐按照业务类型分）
     * 因为spel动态取值，所有需要额外加''表示字符串
     * SPEL表达式参考：https://docs.spring.io/spring/docs/5.2.7.RELEASE/spring-framework-reference/integration.html#cache-spel-context
     *
     */
    @Override
    @Cacheable(cacheNames = {"category"}, key = "#root.methodName")
    public List<CategoryEntity> getLevel1Category() {
        System.out.println("查询数据库");
        return this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    /**
     * 常规数据，读多写少，即时性，一致性要求不高的额数据，使用spring-cache。sync：读模式加本地锁，不需要使用分布式这个大锁；
     * 特殊数据（实时性要求高的）：1、可以使用canal，感知到mysql的更新去更新缓存。 2、读写加锁 3、排队公平锁
     *
     * spring-cache解决redis引发的问题的解决方案：
     *      缓存穿透：缓存空对象（cache-null-values: true）
     *      缓存雪崩：加过期时间（time-to-live: 3600000）
     *      缓存击穿：加本地锁（sync = true）
     */
    @Cacheable(cacheNames = "category", key = "#root.methodName", sync = true)
    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJson() {
        System.out.println("查询了数据库。。。");
        // 将数据库的查询多次变为一次
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        // 查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        // key : 每个一级分类的ID，value : List<Catelog2Vo>
        Map<String, List<Catelog2Vo>> listMap = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 根据一级分类查询二级分类集合
            List<Catelog2Vo> catelog2Vos = new ArrayList<>();
            List<CategoryEntity> category2EntityList = getParent_cid(selectList, v.getCatId());

            List<Catelog2Vo> catelog2VoList = null;
            if (!CollectionUtils.isEmpty(category2EntityList)) {
                // 封装好需要返回的数据 List<Catelog2Vo>
                catelog2VoList = category2EntityList.stream().map(c2 -> {
                    // 查找当前二级分类下的三级分类
                    List<CategoryEntity> category3Entities = getParent_cid(selectList, c2.getCatId());
                    List<Catelog2Vo.Catelog3Vo> catelog3Vos = null;
                    if (!CollectionUtils.isEmpty(category3Entities)) {
                        // 封装三级分类VO
                        catelog3Vos = category3Entities.stream().map(c3 -> {
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo().setCatalog2Id(c2.getCatId().toString()).setId(c3.getCatId().toString()).setName(c3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                    }
                    Catelog2Vo catelog2Vo = new Catelog2Vo().setCatalog1Id(v.getCatId().toString()).setId(c2.getCatId().toString()).setName(c2.getName()).setCatalog3List(catelog3Vos);
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2VoList;
        }));
        return listMap;
    }

    //    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJson1() {

        /**
         * 解决缓存相关问题：
         * 1、缓存空对象：解决缓存穿透
         * 2、设置过期时间（加随机值）：解决缓存雪崩
         * 3、加锁：解决缓存击穿
         */
        // 加入Redis缓存逻辑
        String catelogJson = redisTemplate.opsForValue().get("catelogJson");
        if (StringUtils.isEmpty(catelogJson)) {
            System.out.println("缓存不命中，准备查询数据库。。。");
            // 缓存中没有,查询数据库
            Map<String, List<Catelog2Vo>> catelogJsonFromDb = getCatelogJsonFromDbWithRedissonLock();
            return catelogJsonFromDb;
        }
        System.out.println("缓存命中。。。");
        // 给缓存中放json,拿出的json字符串，还要逆转为需要的对象类型【也就是序列化与反序列化】,返回一个复杂类型使用TypeReference来引用
        return JSON.parseObject(catelogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });
    }

    /**
     * redisson分布式锁的实现原理：
     * Redis分布式锁测试：官方文档：http://www.redis.cn/topics/distlock.html
     *
     * 总结：分布式锁两个核心原理： 最基本的原理是set NX EX原理（命令：set lock uuid EX 300 NX），（redis中不存某个key时加锁） 对应java中的redisTemplate.opsForValue().setIfAbsent()方法
     * 1、原子加锁，使用 set(key,value,NX,EX,timeout)：SET resource_name my_random_value NX EX 30000。
     * 2、原子解锁，使用Lua脚本原子解锁
     *
     * @return java.util.Map<java.lang.String, java.util.List < org.jnyou.gmall.productservice.vo.web.Catelog2Vo>>
     */
    public Map<String, List<Catelog2Vo>> getCatelogJsonFromDbWithRedisLock() {

        // 抢占分布式锁，去Redis中占坑
        // setIfAbsent：相当于Redis中的SETNX EX
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (lock) {
            System.out.println("获取分布式锁成功。。");
            // 占锁成功，执行数据库操作
            // 1、设置30s过期时间，防止在还没有执行删除锁代码机器出现异常情况导致死锁的情况,且需要与设置锁的的时候为原子操作，即写在上方
//            redisTemplate.expire("lock",30,TimeUnit.SECONDS);
            Map<String, List<Catelog2Vo>> dataFromDb;
            try {
                dataFromDb = getDataFromDb();
            } finally {
                // 删除锁，给其他进程占用锁,但是只能删除自己的锁：且：获取值对比 + 对比成功删除操作 = 原子操作（使用lua脚本解锁：Redis官方文档：http://www.redis.cn/topics/distlock.html）
                String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                        "    return redis.call(\"del\",KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";
                // 执行lua脚本原子解锁
                redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if(lockValue.equals(uuid)){
//                // 删除自己的锁
//                redisTemplate.delete("lock");
//            }
            }
            return dataFromDb;
        } else {
            // 占锁失败，采用自旋的方式重试（自旋锁），相当于synchronized()继续占锁
            System.out.println("获取分布式锁失败。。。");
            // 休眠200ms重试
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }
            return getCatelogJsonFromDbWithRedisLock();
        }
    }


    /**
     * 使用Redisson分布式改造上面代码
     * 产生一个问题：如果更改了三级分类数据，缓存里面的数据如何和数据库保持一致（缓存数据一致性）
     * 常用模式：
     * 1、双写模式：更新数据库时，更新缓存。并发条件下会产生脏数据，解决方案：加锁
     * 2、失效模式：更改了就删除缓存中的数据，下次查询在放入最新的数据。
     * 最终gmall系统采取：失效模式，缓存的数据都有过期啥时间，主动更新。读写数据的时候，加上分布式读写锁。
     *
     * @Author JnYou
     */
    public Map<String, List<Catelog2Vo>> getCatelogJsonFromDbWithRedissonLock() {

        // 占锁，锁的名字就是锁的粒度，力度越细越快。
        // 锁的粒度规则：具体缓存的是某个数据，例：11号商品：product-11-lock、product-12-lock；防止等待问题
        RLock lock = redisson.getLock("catelogJson-lock");
        // 加锁
        lock.lock();
        // 占锁成功，执行数据库操作
        Map<String, List<Catelog2Vo>> dataFromDb;
        try {
            dataFromDb = getDataFromDb();
        } finally {
            // 解锁
            lock.unlock();
        }
        return dataFromDb;
    }

    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        // 获得锁之后再去查询一次，没有则继续查询数据库
        String catelogJson = redisTemplate.opsForValue().get("catelogJson-lock");
        if (!StringUtils.isEmpty(catelogJson)) {
            return JSON.parseObject(catelogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
        }
        System.out.println("查询了数据库。。。");
        // 将数据库的查询多次变为一次
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        // 查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        // key : 每个一级分类的ID，value : List<Catelog2Vo>
        Map<String, List<Catelog2Vo>> listMap = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 根据一级分类查询二级分类集合
            List<Catelog2Vo> catelog2Vos = new ArrayList<>();
            List<CategoryEntity> category2EntityList = getParent_cid(selectList, v.getCatId());

            List<Catelog2Vo> catelog2VoList = null;
            if (!CollectionUtils.isEmpty(category2EntityList)) {
                // 封装好需要返回的数据 List<Catelog2Vo>
                catelog2VoList = category2EntityList.stream().map(c2 -> {
                    // 查找当前二级分类下的三级分类
                    List<CategoryEntity> category3Entities = getParent_cid(selectList, c2.getCatId());
                    List<Catelog2Vo.Catelog3Vo> catelog3Vos = null;
                    if (!CollectionUtils.isEmpty(category3Entities)) {
                        // 封装三级分类VO
                        catelog3Vos = category3Entities.stream().map(c3 -> {
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo().setCatalog2Id(c2.getCatId().toString()).setId(c3.getCatId().toString()).setName(c3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                    }
                    Catelog2Vo catelog2Vo = new Catelog2Vo().setCatalog1Id(v.getCatId().toString()).setId(c2.getCatId().toString()).setName(c2.getName()).setCatalog3List(catelog3Vos);
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2VoList;
        }));
        // 将查询的数据放入缓存，所有存在缓存中的数据都转成json字符串形式，因为JSON跨语言。
        redisTemplate.opsForValue().set("catelogJson", JSON.toJSONString(listMap), 1, TimeUnit.DAYS);
        return listMap;
    }

    /**
     * 本地锁测试
     *
     * @Author JnYou
     */
    public Map<String, List<Catelog2Vo>> getCatelogJsonFromDbWithLocalLock() {

        // 本地锁：synchronized、JUC：Java.util.concurrent.（locks），在分布式情况下，想要锁住所有，必须使用分布式锁
        synchronized (this) {
            return getDataFromDb();
        }
    }











    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentId) {
        List<CategoryEntity> collect = selectList.stream().filter(item -> item.getParentCid().equals(parentId)).collect(Collectors.toList());
        return collect;
    }

    // 递归向上查询当前catelogId的父ID
    private List<Long> findParentsPath(Long catelogId, List<Long> paths) {
        CategoryEntity categoryEntity = this.baseMapper.selectById(catelogId);
        // 先加入当前ID
        paths.add(catelogId);
        if (categoryEntity.getParentCid() != 0) {
            // 还有父ID，将父ID传入方法继续查询
            findParentsPath(categoryEntity.getParentCid(), paths);
        }
        return paths;
    }

    // 递归查询分类树
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> child = all.stream()
                .filter(categoryEntity -> root.getCatId().equals(categoryEntity.getParentCid()))
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildrens(categoryEntity, all));
                    return categoryEntity;
                }).sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())).collect(Collectors.toList());
        return child;
    }

}