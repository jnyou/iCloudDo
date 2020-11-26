package org.jnyou.gmall.thridpartyservice;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class ThridPartyServiceApplicationTests {

    @Autowired
    private OSSClient ossClient;

    @Test
    void contextLoads() {
    }

    @Test
    void testUpload() throws FileNotFoundException {

        // 引入阿里云oss组件存储：spring-cloud-starter-alicloud-oss
        String bucketName = "smallsword-mall";
// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "hahah.jpg";

// 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        InputStream inputStream = new FileInputStream("C:\\Users\\cizing\\Pictures\\liunian.jpg");
        ossClient.putObject(bucketName, objectName, inputStream);
    }

}
