package org.jnyou.starter.autoconfigure.jpush;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 参数配置
 *
 * @author Jnyou
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "j-push")
public class JPushProperties {

    private String appKey;

    private String masterSecret;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

}
