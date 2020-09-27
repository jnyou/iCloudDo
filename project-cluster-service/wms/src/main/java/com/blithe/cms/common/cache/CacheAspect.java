package com.blithe.cms.common.cache;

import com.blithe.cms.pojo.system.Dept;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CacheAspect
 * @Description: 自定义缓存切面类
 * @Author: 夏小颜
 * @Date: 19:52
 * @Version: 1.0
 **/
@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class CacheAspect {

    /**
     * 定义一个Key的前缀
     */
    static final String CACHE_DEPT_KEY_PREFIX = "dept_";

    /**
     *定义一个缓存容器
     */
    Map<String,Object> cacheMaps = new HashMap<>();


    /**
     * 根据id查询缓存
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(* com.blithe.cms.service.system.impl.DeptServiceImpl.selectOne(..))")
    public Object selectByIdCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取第一个参数，也就是查询的id
        Integer fid = (Integer) joinPoint.getArgs()[0];
        // 从缓存中取
        Object deptObj = cacheMaps.get(CACHE_DEPT_KEY_PREFIX + fid);
        if(deptObj == null){
            // 为空 放行 去数据库查询出来
            Dept dept = (Dept) joinPoint.proceed();
            // 放入缓存容器中
            cacheMaps.put(CACHE_DEPT_KEY_PREFIX + dept.getId(),dept);
            return dept;
        }else {
            // 容器中有则直接返回
            return deptObj;
        }
    }

    /**
     * 更新缓存
     * @param joinPoint
     * @return
     * @throws Throwable
     */

    @Around(value = "execution(* com.blithe.cms.service.system.impl.DeptServiceImpl.updateById(..))")
    public Object updateByIdCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取第一个参数，也就是update的一个对象
        Dept dept = (Dept) joinPoint.getArgs()[0];

        // 是否放行来进行判断
        Boolean isFlag = (Boolean) joinPoint.proceed();
        // 为true时，说明放行了,dept为数据库最新数据
        if(isFlag){
            // 从容器中获取  ，看看是否存在这条数据
            Dept deptObj = (Dept) cacheMaps.get(CACHE_DEPT_KEY_PREFIX + dept.getId());
            // 如果容器中没有更新的这条数据 则copy一个
            if(deptObj == null){
                deptObj = new Dept();
                BeanUtils.copyProperties(dept,deptObj);
                // 存入容器中
                cacheMaps.put(CACHE_DEPT_KEY_PREFIX + deptObj.getId(),deptObj);
            }
        }
        return isFlag;
    }


    /**
     * 删除缓存
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(* com.blithe.cms.service.system.impl.DeptServiceImpl.deleteById(..))")
    public Object deleteByIdCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取第一个参数，也就是需要删除的id
        Integer fid = (Integer) joinPoint.getArgs()[0];

        // 是否放行来进行判断
        Boolean isFlag = (Boolean) joinPoint.proceed();
        // 为true时，说明放行了，直接删除容器中的KEY
        if(isFlag){
            cacheMaps.remove(CACHE_DEPT_KEY_PREFIX + fid);
        }
        return isFlag;
    }


}