package com.blithe.cms.common.tools;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * Description
 */
public class AliSmsUtil {
    private static Logger log = LogManager.getLogger(AliSmsUtil.class);

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    //短信签名
    public static String SMS_SIGN;
    //短信模板
    public static String SMS_TEMP;
    //预警积分短信模板
    public static String SMS_WARN_TEMP;
    //积分预警短信参数: 配资公司名称
    public static String SMS_COMPANY_NAME;
    //状态是S0,S6超过1分钟未发生变化的交易预警短信模板
    public static String SMS_TRADE_WARN;
    //状态是S7交易预警短信模板
    public static String SMS_TRADE_WARNS7;

    private static String accessKeyId;
    private static String accessKeySecret;

    static {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("amtp");
        accessKeyId = bundle.getString("aliyun.sms.accessKeyId");
        accessKeySecret = bundle.getString("aliyun.sms.accessKeySecret");
        SMS_TEMP = bundle.getString("aliyun.sms.temp");
        SMS_WARN_TEMP = bundle.getString("aliyun.sms.warntemp");
        SMS_TRADE_WARN = bundle.getString("aliyun.sms.tradewarn");
        SMS_TRADE_WARNS7 = bundle.getString("aliyun.sms.tradewarns7");
        try {
            SMS_SIGN = new String(bundle.getString("aliyun.sms.sigh").getBytes("ISO-8859-1"),"utf8");
            SMS_COMPANY_NAME = new String(bundle.getString("company_name").getBytes("ISO-8859-1"),"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 阿里发送短信
     * @param phone 手机号
     * @param parameters key value格式的参数，key为短信模板定义的变量名
     * @param signName 短信签名【去阿里控制台配置】
     * @param templateCode 短信模板【去阿里控制台配置】
     * @return
     */
    public static SendSmsResponse sendValidCodeMsg(String phone, Map parameters, String signName, String templateCode){
        SendSmsResponse sendSmsResponse=null;
        String jsonString = new JSONObject(parameters).toJSONString();
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient,暂不支持多region
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();

            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam(jsonString);
            //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            sendSmsResponse = acsClient.getAcsResponse(request);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                log.info("手机号：" + phone + "，参数：" + jsonString + ", 发送成功！MessageId：" + sendSmsResponse.getRequestId());
            }else{
                log.info("手机号：" + phone + "，参数：" + jsonString + ", 发送失败！Message：" + sendSmsResponse.getMessage());
            }
        } catch (ServerException e1) {
            e1.printStackTrace();
            log.warn("手机号：" + phone + "，参数：" + jsonString + ", 发送失败！errCode：" + e1.getErrCode());
        } catch (ClientException e1) {
            e1.printStackTrace();
            log.warn("手机号：" + phone + "，参数：" + jsonString + ", 发送失败！errCode：" + e1.getErrCode());
        } catch(Exception e){
            log.warn("手机号：" + phone + "，参数：" + jsonString + ", 发送失败！errMsg：" + e.getMessage());
            e.printStackTrace();
        }finally {
            return sendSmsResponse;
        }
    }

    public static void main(String[] args) throws ClientException, InterruptedException {
        //发短信
        //String phone, String validCode, String signName, String templateCode
        Map map = new HashMap();
        map.put("name", "crazy");
        map.put("code", 123456);
        SendSmsResponse response = sendValidCodeMsg("",map,"","");
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
            RequestId=0FF8CB70-BEEF-48E4-BEE7-93C7DCEACFF7
            BizId=521507041132864546^0
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

    /*public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("13001140398");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }*/

    /**
     * 生成6位随机验证码
     * @return
     */
    public static String getRandNum() {
        String verifyCode = String
                .valueOf(new Random().nextInt(899999) + 100000);
        return verifyCode;
    }

}