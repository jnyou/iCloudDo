package com.blithe.cms.controller.upload;

import cn.hutool.core.date.DateUtil;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.utils.FileUpdloadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName FileUploadController
 * @Description: 文件上传下载
 * @Author: 夏小颜
 * @Date: 12:08
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/file")
public class FileUploadController {

    /**
     * 文件上传临时根地址
     */
    @Value("${spring.servlet.multipart.location}")
    private String upload;


    @RequestMapping("/uploadFile")
    public R uploadFile(MultipartFile mf) {
        System.out.println("获取的上传地址根目录" + upload);
        // 判断临时地址是否存在,不存在则使用默认的地址
        if(null == upload){
            upload = FileUpdloadUtil.FILE_ROOTPATH;
        }
        // 获取上传过来的旧名字
        String oldName = mf.getOriginalFilename();
        // 修改新名字
        String newName = FileUpdloadUtil.newFileName(oldName);

        // 创建文件夹
        String dir = DateUtil.format(new Date(), "yyyy-MM-dd");

        // 将文件夹放入根目录中
        File dirFile = new File(upload, dir);
        // 判断文件是否存在,不存在则创建
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        // 构造文件对象
        File file = new File(dirFile, newName);

        //将文件写入文件夹中
        try {
            mf.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().put("path", dir + "/" + newName);
    }

    /**
     * 显示图片
     * @param path
     * @return
     */
    @RequestMapping("/showImageFile")
    public ResponseEntity<Object> showImageFile(String path) {
        return FileUpdloadUtil.createResponseEntity(path,upload);
    }

}