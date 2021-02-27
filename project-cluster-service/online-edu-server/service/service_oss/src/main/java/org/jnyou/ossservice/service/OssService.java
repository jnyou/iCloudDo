package org.jnyou.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
public interface OssService {
    /**
     * 上传文件到OSS服务，返回路径保存数据库
     * @param mpf
     * @return
     */
    String uploadFileAvatar(MultipartFile mpf);
}