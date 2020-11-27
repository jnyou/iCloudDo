package com.blithe.cms.common.tools;

import com.guoyin.amtp.exception.AmtpException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description 漫道科技发送短信
 * Created by crazy
 * Created on 2019/4/30.
 */
public class MdSmsUtil {

    private static Logger log = LogManager.getLogger(MdSmsUtil.class);

    /*
	 * webservice服务器定义
	 */
    //调用注册方法可能不成功。
    //java.io.IOException: Server returned HTTP response code: 400 for URL: http://sdk.entinfo.cn:8060/webservice.asmx。
    //如果出现上述400错误，请参考第102行。
    //如果您的系统是utf-8，收到的短信可能是乱码，请参考第102，295行
    //可以根据您的需要自行解析下面的地址
    //http://sdk2.zucp.net:8060/webservice.asmx?wsdl
    static final String serviceURL = "http://sdk.entinfo.cn:8060/webservice.asmx";

    private static String sn = "";// 序列号
    private static String password = "";
    private static String pwd = "";// 加密后的密码
    private static Map<String,String> clientMap =  new HashMap();// ext扩展码对应的客户端名称

    static {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("amtp");
        sn = bundle.getString("md.sms.sn");
        password = bundle.getString("md.sms.password");
        try {
            pwd = getMD5(sn + password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String clientStr = null;
        try {
            clientStr = new String(bundle.getString("sms.user").getBytes("ISO-8859-1"),"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] clientArr = clientStr.split(";");
        for (String clientMapStr:clientArr) {
            String[] clientMapArr = clientMapStr.split(":");
            clientMap.put(clientMapArr[0],clientMapArr[1]);
        }
    }

    /*
     * 方法名称：send
     * 功    能：发送UTF8短信 ,传多个手机号就是群发，一个手机号就是单条提交
     * 参    数：mobile,content,ext(手机号【支持10000个手机号，建议最多1000个】，内容，短信签名扩展码【与服务商约定，一一对应】
     * 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
     */
    public static String send(String mobile, String content, String ext) {
        if (StringUtil.isEmpty(ext)){
            ResourceBundle bundle = PropertyResourceBundle.getBundle("amtp");
            String clientExtStr = null;
            try {
                clientExtStr = new String(bundle.getString("sms.default").getBytes("ISO-8859-1"),"utf8");
            } catch (UnsupportedEncodingException e) {
                log.warn("手机号：" + mobile + ", 内容：" + content + " 发送失败！errMsg：" + e.getMessage());
                e.printStackTrace();
                throw new AmtpException("参数错误，请联系管理员");
            }
            String[] clientExtArr = clientExtStr.split(":");
            content = "【"+clientExtArr[1]+"】"+content;
            ext = clientExtArr[0];
        }else{
            String clientName = clientMap.get(ext);//扩展码对应的签名
            if (StringUtil.isEmpty(clientName)){
                log.warn("发送给：" + mobile + "的短信，内容为：" + content + "【短信签名扩展码无对应客户端名称】");
                throw new AmtpException("参数错误，请联系管理员");
            }
            content = "【"+clientName+"】"+content;
        }

        String result = "";
        String soapAction = "http://tempuri.org/mdSmsSend_u";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mdSmsSend_u xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "<mobile>" + mobile + "</mobile>";
        xml += "<content>" + content + "</content>";
        xml += "<ext>" + ext + "</ext>";
        xml += "<stime></stime>";
        xml += "<rrid></rrid>";
        xml += "</mdSmsSend_u>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";
        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes("GBK"));
            //如果您的系统是utf-8,这里请改成bout.write(xml.getBytes("GBK"));

            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String
                    .valueOf(b.length));
            httpconn.setRequestProperty("Content-Type",
                    "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn
                    .getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<mdSmsSend_uResult>(.*)</mdSmsSend_uResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }

            if(result.startsWith("-")||result.equals("")){//发送短信，如果是以负号开头就是发送失败。
                log.info("手机号：" + mobile + ", 内容：" + content + " 发送失败！返回值为："+result+"请查看webservice返回值对照表");
            }else {//输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
                log.info("手机号：" + mobile + ", 内容：" + content + " 发送成功！返回值为：" + result);
            }
            return result;
        } catch (Exception e) {
            log.warn("手机号：" + mobile + ", 内容：" + content + " 发送失败！errMsg：" + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }


    /*
     * 方法名称：getMD5
     * 功    能：字符串MD5加密
     * 参    数：待转换字符串
     * 返 回 值：加密之后字符串
     */
    public static String getMD5(String sourceStr) throws UnsupportedEncodingException {
        String resultStr = "";
        try {
            byte[] temp = sourceStr.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            // resultStr = new String(md5.digest());
            byte[] b = md5.digest();
            for (int i = 0; i < b.length; i++) {
                char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'A', 'B', 'C', 'D', 'E', 'F' };
                char[] ob = new char[2];
                ob[0] = digit[(b[i] >>> 4) & 0X0F];
                ob[1] = digit[b[i] & 0X0F];
                resultStr += new String(ob);
            }
            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String send = MdSmsUtil.send("13000000000", "短信内容", null);
    }
}
