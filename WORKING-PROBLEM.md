项目中遇到的问题：
####Q1、feign远程调用丢失头信息（丢失cookie所带的所有信息等）
```$xslt
S1：在调用feign 之前先进行RequestInterceptor.apply()方法，通过RequestContextHolder来获取老请求的所有头信息，将老请求的头信息同步到新的请求当中。
```

####Q2、feign异步模式请求丢失头信息，子线程与主线程所用的cookie不一样
```$xslt
S2：获取老请求：RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

通过RequestContextHolder.setRequestAttributes(requestAttributes);每一个线程都来共享原来的请求信息
```

#### Q3本地事务失效问题(同一个对象内事务方法互调默认失效)
```$xslt
S3：原因是绕过了代理对象，事务是使用代理对象来控制的

解决：使用代理对象来调用事务方法
- 1、引入aop的starter，内部引用了aspectj
- 2、@EnableAspectJAutoProxy(exposeProxy = true)，开启aspectj动态代理功能。以后所有的动态代理都是aspectj动态构建的（即使没有接口也可以创建动态代理）
exposeProxy：对外暴露代理对象
- 3、暴露互调使用代理对象调用
- 4、通过AopContext上下文来获取当前的代理对象

CODE:

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