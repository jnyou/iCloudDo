package com.blithe.cms.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;

/**
 * @ClassName uploadFileUtil
 * @Description: 文件上传下载的工具类
 * @Author: 夏小颜
 * @Date: 12:13
 * @Version: 1.0
 **/
public class FileUpdloadUtil {

    /**
     * 默认文件路径的根路径
     */
    public static String FILE_ROOTPATH = "D:/projectdev/defaultFilePath/" ;


    /**
     * 根据旧名字获取到新名字
     * @param oldName
     * @return
     */
    public static String newFileName(String oldName) {
        // 获取文件的后缀.包括.
        String suffix = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        return IdUtil.simpleUUID().toUpperCase() + suffix;
    }


    /**
     * 显示图片
     * @param path 当前文件的相对路径
     * @param upload 文件的根目录
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path,String upload) {

        //1,构造文件对象
        File file=new File(upload, path);
        if(file.exists()) {
            //将下载的文件，封装byte[]
            byte[] bytes=null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //创建封装响应头信息的对象
            HttpHeaders header=new HttpHeaders();
            //封装响应内容类型(APPLICATION_OCTET_STREAM 响应的内容不限定)
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置下载的文件的名称
//			header.setContentDispositionFormData("attachment", "123.jpg");
            //创建ResponseEntity对象
            ResponseEntity<Object> entity=
                    new ResponseEntity<Object>(bytes, header, HttpStatus.CREATED);
            return entity;
        }
        return null;
    }
}