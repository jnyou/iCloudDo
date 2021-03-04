package io.jnyou.aspect;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import io.jnyou.domain.SysUserLog;
import io.jnyou.model.WebLog;
import io.jnyou.service.SysUserLogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;

@Aspect
@Component
@Order(2)
@Slf4j
public class WebLogAdminAspect {

    /**
     * 雪花算法
     * 1 : 机器的id
     * 2 : 应用的id
     */
    private Snowflake snowflake = new Snowflake(1,1) ;

    @Autowired
    private SysUserLogService sysUserLogService ;

    /**
     * 日志记录，logstash处理
     */

    @Pointcut("execution( * io.jnyou.controller.*.*(..))")
    public void webLog() {
    }

    @Around(value = "webLog()")
    public Object recordWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        // 创建计时器
        StopWatch stopWatch = new StopWatch();
        //  开始计时器
        stopWatch.start();
        // 不需要我们自己处理这个异常
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        // 记时结束
        stopWatch.stop();

        // 获取请求的上下文
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取登录的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取方法
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 获取方法上的ApiOperation注解
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        // 获取目标对象的类型名称
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        // 获取请求的url 地址
        String requestUrl = request.getRequestURL().toString();
        WebLog webLog = WebLog.builder()
                .basePath(StrUtil.removeSuffix(requestUrl, URLUtil.url(requestUrl).getPath())) // http://ip:port/
                .description(annotation == null ? "no desc" : annotation.value())
                .ip(request.getRemoteAddr())
                .parameter(getMethodParameter(method, proceedingJoinPoint.getArgs()))
                .method(className + "." + method.getName())
                .result(result == null ? "" : JSON.toJSONString(result))
                .recodeTime(System.currentTimeMillis())
                .spendTime(stopWatch.getTotalTimeMillis())
                .uri(request.getRequestURI())
                .url(request.getRequestURL().toString())
                .username(authentication == null ? "anonymous" : authentication.getPrincipal().toString())
                .build();
        log.info(JSON.toJSONString(webLog, true));

        SysUserLog sysUserLog = new SysUserLog();

        sysUserLog.setId(snowflake.nextId());
        sysUserLog.setCreated(new Date());
        sysUserLog.setDescription(webLog.getDescription());
        sysUserLog.setGroup(webLog.getDescription());
        sysUserLog.setUserId(Long.valueOf(webLog.getUsername()));
        sysUserLog.setMethod(webLog.getMethod());
        sysUserLog.setIp(sysUserLog.getIp());
        // 保存系统登录日志信息
        sysUserLogService.save(sysUserLog) ;

        return result;
        /**
         * result{
         * 	"basePath":"http://localhost:8080",
         * 	"description":"测试方法",
         * 	"ip":"0:0:0:0:0:0:0:1",
         * 	"method":"io.jnyou.controller.TestController.testMethod",
         * 	"parameter":{
         * 		"param":"\"paramValue\"",
         * 		"param1":"\"paramValue\""
         *        },
         * 	"recodeTime":1614673332184,
         * 	"result":"{\"code\":200,\"data\":\"ok\"}",
         * 	"spendTime":0,
         * 	"uri":"/common/test",
         * 	"url":"http://localhost:8080/common/test",
         * 	"username":"1018715142409592835"
         * }
         */
    }

    /**
     * {
     * "":value,
     * "":"value"
     * }
     * 获取方法执行的参数
     *
     * @param method
     * @param args
     * @return
     */
    private Object getMethodParameter(Method method, Object[] args) {
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        // 方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        HashMap<String, Object> methodParameters = new HashMap<>(124);
        Parameter[] parameters = method.getParameters();
        if (args != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                methodParameters.put(parameterNames[i], args[i] == null ? "" : JSON.toJSONString(args[i]));
            }
        }
        return methodParameters;
    }
}