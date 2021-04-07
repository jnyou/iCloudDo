# iCloudDo
☀小剑Javaの学习☁整理笔记❤ Backstage-end-learning-to-organize-notes

###springcloud alibaba 系列：
- nacos：注册中心（服务注册/发现），配置中心（动态配置管理）
- 原fescar --> seata  （分布式事务解决方案）
- 原Hystrix --> sentinel（熔断，限流，降级）
- OSS （对象存储）

###springcloud 系列：
- openFeign：远程调用
- ribbon：负载均衡
- gateway：API网关（reactive&webflux响应式编程）
- sleuth + zipkin：调用链监控

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
使用springsession技术
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
简介：由erlang开发的AMQP的开源实现

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

延时队列 场景：下订单30分钟后进行查询一次，看哪些没有支付，需要关闭订单、锁库存-解锁库存等等
- 消息的TTL（time to live）消息的存活时间。比如如果设置了30分钟这个消息没人消费，则称为死信路由
- DLX：（dead letter exchange）以下都是死信，都丢进死信交换器，然后家交换器放入一个指定队列中，直接监听到这个队列就是需要处理的消息
  - 一个消息被消费者拒收
  - TTL过期时间到了
  - 队列的长度限制满了

实战：延时队列关单：测试类： order-service服务的 TestController

消息丢失？
- 消息发送出去，由于网络等一系列的原因，没有抵达服务器
  - 做好容错方法（try-catch）,出现异常了采用重试机制，可记录到数据库，采用定期扫描重发的方式
  - 做好日志记录，每个消息状态是否都被服务器收到都应该记录
  - 做好定期重发，如果消息没有发送成功，定期去数据库扫描未成功的消息进行重发
- 消息抵达Broker，Broker要将消息持久化才算成功
- 自动ack
- 总结：
  - 1、做好消息的确认机制（publisher , consumer【手动ack机制】）  
  - 2、每个发送的消息都在数据库做好记录。定期将失败的消息再次发送一遍

消息重复？
- 宕机、网络等原因没有ack成功，导致broker将消息状态变为了ready状态而重复消费

解决方案
- 消费者业务接口应该设置为幂等性的。比如扣库存有工作单的状态标志
- 使用防重表 ，发送消息都有一个业务的唯一标识，处理过就不用处理
- rabbitMQ的每一个消息都有redelivered字段，可以判断是否是被重新投递过来的，而不是第一次投递过来的（Boolean redelivered = message.getMessageProperties().getRedelivered();
）

消息积压？
- 消费者宕机积压
- 消费者消费能力不足
- 发送者发送流量太大

解决方案：
- 上线更多的消费者，进行正常的消费（比如多弄几个库存服务进行消费）
- 上线专门的队列消息服务，将消息先批量取出来，记录数据库，离线慢慢处理

### 接口防刷
1、OAuth2Controller
2、幂等性处理

### 接口幂等性处理
简单说：一个订单的同一操作提交一次和提交100次，数据库只保存了一次。

概念：接口幂等性就是用户对于同一操作发起的一次请求或者多次请求的结果是一致的。比如用户下单，网速缓慢点击了多次会不会导致多次扣款

哪些情况需要防止：
- 用户多次点击
- 用户页面回退再次提交
- 微服务互相调用，由于网络问题，导致请求失败。feign触发重试机制
- 其他业务场景

数据库的操作：查询、更新、删除、主键插入是天然幂等。

不是主键插入、更新时进行计算操作等都不是幂等的

解决方案：
- token令牌机制（常用）（比如秒杀时派发一个令牌，只有秒杀开始的时候才会返回这个令牌，可以将令牌保存在redis中，然后处理秒杀业务时候可以进行校验）
- 各种锁机制
  - 数据库悲观锁 查询操作
  - 数据库乐观锁 更新操作
  - 业务层Redisson分布式锁
- 各种唯一约束机制
  - 数据库唯一约束（比如订单号是唯一主键）
  - redis set 防重
- 防重表
- 全局请求唯一id

### 本地事务失效问题
同一个对象内事务方法互调默认失效，原因是绕过了代理对象，事务是使用代理对象来控制的

解决：使用代理对象来调用事务方法
- 1、引入aop的starter，内部引用了aspectj
- 2、@EnableAspectJAutoProxy(exposeProxy = true)，开启aspectj动态代理功能。以后所有的动态代理都是aspectj动态构建的（即使没有接口也可以创建动态代理）
exposeProxy：对外暴露代理对象
- 3、暴露互调使用代理对象调用
```$xslt
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {    
    @Transactional(timeout = 30)
    public void c(){
        /** 失效 **/
        a(); 
        b();
        /** 解决方案 **/
        OrderServiceImpl orderService = (OrderServiceImpl) AopContext.currentProxy();
        orderService.b();
        orderService.c();
    }
    @Transactional(propagation = Propagation.REQUIRED,timeout = 2)
    public void a(){}
    @Transactional(propagation = Propagation.REQUIRES_NEW,timeout = 30)
    public void b(){}
}
```
## 分布式事务 seata：（通常用于后台管理系统，对于并发不是很高的业务场景中使用，比如保存订单的时候，还要远程调用保存库存、优惠等信息）
### CAP定理
- 一致性 C
- 可用性 A
- 分区容错性 P ：无法避免
CAP定理告诉我们剩下的A和C无法同时做到 所以只有 AP 或者 CP

分布式系统中实现一致性的[raft](http://thesecretlivesofdata.com/raft) 算法 & paxos算法
raft：有三种状态，follow、Candidate、leader；领导选举，通过领导给随从进行日志复制，保证分布式一致性.（保证了CP）
选举过程：随从节点向候选人投票，随从自旋时间一般为150-300毫秒,看哪个随从几点快优先成为候选人.
日志复制：在发生改变后的下一次心跳时间复制日志给其他的随从节点（其他系统 ），随从节点收到后响应给领导节点.

需要保证服务的可用：舍弃CP强一致性定理，使用弱一致性;BASE定理

### BASE定理
保证系统的基本可用性，软状态，最终一致性

- 强一致性：本地事务
- 弱一致性：允许一定时间内，不同节点的数据不一致，但要求最终一致性
- 最终一致性：

### 分布式事务几种方案
- 1、2PC模式：两阶段提交 又叫XA Transactions （性能不理想）
- 1.1、3PC模式：三阶提交模式：将二阶的预备分为两阶段，第一：能否提交？ 第二：准备提交的数据.
XA 是一个两阶段提交协议，分为以下两个阶段

第一阶段：（预备）事务协调器要求每个涉及到事务的数据库预提交此操作，并反映是否可以提交.

第二阶段：事务协调器要求每个数据提交数据

- 2、柔性事务-TCC（事务补偿方案）：遵循BASE理论，最终一致性

try \ confirm \ cancel ， 通过cancel来进行事务的rollback机制进行事务补偿（比如之前数量+2，出现问题程序员编写好程序逻辑进行-2）
- 3、柔性事务-最大努力通知型方案：发送MQ消息进行最大努力通知（支付宝异步通知，每隔4m,10m,10m,1h,2h,6h,15h进行通知，直到返回给支付宝success，就不会通知了）
- 4、柔性事务-可靠消息+最终一致性方案（异步确保型）（延迟队列关单，延迟队列解库存）常用于高并发场景

### 秒杀（高并发）系统设计原则：
- 服务单一职责 + 独立部署 （seckillservice服务，且独立部署）
- 秒杀链接加密（接口返回添加随机码）
- 库存预热 + 快速扣减 （定时任务上架秒杀商品在redis中，使用分布式信号量控制进来秒杀的请求）
- 动静分离 （nginx动静分离）
- 恶意请求拦截 （登录拦截器，也可以使用网关层处理）
- 流量错峰（使用一定手段，比如验证码，加入购物车，将流量错峰开来）
- 限流 & 熔断 & 降级 
- 队列削峰（使用消息队列通知订单系统，让订单服务慢慢消费处理创建订单）

### [Sentinel](https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D)
远程服务被降级，就会触发熔断回调方法（降级数据）

### [Sleuth + zipkin] 
Sleuth基本术语：
- span（跨度）
- trace（跟踪）
- annotation（标注） 
  - cs - Client Sent - 客户端发送一个请求，这个注解描述了这个Span的开始。
  - sr - Server Received - 服务端获得请求并准备开始处理它，其中（sr – cs） 时间戳便可得到网络传输的时间。
  - ss - Server Sent （服务端发送响应）– 该注解表明请求处理的完成(当请求返回客户端)， （ss – sr）时间戳就可以得到服务器请求的时间。
  - cr - Client Received （客户端接收响应）- 表明此时Span的结束，（cr – cs）时间戳便可以得到整个请求所消耗的时间。

Sleuth 与 zipkin 的关系
- Sleuth用来追踪操作，zipkin是一个可视化界面工具

### TODO 缓存 和 分布式锁

### Redisson                 
看门狗LockWatchdogTimeout原理：
     
```

     /**
      * redisson的强大之处：（内部有一个看门狗（LockWatchdogTimeout）机制）
      *  1、锁的自动续期：如果业务处理时间超长，运行期间自动给锁续上新的30s（默认时间）。不用担心业务时间长，锁自动过期被删掉的问题。前提是自动解锁时间一定要大于业务执行时间
      *  2、解决死锁问题：加锁的业务只要运行完成，就不会给当前的锁续期，即使不手动解锁，锁默认在30s以后自动删除。
      *
      * 看门狗（LockWatchdogTimeout）实现原理：
      *      如果指定了锁的超时时间，就发送给Redis执行lua脚本执行，进行占锁，默认超时就是我们指定的时间；不会执行看门狗机制。
      *      未指定锁的超时时间。就使用30 * 1000 【LockWatchdogTimeout看门狗的默认时间30s】，只要占锁成功，就会启动一个定时任务，重新给锁设置过期时间，新的过期时间就是看门狗的默认时间
      *      啥时候蓄积？this.internalLockLeaseTime / 3L 。 当前看门狗默认时长/3 。 也就是10s蓄积一次，蓄积到满时间，也就是20s的时候就会蓄积一次到30s

      *    总结：推荐使用lock.lock(30, TimeUnit.SECONDS);这种方式，跳过看门狗机制，过期时间设置稍微大点，如果这个时间还是有异常，那就是程序有问题了。
      */
     @ResponseBody
     @GetMapping("/hello")
     public String hello(){
         // 1、获取一把可重入锁（可避免死锁的锁），只要锁的名字一样。就是同一把锁
         RLock lock = redisson.getLock("my-lock");
 
         //  lock.lock(10, TimeUnit.SECONDS); 会报错抛出异常，在锁到期之后，不会自动续期，因为自动解锁时间一定要大于业务执行时间
         // 加锁
         // lock.lock(); // 阻塞式等待，相当于自旋方式. 默认加的锁为30s，Redisson内部提供了一个监控锁的看门狗，有自动蓄积的锁时长，执行完成后30s就释放锁
         // 看门狗（LockWatchdogTimeout） 实现原理：
         // 如果指定了锁的超时时间，就发送给Redis执行lua脚本执行，进行占锁，默认超时就是我们指定的时间；不会执行看门狗机制。
         // 未指定锁的超时时间。就使用30 * 1000 【LockWatchdogTimeout看门狗的默认时间30s】，只要占锁成功，就会启动一个定时任务，重新给锁设置过期时间，新的过期时间就是看门狗的默认时间
         // 啥时候蓄积？this.internalLockLeaseTime / 3L 。 当前看门狗默认时长/3 。 也就是10s蓄积一次，蓄积到满时间，也就是20s的时候就会蓄积一次到30s

         // 最佳实践
         // 推荐使用lock.lock(30, TimeUnit.SECONDS);这种方式，跳过看门狗机制，过期时间设置稍微大点，如果这个时间还是有异常，那就是程序有问题了。
         lock.lock(30, TimeUnit.SECONDS);
         try {
             System.out.println("加锁成功，执行业务。。。" + Thread.currentThread().getId());
             Thread.sleep(30000); // 30s业务时间
         }catch (Exception e) {
 
         } finally {
             // 解锁
             System.out.println("释放锁" + Thread.currentThread().getId());
             lock.unlock();
         }
         return "hello";
     }
 
```   


### TODO 异步&线程池&异步编排
