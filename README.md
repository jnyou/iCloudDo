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


### SpringCache
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

### SpringCloud alibaba openFeign
feign调用流程：

* 1、构造请求数据，将对象转为json

       RequestTemplate template = this.buildTemplateFromArgs.create(argv);
* 2、发送请求进行执行（执行成功会解码响应数据）

        return this.executeAndDecode(template, options);
* 3、执行请求会有重试机制
```
while(true) {
         try {
             return this.executeAndDecode(template, options);
         } catch (RetryableException var9) {
             RetryableException e = var9;

             try {
                  // 重试机制
                 retryer.continueOrPropagate(e);
             } catch (RetryableException var8) {
                 Throwable cause = var8.getCause();
                 if (this.propagationPolicy == ExceptionPropagationPolicy.UNWRAP && cause != null) {
                     throw cause;
                 }

                 throw var8;
             }
         }
     }
```
           
         
### Redisson                 
看门狗：LockWatchdogTimeout原理：
     
```

     public String hello(){
     // 1、获取一把可重入锁（可避免死锁的锁），只要锁的名字一样。就是同一把锁
     RLock lock = redisson.getLock("my-lock");     
     // 加锁
     // lock.lock(); // 阻塞式等待，相当于自旋方式. 默认加的锁为30s，Redisson内部提供了一个监控锁的看门狗，有自动蓄积的锁时长，执行完成后30s就释放锁
     // 看门狗（LockWatchdogTimeout） 实现原理：
     // 如果指定了锁的超时时间，就发送给Redis执行lua脚本执行，进行占锁，默认超时就是我们指定的时间
     // 没有指定时间。就使用30 * 1000 【LockWatchdogTimeout看门狗的默认时间30000L】，只要占锁成功，就会启动一个定时任务，重新给锁设置过期时间，新的过期时间就是看门狗的默认时间
     // 啥时候蓄积？this.internalLockLeaseTime / 3L 。 当前看门狗默认时长/3 。 也就是10s蓄积一次，蓄积到满时间，也就是20s的时候就会蓄积一次到30s
     // 最佳实践
     lock.lock(30, TimeUnit.SECONDS);
     try {
         System.out.println("加锁成功，执行业务。。。" + Thread.currentThread().getId());
         Thread.sleep(30000);
     }catch (Exception e) {

     } finally {
         // 解锁
         System.out.println("释放锁" + Thread.currentThread().getId());
         lock.unlock();
     }
     return "hello";
     } 
 
```
         
### OAuth2.0 认证开放标准
对于用户相关的openAPI(例如获取用户的信息，动态同步，照片，日志，分享)

### 分布式SpringSession  [文档地址](https://docs.spring.io/spring-session/docs/2.3.1.RELEASE/reference/html5/guides/boot-redis.html#boot-sample)
解决分布式session下的不同服务，不同域名，子域session共享问题
- 将session存放在Redis中统一存储
- 发session id 的时候指定为父级域名解决子域session共享问题

核心原理：@EnableRedisHttpSession注解

@EnableRedisHttpSession注解导入了RedisHttpSessionConfiguration配置类
- 给容器中添加了一个组件RedisISessionRepositoryFilterndexedSessionRepository：Redis保存session，session的增删改查封装类
- SessionRepositoryFilter：session 存储过滤器。实际就是实现了原生http的Filter接口
- 1、创建的时候，就自动从容器中获取到sessionRepository。
- 2、通过传过来的原生request，response都被包装成SessionRepositoryRequestWrapper,SessionRepositoryResponseWrapper
- 3、以后获取session。request.getSession();
- 4、wrapperRequest.getSession(); ==> SessionRepority中获取到

`使用的是装饰者模式`

### SSO单点登录流程 参考xxl-sso
- 1、client1登录之后向认证服务器存入一个cookie，并带上token重定向到自己（client1）的页面，创建自己的会话
- 2、client2没登录，跳转到登录页面，但是会带上认证服务器的那个cookie，带上token重定向到自己的页面，创建自己的会话

