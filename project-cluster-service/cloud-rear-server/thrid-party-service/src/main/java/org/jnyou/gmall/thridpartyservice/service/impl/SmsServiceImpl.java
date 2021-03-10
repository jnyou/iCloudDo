package org.jnyou.gmall.thridpartyservice.service.impl;

import com.alibaba.alicloud.sms.ISmsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.gmall.thridpartyservice.entity.Sms;
import org.jnyou.gmall.thridpartyservice.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SmsService
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private ISmsService smsService;

    @Autowired
    private ConfigService configService;

    /**
     * 发送一个短信
     *
     * @param sms 短信内容
     * @return 是否发送成功
     */
    @Override
    public boolean sendMsg(Sms sms) {
        log.info("发送短信{}", JSON.toJSONString(sms, true));
        SendSmsRequest request = buildSmsRequest(sms);
        try {
            SendSmsResponse sendSmsResponse = smsService.sendSmsRequest(request);
            log.info("发送的结果为{}", JSON.toJSONString(sendSmsResponse, true));
            String code = sendSmsResponse.getCode();
            if ("OK".equals(code)) { // 发送成功,否则失败
                sms.setStatus(1);
//                return save(sms); 保存到数据库
            } else {
                return false;
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 构建发送短信的请求对象
     *
     * @param sms
     * @return
     */
    private SendSmsRequest buildSmsRequest(Sms sms) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(sms.getMobile()); //发送给谁
        sendSmsRequest.setSignName("ABC商城"); // 设置签名
        sendSmsRequest.setTemplateCode("SMS_204751106"); // 模板的Code--动态改变
        // Required:The param of sms template.For exmaple, if the template is "Hello,your
        // sendSmsRequest.setTemplateParam("{\"code\":\"" + 1234+ "\"}");
        sms.setContent("1234"); // 最后短信的内容
        return sendSmsRequest;
    }


}
