package org.jnyou.gmall.thridpartyservice.service;

import org.jnyou.gmall.thridpartyservice.entity.Sms;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SmsService
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public interface SmsService {
    /**
     * 短信的发现
     * @param sms
     *  短信
     * @return
     * 是否发送成功
     */
    boolean sendMsg(Sms sms);
}
