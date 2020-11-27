package com.blithe.cms.config.redis;

import com.blithe.cms.common.utils.RedisObjectSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: youjiannan
 * @Description: redis 自定义配置
 **/
@Configuration
//继承CachingConfigurerSupport，为了自定义生成KEY的策略。可以不继承。
public class RedisConfiguration extends CachingConfigurerSupport {

    /**
     * SpringBoot 2.X 缓存管理器配置
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 使用json方式序列化，在实体类中可以不进行序列化了
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //  解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        CacheKeyPrefix keyPrefix = new CacheKeyPrefix() {
            @Override
            public String compute(String cacheName) {
                return cacheName + "::";
            }
        };

        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置   配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 默认缓存时间
                .entryTtl(Duration.ofMinutes(0))
                // 设置key的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                // 设置value的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues(); // 不缓存null值

        // 对每个缓存空间应用不同的配置 自定义的缓存配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>(124);
        // my-redis-cache1过期时间为30分钟
        configMap.put("my-redis-cache1", config.entryTtl(Duration.ofHours(30L)));
        // my-redis-cache2过期时间120s
        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));

        // 使用自定义的缓存配置初始化一个cacheManager
        return RedisCacheManager.builder(redisConnectionFactory)
                // 默认缓存
                .cacheDefaults(config)
                // 特殊缓存
                .withInitialCacheConfigurations(configMap)
                .transactionAware() // 事务
                .build();

    }


    /**
     * {@link org.springframework.cache.annotation.Cacheable}第一个注解代表从缓存中查询指定的key,如果有,从缓存中取,不再执行方法.如果没有则执
     * 行方法,并且将方法的返回值和指定的key关联起来,放入到缓存中.<br/>
     * {@link org.springframework.cache.annotation.CacheEvict}从缓存中清除指定的key对应的数据.<br/>
     * {@link org.springframework.cache.annotation.CachePut}在新增或者更新的时候,进行使用;更新时,则会覆盖原先的数据<br/>
     * {@link com.adc.da.main.util.RedisService}是使用示例
     *
     * @see org.springframework.cache.annotation.CacheEvict
     * @see org.springframework.cache.annotation.Cacheable
     * @see org.springframework.cache.annotation.CachePut
     * @see com.adc.da.main.util.RedisService
     **/


    /**
     * 自定义key. 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key,即使@Cacheable中的value属性一样，key也会不一样。
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());  // 类名
                sb.append(method.getName()); // 方法名
                for (Object obj : objects) {
                    sb.append(obj.toString()); // 参数名
                }
                return sb.toString();
            }
        };
    }
    // 自定义key生成器  Java8 lambda写法
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator(){
//        return (o, method, params) ->{
//            StringBuilder sb = new StringBuilder();
//            sb.append(o.getClass().getName()); // 类目
//            sb.append(method.getName()); // 方法名
//            for(Object param: params){
//                sb.append(param.toString()); // 参数名
//            }
//            return sb.toString();
//        };
//    }


    /**
     * redisTemplate 自定义序列化
     *
     * @param redisConnectionFactory
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @Primary
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//      redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(new RedisObjectSerializer());
        // hash的key采用String的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//      redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(new RedisObjectSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * 问题一：为什么要自定义RedisTemplate?
     * 通过源码可以看出，SpringBoot自动帮我们在容器中生成了一个RedisTemplate和一个StringRedisTemplate。
     *
     * RedisTemplate的泛型是<Object,Object>，写代码不是很方便，一般我们的key是String类型，我们需要一个<String,Object>的泛型。
     * RedisTemplate没有设置数据存储在Redis时，Key和Value的序列化方式。（采用默认的JDK序列化方式）
     *
     *
     *
     * 问题二：针对StringRedisSerializer，Jackson2JsonRedisSerializer和JdkSerializationRedisSerializer进行测试?
     * StringRedisSerializer进行序列化后的值，在Java和Redis中保存的内容时一致的。
     * 用Jackson2JsonRedisSerializer序列化后，在Redis中保存的内容，比Java中多一对逗号。
     * 用JdkSerializationRedisSerializer序列化后，对于Key-Value结构来说，在Redis中不可读；对于Hash的Value来说，比Java的内容多了一些字符。
     *
     *
     *
     *
     * jedis客户端和lettuce客户端的区别?
     *
     *jedis采用的是直连redis server，在多线程之间公用一个jedis实例，是线程不安全的，想要避免线程不安全，可以使用连接池pool，这样每个线程单独使用一个jedis实例，但是线程过多时，带来的是redis server的负载较大。有点类似BIO模式。
     *
     * lettuce采用netty连接redis server，实例在多个线程间共享，不存在线程不安全的情况，这样可以减少线程数量。当然在特殊情况下，lettuce也可以使用多个实例，有点类似NIO模式。
     *
     *
     * 概念：
     *
     * 　　Jedis：是Redis的Java实现客户端，提供了比较全面的Redis命令的支持，
     *
     * 　　Redisson：实现了分布式和可扩展的Java数据结构。
     *
     * 　　Lettuce：高级Redis客户端，用于线程安全同步，异步和响应使用，支持集群，Sentinel，管道和编码器。
     *
     * 优点：
     *
     * 　　Jedis：比较全面的提供了Redis的操作特性
     *
     * 　　Redisson：促使使用者对Redis的关注分离，提供很多分布式相关操作服务，例如，分布式锁，分布式集合，可通过Redis支持延迟队列
     *
     * 　　Lettuce：主要在一些分布式缓存框架上使用比较多
     *
     * 可伸缩：
     *
     * Jedis：使用阻塞的I/O，且其方法调用都是同步的，程序流需要等到sockets处理完I/O才能执行，不支持异步。Jedis客户端实例不是线程安全的，所以需要通过连接池来使用Jedis。
     *
     * Redisson：基于Netty框架的事件驱动的通信层，其方法调用是异步的。Redisson的API是线程安全的，所以可以操作单个Redisson连接来完成各种操作
     *
     * Lettuce：基于Netty框架的事件驱动的通信层，其方法调用是异步的。Lettuce的API是线程安全的，所以可以操作单个Lettuce连接来完成各种操作
     *
     *
     *
     * 结论：
     *
     * 建议使用：Jedis + Redisson
     *
     *
     *
     *
     *
     *@Cacheable(value = {"emp"},key = "#id")
     *
     *
     * 注解式的Redis处理
     *  注解式的使用就是在方法上面加上Cacheable / CacheEvict / CachePut的注解
     *  注解支持使用EL表达式 这里就是支持使用相关的参数和属性来表示
     *  #root.targetClass 是类名
     *  #p0是第一个参数值
     * @Cacheable(value = "test", key = "#root.targetClass + '_' + #p0 + '_' + #p1")
     */
}
