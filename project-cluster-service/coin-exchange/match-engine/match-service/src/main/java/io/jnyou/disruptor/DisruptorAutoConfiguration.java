package io.jnyou.disruptor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * DisruptorAutoConfiguration
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
@EnableConfigurationProperties(value = DisruptorProperties.class)
public class DisruptorAutoConfiguration {

    private DisruptorProperties disruptorProperties;

    public DisruptorAutoConfiguration(DisruptorProperties disruptorProperties) {
       this.disruptorProperties =  disruptorProperties;
    }

}