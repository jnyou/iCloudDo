package com.blithe.cms.common.tools;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName SendSms
 * @Description: aliyun短信服务
 * @Author: 夏小颜
 * @Date: 11:06
 * @Version: 1.0
 **/
@Slf4j
public class SendSms {

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // 可在我的阿里云中找到
    private static String accessKeyId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxx";

    // 可在我的阿里云中找到
    private static String accessSecret = "xxxxxxxxxxxxxxxxxxxxxxxxxxx";

    // 短信签名【去阿里控制台配置】
    public static String SMS_SIGN = "叮咚网城";
    // 短信模板【去阿里控制台配置】
    public static String SMS_TEMP = "SMS_186618948";

    public static SendSmsResponse sendSms(String phone) throws ClientException {
        SendSmsResponse sendSmsResponse = null;
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient client = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        request.setSysMethod(MethodType.POST);
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SMS_SIGN);
        request.putQueryParameter("TemplateCode", SMS_TEMP);
        request.putQueryParameter("TemplateParam", "{\"code\":"+getRandNum()+"}");
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //请求失败这里会抛ClientException异常
            sendSmsResponse = client.getAcsResponse(request);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")){
                //请求成功
                log.info("手机号：" + phone + ", 发送成功！MessageId：" + sendSmsResponse.getRequestId());
            }else{
                log.info("手机号：" + phone + ", 发送失败！Message：" + sendSmsResponse.getMessage());
            }
        } catch (ServerException e) {
            e.printStackTrace();
            log.warn("手机号：" + phone +  ", 发送失败！errCode：" + e.getErrCode());
        } catch (ClientException e) {
            log.warn("手机号：" + phone +  ", 发送失败！errMsg：" + e.getMessage());
            e.printStackTrace();
        }
        return sendSmsResponse;
    }


    /**
     * 生成6位随机验证码
     * @return
     */
    public static String getRandNum() {
        String verifyCode = String
                .valueOf(new Random().nextInt(899999) + 100000);
        return verifyCode;
    }



    public static void main(String[] args) throws ClientException, InterruptedException {
        //发短信
        //String phone, String validCode, String signName, String templateCode
        String phone = "18296557705";
        SendSmsResponse response = sendSms(phone);
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
        if(response.getCode() != null && response.getCode().equals("OK")) {
            //记录验证码到数据库
        }
        /*Thread.sleep(3000L);

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }else{
            System.out.println("else");
        }*/

    }

}