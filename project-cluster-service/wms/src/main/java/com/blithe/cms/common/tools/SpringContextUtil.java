package com.blithe.cms.common.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @ClassName SpringContextUtil
 * @Description: 灵活的获取Spring Bean对象
 * @Author: 夏小颜
 * @Date: 22:58
 * @Version: 1.0
 **/
@Service
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.context=applicationContext;
    }

    public static <T> T getBean(String name, Class<T> requiredType){
        return context.getBean(name, requiredType);
    }
}