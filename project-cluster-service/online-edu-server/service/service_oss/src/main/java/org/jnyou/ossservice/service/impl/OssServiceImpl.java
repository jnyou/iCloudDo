package org.jnyou.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.jnyou.ossservice.service.OssService;
import org.jnyou.ossservice.utils.ContantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传文件到OSS服务，返回路径保存数据库
     * @param mpf
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile mpf) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ContantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ContantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ContantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ContantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            // 获取上传文件的输入流
            inputStream = mpf.getInputStream();

            // 获取文件原始名称 在文件名称里面添加随机唯一的值，防止名称重复被覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String filename = uuid + mpf.getOriginalFilename();
            // 把文件按照日期进行分类  // 2020/6/5/1.jpg
            // 获取当前日期 使用了joda-time依赖包
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 返回 https://jnyou.oss-cn-beijing.aliyuncs.com/2020/06/05/03c63e70a37942ba9132bc5052ea8556timg.jpg
            filename = datePath + "/" +filename;

            // 调用oss方法实现上传
            // 第一个参数 bucket名称
            // 第二个参数 上传到OSS的文件路径和文件名称  /aaa/bbb/1.png
            ossClient.putObject(bucketName, filename, inputStream);

            // 把上传的文件路径返回
            // 需要手动拼接出来url
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;

            // 关闭OSSClient。
            ossClient.shutdown();

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}