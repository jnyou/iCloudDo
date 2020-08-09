package org.jnyou.service.strategy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 决策 策略上下文 选择
 * @author 夏小颜
 */
@Component
public class PayChooser implements ApplicationContextAware {

    /**
     * 获取spring容器
     * @param applicationContext
     * @throws BeansException
     */
    private ApplicationContext context;

    private Map<PayType, PayService> chooseMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * 真正策略选择的方法
     * @param payType
     * @return
     */
    public PayService chooser(PayType payType){
        return chooseMap.get(payType);
    }


    @PostConstruct  // 初始化，相当于init-method
    public void registerPayType(){
        // 获取所有接口中所有的实现
        Map<String,PayService> beanMap = context.getBeansOfType(PayService.class);
        // 把枚举类型的支付方式和具体的策略实现关联起来
        beanMap.forEach((String t,PayService u) -> {
            chooseMap.put(u.payType(),u);
        });
    }

}