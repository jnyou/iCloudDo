package org.jnyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName App
 * @Description:
 * @Author: jnyou
 **/
@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = {"org.jnyou.mapper"})
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        // 策略模式测试类 START
//        PayChooser bean = context.getBean(PayChooser.class);
//        // 支付宝支付
//        bean.chooser(PayType.APPLY_PAY).price(50.0);
//        // 微信支付
//        bean.chooser(PayType.WECHAT_PAY).price(100.0);
        // 策略模式测试类 END


        // 模板方法模式实现双重检验加锁  避免缓存击穿
//        UserService userService = context.getBean(UserService.class);
//        User userByIdTemplate = userService.getUserByIdTemplate(1);
//        System.out.println(userByIdTemplate);
    }

}