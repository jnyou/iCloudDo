package org.jnyou.gmall.thridpartyservice.controller;

import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.jnyou.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 分类名称
 *
 * @ClassName OssController
 * @Description: 对象存储服务签名
 * @Author: jnyou
 **/
@RestController
@RequestMapping("/thirdparty/oss")
public class OssController {

    @Autowired
    private OSS ossClient;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;
    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;

    /**
     * OSS的签名获取
     */
    @RequestMapping("/policy")
    public R policy(){
        String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
//        String callbackUrl = "http://88.88.88.88:8888";
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        // 用户上传文件时指定的前缀。
        String dir = format + "/";

        Map<String, String> respMap = null;
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));

        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }

        return R.ok().put("data",respMap);
    }

    /**
     * web 直传的实现
     * 1 前台访问后台获取票据
     * 2 前台使用该票据直接去上传图片
     * https://help.aliyun.com/document_detail/91868.html?spm=a2c4g.11186623.2.15.16076e28Cw94Ga#concept-ahk-rfz-2fb
     *
     * 获取一个上传的票据
     * @return
     */
    @GetMapping("/image/pre/upload")
    public R asyncUpload() {
        String dir = DateUtil.today().replaceAll("-", "/");
        Map<String, String> uploadPolicy = getUploadPolicy(30L,3*1024*1024L,dir,"");
        return R.ok().setData(uploadPolicy);
    }


    /**
     * 获取上传的票据
     *
     * @param expireTime  票据的过期时间
     * @param dir         文件上传的文件夹
     * @param maxFileSize 上传文件的最大体积
     * @param callbackUrl 前端把文件上传成功后的回调地址
     * @return 票据
     */
    private Map<String, String> getUploadPolicy(Long expireTime, Long maxFileSize, String dir, String callbackUrl) {
        // 返回给前端的值
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime); // 过期时间
        // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
        PolicyConditions policyConds = new PolicyConditions();
        // 设置上传的大小 ,最大为3M
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 3 * 1024 * 1024);
        // 上传的到那个文件夹
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        try {
            // 生成一个票据
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);

            // 对票据加密
            byte[] binaryData = new byte[0];

            binaryData = postPolicy.getBytes("utf-8");

            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", "https://" + bucket + "." + endpoint); // host的格式为 https://bucketname.endpoint
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            respMap.put("callbackUrl", callbackUrl); // 前端上传成功了,可以使用该地址来通知后端.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return respMap;
    }


}