package org.jnyou.ossservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jnyou.commonutils.R;
import org.jnyou.ossservice.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@RestController
@RequestMapping("eduoss/fileoss")
@Api(description = "OSS服务")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("uploadOssFile")
    @ApiOperation(value = "上传文件至OSS文档")
    public R uploadOssFile(MultipartFile mpf){
        String fileUrl = ossService.uploadFileAvatar(mpf);
        return R.ok().put("url",fileUrl);
    }

}