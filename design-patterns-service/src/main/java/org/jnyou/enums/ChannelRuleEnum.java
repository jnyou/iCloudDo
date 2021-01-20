package org.jnyou.enums;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ChannelRuleEnum
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public enum ChannelRuleEnum {

    /**
     * 头条
     */
    TOUTIAO("TOUTIAO"),
    /**
     * 腾讯
     */
    TENCENT("TENCENT"),
    ;
    public String code;

    ChannelRuleEnum(String code){
        this.code = code;
    };

}
