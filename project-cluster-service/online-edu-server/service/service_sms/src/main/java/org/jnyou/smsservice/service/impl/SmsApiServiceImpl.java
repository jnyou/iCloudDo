package org.jnyou.smsservice.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.jnyou.smsservice.service.SmsApiService;
import org.jnyou.smsservice.utils.RandomUtil;
import org.jnyou.smsservice.utils.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Service
public class SmsApiServiceImpl implements SmsApiService {

    @Autowired
    private SmsApiService smsApiService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean send(String phone) {
        // 判断手机号是否为空
        if(StringUtils.isEmpty(phone)) {return false;}
        // 看redis中是否有，有就直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {return true;}

        // 生成4位随机值  传到ali
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>(1024);
        param.put("code", code);
        SendSmsResponse response = null;
        try {
            response = SendSms.sendSms(phone,param);
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            //请求ID
            System.out.println("RequestId=" + response.getRequestId());
            //发送回执ID,可根据该ID查询具体的发送状态
            System.out.println("BizId=" + response.getBizId());

        /*
            短信接口返回的数据----------------
            Code=OK
            Message=OK
            RequestId=CF0E1F5C-9337-4472-8F1D-4FA9190E9980
            BizId=445311485196164644^0
        */
            //发送成功
            if(response.getCode() != null && "OK".equals(response.getCode())) {
                //记录验证码到redis中
                redisTemplate.opsForValue().set("phone",code,5, TimeUnit.MINUTES);
                return true;
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}