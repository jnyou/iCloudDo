package com.blithe.cms.log;

import com.alibaba.fastjson.JSON;
import com.blithe.cms.common.tools.HttpContextUtils;
import com.blithe.cms.common.tools.IPUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Aspect
@Component
public class SysLogAspect {

    private static final Log logger = LogFactory.getLog(SysLogAspect.class);

    /**
     *  定义切点
     */
    @Pointcut(value = "@annotation(com.blithe.cms.log.SysLog)")
    public void SysLog(){

    }

    /**
     *  定义通知（增强方法）
     */
    @AfterReturning(value = "SysLog()")
    public void showSysLog(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 获取到方法
        Method method = signature.getMethod();

        // 获取方法上面的log注解
        SysLog annotation = method.getAnnotation(SysLog.class);
        // 获取注解上的描述信息
        String operation =null;
        if(null != annotation){
            operation = annotation.value();
        }
        // 获取方法的参数
        Object[] args = joinPoint.getArgs();
        String argNameString = JSON.toJSONString(args);

        // 获取ip
        String ip = IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest());

        // 获取类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取方法名
        String methodName = signature.getName();

        logger.info("时间：" + new Date() + "，描述：" + operation + "，IP：" + ip +"，类路径："+ className + "，方法名：" + methodName + "，参数：" + argNameString);
    }


}