package org.jnyou.enums;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ChannelRuleEnumNew
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public enum ChannelRuleEnumNew {

    /**
     * 头条
     */
    TOUTIAO("TOUTIAO",new TouTiaoChannelRule()),
    /**
     * 腾讯
     */
    TENCENT("TENCENT",new TencentChannelRule()),
    ;
    public String name;

    public GeneralChannelRule channel;

    ChannelRuleEnumNew(String name, GeneralChannelRule channel) {
        this.name = name;
        this.channel = channel;
    }

    // 匹配方式
    public static ChannelRuleEnumNew match(String name) {
        // 获取到枚举中所有的值
        ChannelRuleEnumNew[] values = ChannelRuleEnumNew.values();
        for (ChannelRuleEnumNew value : values) {
            if(value.name.equals(name)){
                return value;
            }
        }
        return null;
    }

}
