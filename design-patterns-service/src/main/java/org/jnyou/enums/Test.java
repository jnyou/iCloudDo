package org.jnyou.enums;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Test
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class Test {
    /**
     * 新的思路使用 枚举方式
     * @Author JnYou
     */
    public static void main(String[] args) {
        ChannelRuleEnumNew channelRule = ChannelRuleEnumNew.match(ChannelRuleEnumNew.TOUTIAO.name);
        channelRule.channel.process();
    }

    /**
     * 直接使用if else 完成；但存在两个缺点
     * 1、新增渠道的时候需要修改执行代码中的code，这违背了设计模式的开放封闭原则。
     * 开放封闭原则的核心的思想是软件实体是可扩展，而不可修改的。 也就是说，对扩展是开放的，而对修改是封闭的。
     * 2、产生大量的if else 代码不够优雅
     * @Author JnYou
     */
    public static void simpleTest() {
        //这里我们模拟接收到的数据，其渠道为为TOUTIAO，来自头条的数据
        String sign = "TOUTIAO";
        GeneralChannelRule rule = null;
        //根据对应渠道获取对应的具体规则实现类
        if (ChannelRuleEnum.TENCENT.code.equals(sign)) {
            rule = new TencentChannelRule();
        } else if (ChannelRuleEnum.TOUTIAO.code.equals(sign)) {
            rule = new TouTiaoChannelRule();
        } else {
            //匹配不到
        }
        //执行
        rule.process();
    }
}