package org.jnyou.enums;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TouTiaoChannelRule
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class TouTiaoChannelRule extends GeneralChannelRule {

    /**
     * 头条的规则类
     *
     * @Author JnYou
     */
    @Override
    public void process() {
        System.out.println("TouTiao处理逻辑");
    }
}