package org.jnyou.smsservice.service;

import java.util.Map;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
public interface SmsApiService {
    /**
     * 短信发送
     * @param phone
     * @param templateCode
     * @param param
     * @return
     */
    boolean send(String phone);
}