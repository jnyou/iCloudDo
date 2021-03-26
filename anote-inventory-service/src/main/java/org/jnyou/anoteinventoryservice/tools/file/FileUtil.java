package org.jnyou.anoteinventoryservice.tools.file;

import cn.hutool.core.util.IdUtil;

import java.io.File;

/**
 * 分类名称
 *
 * @ClassName FileUtil
 * @Description:
 * @Author: jnyou
 **/
public class FileUtil {

    public static void delAllFiles(File file) {
        if(file.isDirectory()){
            for (File f : file.listFiles()) {
                if(!f.delete()){
                    delAllFiles(f);
                }
            }
        }
        file.delete();
        System.out.println("delete : " + file.getAbsolutePath());
    }

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

}