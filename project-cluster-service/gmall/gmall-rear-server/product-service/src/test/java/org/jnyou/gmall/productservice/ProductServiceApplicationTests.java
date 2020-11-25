package org.jnyou.gmall.productservice;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jnyou.gmall.productservice.entity.BrandEntity;
import org.jnyou.gmall.productservice.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {

//        brandService.save(new BrandEntity().setName("华为").setLogo("http:baidu.com").setDescript("国际品牌").setShowStatus(0));

        LambdaQueryWrapper<BrandEntity> select = Wrappers.<BrandEntity>lambdaQuery().select(BrandEntity::getName, BrandEntity::getLogo);
        System.out.println(select.getSqlSelect());

    }

    @Test
    void testUpload() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G4kd2LUwd58wQyh3Th7";
        String accessKeySecret = "gFfw1p6Eo3u7YDhPv1dFR6cNPgkjq7";
        String bucketName = "smallsword-mall";
// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "timg.jpg";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        InputStream inputStream = new FileInputStream("C:\\Users\\YJN\\Pictures\\Saved Pictures\\timg (3).jpg");
        ossClient.putObject(bucketName, objectName, inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
    }

}
