package org.jnyou.ossservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName yjn
 * @Description: 常量工具类，读取配置文件application.properties中的配置
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Component
public class ContantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

//    @Value("${aliyun.oss.file.filehost}")
//    private String fileHost;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;


    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
//    public static String FILE_HOST ;
    /**
     * 当springc初始化完成的时候会执行afterPropertiesSet方法处理，定义公开静态常量
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
//        FILE_HOST = fileHost;
    }
}