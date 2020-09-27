package com.blithe.cms.config.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName yjn
 * @Description: shiro属性配置
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
@ConfigurationProperties(prefix = "shiro.conf")
public class ShiroProperties {
    /**
     * 加密方式
     */
    private String hashAlgorithmName;
    /**
     * 散列次数
     */
    private Integer hashIterations;

    private String loginUrl;

    private String unauthorizedUrl;

    private String successUrl;

    private String [] anonUrls;

    private String  logOutUrl;

    private String [] authcUrls;

}