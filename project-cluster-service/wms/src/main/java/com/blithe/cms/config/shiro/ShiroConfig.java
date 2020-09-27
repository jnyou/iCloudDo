package com.blithe.cms.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.blithe.cms.config.redis.RedisCompoent;
import com.blithe.cms.filter.KickoutSessionControlFilter;
import com.blithe.cms.filter.LoginFormAuthenticationFilter;
import com.blithe.cms.listener.IsMeSessionListener;
import com.blithe.cms.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisClusterManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.*;

/**
 * @ClassName ShiroConfig
 * @Description: shiro配置
 * @Author: 夏小颜
 * @Date: 13:29
 * @Version: 1.0
 **/
@Configuration
@EnableConfigurationProperties(value = ShiroProperties.class)
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Autowired
    private ShiroProperties shiroProperties;


    @Autowired
    private RedisCompoent redisCompoent;

    @Value("${redis.host-port}")
    private String host;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.timeout}")
    private int timeout;
//    @Value("${spring.redis.password}")
//    private String password;

    public static final String CACHE_KEY = "shiro:cache:";
    public static final String SESSION_KEY = "shiro:session:";
    private static final String NAME = "custom.name";
    private static final String VALUE = "/";


    /**
     * 声明凭证匹配器
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(shiroProperties.getHashAlgorithmName());
        credentialsMatcher.setHashIterations(shiroProperties.getHashIterations());
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    /**
     * 声明userRealm，如果还有其他的realm，则再次注入一个
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        // 注入凭证匹配器
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        logger.info("current realm is userRealm!");
        return userRealm;
    }

    /**
     * 配置SecurityManager
     *
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier(value = "userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 设置自定义realm.
        securityManager.setRealm(userRealm);
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManagers());
        // 配置自定义session管理，使用redis
        securityManager.setSessionManager(sessionManager());
        // 使用记住我
        /**
         * TODO 记住我功能
         */
//        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }


    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {
        // web环境非HttpSession缓存管理器
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(new IsMeSessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(redisSessionDAO());

        //全局会话超时时间（单位毫秒），默认30分钟
        sessionManager.setGlobalSessionTimeout(DefaultWebSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;

    }


    /**
     * 配置shiro的过滤器
     *
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilter.setSecurityManager(securityManager);

        //配置拦截链 使用LinkedHashMap,因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        Map<String,String> filterChain = new LinkedHashMap<String,String>(16);
        // 未授权界面;
        shiroFilter.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());
        //设置默认登录的URL.
        shiroFilter.setLoginUrl(shiroProperties.getLoginUrl());
        // 登录成功后要跳转的链接
        shiroFilter.setSuccessUrl(shiroProperties.getSuccessUrl());
        /**
         * 注入顺序规则
         * authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
         */

        logger.info("***************************从数据库读取权限规则，加载到shiroFilter中*****************************");

        // 注入放行地址（不会被拦截得内容）   测试用的可以不用注入 filterChain.put("/test*","anon")
        if(shiroProperties.getAnonUrls()!=null&&shiroProperties.getAnonUrls().length>0){
            String[] anonUrls = shiroProperties.getAnonUrls();
            for (String anonUrl : anonUrls) {
                filterChain.put(anonUrl,"anon");
                logger.debug("shiro:["+anonUrl+"："+"anon"+"]");
            }
        }

        //注入登出的地址（配置退出过滤器logout登出，由shiro实现）
        if(shiroProperties.getLogOutUrl() != null){
            filterChain.put(shiroProperties.getLogOutUrl(),"logout");
            logger.debug("shiro:["+shiroProperties.getLogOutUrl()+"："+"logout"+"]");
        }

        //注拦截的地址
        if(null != shiroProperties.getAuthcUrls() && shiroProperties.getAuthcUrls().length > 0){
            String[] authcUrls = shiroProperties.getAuthcUrls();
            for (String authcUrl : authcUrls) {
                filterChain.put(authcUrl,"authc");
                logger.debug("shiro:["+authcUrl+"："+"authc"+"]");
            }
        }
        filterChain.put("/**","rememberMe,user");
        shiroFilter.setFilterChainDefinitionMap(filterChain);

/************************************华丽的分割线*******************************************************/
        // 前后端分离模式 start
        Map<String, Filter> filters = new HashMap<>(124);
        filters.put("rememberMe", formAuthenticationFilter());
        //配置过滤器
        shiroFilter.setFilters(filters);
        // 前后端分离模式 end

        return shiroFilter;
    }

    /**
     * 注册shiro的委托过滤器，相当于之前在web.xml里面配置的
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        // 创建注册器
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        // 创建过滤器
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        // zhu'ru注入
        filterRegistrationBean.setFilter(proxy);

        // 设置一些参数
        filterRegistrationBean.addInitParameter("targetFilterLifecycle","true");
        filterRegistrationBean.addInitParameter("targetBeanName","shiroFilter");

        List<String> servletNames=new ArrayList<>();
        servletNames.add(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
        filterRegistrationBean.setServletNames(servletNames);
        return filterRegistrationBean;
    }

    /* 加入注解的使用，不加入这个注解不生效--开始 */

    /**
     * @param securityManager
     * @return
     * @Description：
     *      *  开启shiro aop注解支持.
     *      *  使用代理方式;所以需要开启代码支持;
     *      *  开启 权限注解
     *      *  Controller才能使用@RequiresPermissions
     *    可以在controller中的方法前加上注解
     *    如 @RequiresPermissions("userInfo:add")
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /* 加入注解的使用，不加入这个注解不生效--结束 */


    /**
     * 这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
     *
     *  必须（thymeleaf页面使用shiro标签控制按钮是否显示）
     *  未引入thymeleaf包，Caused by: java.lang.ClassNotFoundException: org.thymeleaf.dialect.AbstractProcessorDialec
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }



    /**
     * 此处不应将自定义Filter注册为 @Bean 否则SpringBoot将加载此Filter导致ShiroFilter优先级失效等一系列问题
     * 该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter -->
     * 前后端分离使用拦截非登录请求。返回相应的信息
     * @return
     */
    public LoginFormAuthenticationFilter formAuthenticationFilter() {
        return new LoginFormAuthenticationFilter();
    }


/************************redisCacheManager(实现shiro的CacheManager) Start**********************************/

    /**
     * Redis集群使用RedisClusterManager，单个Redis使用RedisManager
     * @param redisProperties
     * @return
     *
     * @Description: shiro缓存管理器; 需要添加到securityManager中  使用的是shiro-redis开源插件
     */
    public RedisCacheManager cacheManagers() {
        logger.info("创建cacheManager...");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        //主体ID字段名称。您可以获取唯一标识此主体的ID的字段。
        redisCacheManager.setPrincipalIdFieldName(RedisCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME);
        redisCacheManager.setRedisManager(redisManager());
        // 配置缓存过期时间
        redisCacheManager.setExpire(RedisCacheManager.DEFAULT_EXPIRE);
        redisCacheManager.setKeyPrefix(RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX);
        redisCacheManager.setKeySerializer(new StringSerializer());
        redisCacheManager.setValueSerializer(new ObjectSerializer());
        return redisCacheManager;

    }

    /**
     * 配置redisClusterManager
     * @return
     */
    public RedisClusterManager redisClusterManager() {
        logger.info("创建RedisClusterManager...");
        RedisClusterManager redisManager = new RedisClusterManager();
        redisManager.setHost(host);
//        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        redisManager.setDatabase(database);
        return redisManager;
    }


    /**
     * 配置redisManager
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        logger.info("创建shiro redisManager,连接Redis..URL= " + host + ":" + host);
        redisManager.setHost(host);
//        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        redisManager.setDatabase(database);
        return redisManager;
    }
/********************************************redisCacheManager end*******************************************************/


/********************************************RedisSessionDAO(Redis实现shiro的SessionDao存取session) Start*******************************************************/
    /**
     * 配置会话ID生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 配置redisSessionDAO  使用的是shiro-redis开源插件
     *
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        // 配置缓存管理器
        redisSessionDAO.setRedisManager(redisManager());
        // 配置会话ID生成器
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setExpire(1800);
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        return redisSessionDAO;
    }

/********************************************RedisSessionDAO end*******************************************************/


    /**
     * 解决： 无权限页面不跳转 shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized") 无效
     * shiro的源代码ShiroFilterFactoryBean.Java定义的filter必须满足filter instanceof AuthorizationFilter，
     * 只有perms，roles，ssl，rest，port才是属于AuthorizationFilter，而anon，authcBasic，auchc，user是AuthenticationFilter，
     * 所以unauthorizedUrl设置后页面不跳转 Shiro注解模式下，登录失败与没有权限都是通过抛出异常。
     * 并且默认并没有去处理或者捕获这些异常。在SpringMVC下需要配置捕获相应异常来通知用户信息
     * @return
     */
//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver simpleMappingExceptionResolver=new SimpleMappingExceptionResolver();
//        Properties properties=new Properties();
//        //这里的 /unauthorized 是页面，不是访问的路径
//        properties.setProperty("org.apache.shiro.authz.UnauthorizedException","/unauthorized");
//        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException","/unauthorized");
//        simpleMappingExceptionResolver.setExceptionMappings(properties);
//        return simpleMappingExceptionResolver;
//    }

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    /**
     * 记住我
     * @return
     */
//    @Bean(name = "rememberMeFilter")
//    public RememberMeFilter rememberMeFilter(){
//       return new RememberMeFilter();
//    }

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     * @return
     */
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // cookieRememberMeManager.setCipherKey用来设置加密的Key,参数类型byte[],字节数组长度要求16
        cookieRememberMeManager.setCipherKey(Base64.decode("6ZmI6I2j5Y+R5aSn5ZOlAA=="));
        return cookieRememberMeManager;

    }

    /**
     * 并发登录控制(限制同一账号登录同时登录人数控制)
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        kickoutSessionControlFilter.setCacheManager(cacheManagers());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/login/kickout?kickout=1");
        return kickoutSessionControlFilter;
    }
}