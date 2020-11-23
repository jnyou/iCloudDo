package org.jnyou.frame;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 分类名称
 *
 * @ClassName AService
 * @Description:
 * @Author: jnyou
 **/
@Service
public class AService implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    public int count = 10;

    @Autowired
    private AService aService;

    @Autowired
    private BService bService;

    private ApplicationContext applicationContext;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    public void testAopProxy(){
        System.out.println(aService);
        System.out.println("测试AOP切面");
        aService.testAopProxy();
    }

    @Override
    public void setBeanName(String s) {

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}