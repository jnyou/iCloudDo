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

---
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

---      
### OAuth2.0 认证开放标准
对于用户相关的openAPI(例如获取用户的信息，动态同步，照片，日志，分享)

---
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

---
### SSO单点登录流程 参考xxl-sso
- 1、client1登录之后向认证服务器存入一个cookie，并带上token重定向到自己（client1）的页面，创建自己的会话
- 2、client2没登录，跳转到登录页面，但是会带上认证服务器的那个cookie，带上token重定向到自己的页面，创建自己的会话

---
### ThreadLocal -- 同一个线程共享数据
从拦截器的执行 -- controller --  service -- dao 一直到请求结束给浏览器响应从始至终都是同一个线程，比如上一个人放的数据需要在下一个人使用
其核心原理是一个Map<Thread,Object> key是当前线程，值是当前线程共享的数据，所以每个线程互不干扰

---
### QUEUE base
类型：
- 先进先出（队头进，队尾出）
- 先进后出（队头进，队头出）`是典型的栈结构`
- 双端队列（队头和队尾都能进和出）

---
### RabbitMQ
简介：由erlang开发的AMQO的开源实现

概念：当生产者发送消息以后，由消息代理接管，消息代理保证消息传递到指定的目的地。
- 消息代理（message broker）：相当于中间的消息服务器
- 目的地（destination）：主要有两种形式：
  - 队列（QUEUE）：点对点消息通信（point-to-point）
  - 主题（topic）：订阅（subscribe）/ 发布（publish） 消息通信

1、点对点式：
  - 消息发送者发送消息，消息代理将其放入一个队列汇中，消息接受者从队列中获取消息内容，消息读取后被移除队列
  - 消息只有唯一的发送者和接受者，但并不是说只能有一个接受者 
2、发布订阅式：
  - 发送者发送消息主题，多个接受者监听这个主题，那么就会在消息到达时同时收到消息。
3、JMS（Java Message Service）Java消息服务：
  - 基于JVM消息代理的规范。ActiveMQ、HornetMq是JMS实现
4、AMQP（Advanced Message Queuing Protocol）
  - 高级消息队列协议，也是一个消息代理的规范，兼容JMS
  - RabbitMQ是AMQP的实现

核心概念：
- Message：消息是不具名的，它由消息头和消息体组成。消息体是不透明的，而消息头则由一系列的可选属性组成，这些属性包括routing-key（路由链）、priority（相对于其他消息的优先权）、delivery-mode（指出该消息可能西药持久性存储）等。
- Publisher：消息生成者。也是一个向交换器发布消息的客户端应用程序。
- Exchange：交换器。用来接收生产者发送的消息并将这些消息路由给服务器中的队列。Exchange有4中类型：direct（默认）、fanout、topic和headers，不同类型的Exchange转发消息的策略有所区别。
- Queue：消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息可投入一个或者多个队列。消息一直在队列里面，等待消费者连接到这个队列将其取走。
- Binding：绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。
- Connection：网络连接，比如一个TCP连接。
- Channnel：信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真实的TCP连接内的虚拟连接，AMQP命令都是通过信道发出去的，不管是发布消息，订阅队列还是接受消息，这些动作都是通通过信道完成。因为对于操作系统来说建立和销毁TCP都是非常昂贵的开销，所以引入信道的概念，以复用一条TCP连接。
- Consumer：消息的消费者，标识第一个从消息队列中取到消息的客户端应用程序。
- Virtual Host：虚拟主机，表示一批交换器、消息队列和相关对象。虚拟主机是共享相同的身份认证和加密环境的独立服务器域。每个vhost本质上就是一个mini版的RabbitMQ服务器，拥有自己的队列、交换器、绑定和权限机制。vhost是AMQP概念的基础，必须连接时指定，RabbitMQ默认的vhost是/。
- Broker：表示消息队列服务器实体。

Tip：
- 在集群模式下，同一个消息，只能有一个客户端收到。
- 只有当前消息业务逻辑处理完，才会接受下一个消息进行业务处理。

