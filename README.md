# iCloudDo
☀小剑Javaの学习☁整理笔记❤ Backstage-end-learning-to-organize-notes

###springcloud alibaba 系列：
- springcloud nacos：注册中心（服务注册/发现），配置中心（动态配置管理）
- springcloud ribbon：负载均衡
- 原fescar --> seata  （分布式事务解决方案）
- 原Hystrix --> sentinel（服务容错，限流，降级，熔断）
- springcloud alibaba gateway：API网关（webflux编程模式）
- springcloud sleuth：调用链监控
- springcloud alibaba openFeign：远程调用


### spring cache
- @Cacheable：触发将数据保存到缓存中的操作
   - 默认行为
   -  1）、如果缓存中有，方法不用调用
   -  2）、key默认自动生成，格式：缓存名字::SimpleKey []（自动生成的key）
   -  3）、缓存的value值，默认使用的是jdk的序列化机制，将序列化后的值存在redis中
   -  4）、默认时间ttl=-1
     自定义属性：
         - 1）、指定生成的缓存使用的key：key属性指定，使用spel表达式
             `SPEL表达式：https://docs.spring.io/spring/docs/5.2.7.RELEASE/spring-framework-reference/integration.html#cache-spel-context`
         - 2）、指定缓存的数据的存活时间：配置文件中修改ttl，spring.cache.redis.time-to-live=3600000
         - 3）、将数据保存为json格式（异构系统比如php可能不兼容）
- @CacheEvict：触发将数据从缓存删除操作
- @CachePut:不影响方法执行更新操作
- @Caching:组合以上多个操作
- @CacheConfig:在类级别共享缓存的相同数据
      `