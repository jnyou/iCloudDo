package org.jnyou.mall.auth.biz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * LoginChoose
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Component
@Slf4j
public class LoginChoose implements ApplicationContextAware, InitializingBean {

    private static final Map<LoginEnum, ThirdLoginStrategy> CURRENT_MAPS = new ConcurrentHashMap<>();

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public ThirdLoginStrategy getLoginType(LoginEnum loginEnum) {
        return CURRENT_MAPS.get(loginEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, ThirdLoginStrategy> beans = context.getBeansOfType(ThirdLoginStrategy.class);
        log.info("对象池中实例化的bean:{}", beans.values());
        beans.forEach((String e, ThirdLoginStrategy t) -> {
            log.info("登录类型：{}，登录实例：{}", t.LOGIN_ENUM(), t);
            CURRENT_MAPS.put(t.LOGIN_ENUM(), t);
        });
    }
}