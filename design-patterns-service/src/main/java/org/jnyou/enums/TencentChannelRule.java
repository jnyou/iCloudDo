package org.jnyou.enums;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TencentChannelRule
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class TencentChannelRule extends GeneralChannelRule {

    /**
     * 腾讯的规则类
     *
     * @Author JnYou
     */
    @Override
    public void process() {
        System.out.println("Tencent处理逻辑");
    }
}