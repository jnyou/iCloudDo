package org.jnyou.anoteinventoryservice.tools.file;

import org.jnyou.anoteinventoryservice.tools.CompressFileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * @author jnyou
 */
public class UploadUtil {

    public static String resolveCompressUploadFile(HttpServletRequest request,
                                                   MultipartFile file, String path, String parentPath) throws Exception {

        request.setCharacterEncoding("utf-8");
        /* 截取后缀名 */
        if (file.isEmpty()) {
            throw new Exception("文件不能为空");
        }
//		String fileName = file.getOriginalFilename();
        String fileName = new String(file.getOriginalFilename());
        System.out.println("fileName：{}" + fileName);
        int pos = fileName.lastIndexOf(".");
        String extName = fileName.substring(pos + 1).toLowerCase();
        // 判断上传文件必须是zip或者是rar否则不允许上传
        if (!extName.equals("zip") && !extName.equals("rar")) {
            throw new Exception("上传文件格式错误，请重新上传");
        }
        // 时间加后缀名保存
        String saveName = FileUtil.newFileName(fileName);
        // 根据服务器的文件保存地址和原文件名创建目录文件全路径
        File pushFile = new File(path + "/" + saveName);
        File descFile = new File(path);
        if (!descFile.exists()) {
            descFile.mkdirs();
        }
        File parentPathFile = new File(parentPath);
        if (!parentPathFile.exists())
            parentPathFile.mkdirs();
        // 解压目的文件
        String descDir = path + "/";
        System.out.println("desc:" + descDir);
        file.transferTo(pushFile);

        // 开始解压zip
        if (extName.equals("zip")) {
            CompressFileUtils.zipToFile(path + "/" + saveName, parentPath);
        } else if (extName.equals("rar")) {
            // 开始解压rar
            CompressFileUtils.unRarFile2(pushFile.getAbsolutePath(), descDir);
        } else {
            throw new Exception("文件格式不正确不能解压");
        }
        // 删除 解压完的压缩文件
        doDeleteEmptyDir(path + "/" + saveName);
        returnFiles(descDir + "");
        return fileName.substring(0,pos);
    }

    //返回 File[]
    public static File[] returnFiles(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                File f = new File(strPath + File.separator + fileName);
                System.out.println("文件名称：" + fileName);
                if (f.isDirectory()) {// 如果f是目录
                    File[] newfiles = f.listFiles();
                    return newfiles;
                }

            }
        }
        return files;
    }

    //删除指定文件
    private static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    // 删除目录下的所有
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    //保存file
    public static void saveFile(File file, String path, String name) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(path + File.separator
                    + name, true);
            int count = fis.available();// 获取这个文件里共有多少个字节
            byte[] b = new byte[count];
            int len = 0;
            while ((len = fis.read(b)) != -1) {
                fos.write(b);
            }
            //System.out.println("复制成功！");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
