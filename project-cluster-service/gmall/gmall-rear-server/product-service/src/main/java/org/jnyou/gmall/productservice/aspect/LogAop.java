package org.jnyou.gmall.productservice.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jnyou.gmall.productservice.annotation.EagleEye;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 分类名称
 *
 * @ClassName LogAop
 * @Description: 日志切面
 * @Author: jnyou
 **/
@Component
@Aspect
@EnableAspectJAutoProxy
@Slf4j
public class LogAop {

    @Pointcut("@annotation(org.jnyou.gmall.productservice.annotation.EagleEye)")
    public void eagleEye() {
    }

    @Around("eagleEye()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        // 请求开始时间戳
        long begin = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        EagleEye eagleEye = method.getAnnotation(EagleEye.class);

        log.info("=================================请求开始=================================");
        // 请求链接
        log.info("请求链接：{}", request.getRequestURI().toString());
        // 接口描述信息
        log.info("接口描述：{}", eagleEye.desc());
        // 请求类型
        log.info("请求类型：{}", request.getMethod());
        // 请求方法
        log.info("请求方法：{}_{}", signature.getDeclaringTypeName(), signature.getName());
        // 请求IP
        log.info("请求IP：{}", request.getRemoteAddr());
        // 请求入参
        log.info("请求入参：{}", JSON.toJSONString(pjp.getArgs()));
        Object result = pjp.proceed();

        // 请求结束时间戳
        long end = System.currentTimeMillis();
        // 请求耗时
        log.info("请求耗时：{} ms", end - begin);
        // 请求返回
        log.info("请求返回：{}", JSON.toJSONString(result));
        log.info("=================================请求结束=================================");
        return result;
    }

}