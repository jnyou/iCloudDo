package org.jnyou.gmall.orderservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * @className ThreadPoolConfigProperties
 * @author: JnYou xiaojian19970910@gmail.com
 **/
@ConfigurationProperties(prefix = "mall.thread")
@Component
@Data
public class ThreadPoolConfigProperties {
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Integer keepAliveTime;
}