package com.blithe.cms.common.tools;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件工具类  支持zip、rar
 * @author jnyou
 */
public class CompressFileUtils {

    public static void unZipFiles(HttpServletRequest request, String zipPath,
                                  String descDir) throws IOException {
        unZipFiles(request, new File(zipPath), descDir);
    }

    /**
     * 解压文件到指定目录
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(HttpServletRequest request, File zipFile,
                                  String descDir) throws IOException {
        request.setCharacterEncoding("utf-8");
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration entries = zip.getEntries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            entry.setUnixMode(644); // 解决linux乱码
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);

            BufferedInputStream bis = new BufferedInputStream(in);

            String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
            System.out.println(outPath);

            OutputStream out = new FileOutputStream(outPath);
            Writer out1 = new OutputStreamWriter(out, "UTF-8");
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        System.out.println("******************解压完毕********************");
    }

    /**
     * 根据原始rar路径，解压到指定文件夹下.
     *
     * @param srcRarPath       原始rar路径
     * @param dstDirectoryPath 解压到的文件夹
     * @throws UnsupportedEncodingException
     */
    public static void unRarFile(HttpServletRequest request, String srcRarPath,
                                 String dstDirectoryPath) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        if (!srcRarPath.toLowerCase().endsWith(".rar")) {
            System.out.println("非rar文件！");
            return;
        }
        File dstDiretory = new File(dstDirectoryPath);
        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
            dstDiretory.mkdirs();
        }
        Archive a = null;
        try {
            a = new Archive(new File(srcRarPath));
            if (a != null) {
                a.getMainHeader().print(); // 打印文件信息.
                FileHeader fh = a.nextFileHeader();
                while (fh != null) {
                    String fileName = fh.getFileNameW().isEmpty() ? fh.getFileNameString() : fh.getFileNameW();
                    System.out.println("fileName" + fileName);
                    if (fh.isDirectory()) { // 文件夹
                        File fol = new File(dstDirectoryPath + File.separator
                                + fileName);
                        fol.mkdirs();
                    } else { // 文件
                        File out = new File(dstDirectoryPath + File.separator
                                + fh.getFileNameString().trim());
                        // System.out.println(out.getAbsolutePath());
                        try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.

                            if (!out.exists()) {
                                if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
                                    out.getParentFile().mkdirs();
                                }
                                out.createNewFile();
                            }
                            FileOutputStream os = new FileOutputStream(out);
                            a.extractFile(fh, os);
                            os.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    fh = a.nextFileHeader();
                }
                a.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能:解压缩 、解压Zip文件 (pass)
     *
     * @param zipFileName  需要解压缩的文件位置
     * @param descFileName 将文件解压到某个路径
     * @throws IOException
     */
    public static void zipToFile(String zipFileName, String descFileName) throws IOException {
        System.out.println("文件解压开始...");
        String descFileNames = descFileName;
        if (!descFileNames.endsWith(File.separator)) {
            descFileNames = descFileNames + File.separator;
        }
        try {
            org.apache.tools.zip.ZipFile zipFile = new ZipFile(zipFileName,"GBK");
            org.apache.tools.zip.ZipEntry entry = null;
            String entryName = null;
            String descFileDir = null;
            byte[] buf = new byte[4096];
            int readByte = 0;
            @SuppressWarnings("rawtypes")
            Enumeration enums = zipFile.getEntries();
            while (enums.hasMoreElements()) {
                entry = (org.apache.tools.zip.ZipEntry) enums.nextElement();
                entryName = entry.getName();
                descFileDir = descFileNames + entryName;
                if (entry.isDirectory()) {
                    new File(descFileDir).mkdir();
                    continue;
                } else {
                    new File(descFileDir).getParentFile().mkdir();
                }
                File file = new File(descFileDir);
                OutputStream os = new FileOutputStream(file);
                InputStream is = zipFile.getInputStream(entry);
                while ((readByte = is.read(buf)) != -1) {
                    os.write(buf, 0, readByte);
                }
                os.close();
                is.close();
            }
            zipFile.close();
            System.out.println("文件解压成功!");
        } catch (Exception e) {
            System.out.println("文件解压失败!");
            e.printStackTrace();
        }

    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param zippath     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */

    private static File getRealFileName(String zippath, String absFileName) {
//		log.info("文件名：" + absFileName);
        String[] dirs = absFileName.split("/", absFileName.length());
        File ret = new File(zippath);// 创建文件对象
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                ret = new File(ret, dirs[i]);
            }
        }
        if (!ret.exists()) {// 检测文件是否存在
            ret.mkdirs();// 创建此抽象路径名指定的目录
        }
        ret = new File(ret, dirs[dirs.length - 1]);// 根据 ret 抽象路径名和 child
        // 路径名字符串创建一个新 File 实例
        return ret;
    }

    /**
     * 根据原始rar路径，解压到指定文件夹下.
     *
     * @param srcRarPath       原始rar路径
     * @param dstDirectoryPath 解压到的文件夹
     */
    public static void unRarFile2(String srcRarPath, String dstDirectoryPath) {
        if (!srcRarPath.toLowerCase().endsWith(".rar")) {
            System.out.println("非rar文件！");
            return;
        }
        System.out.println("dstDirectoryPath" + dstDirectoryPath);
        File dstDiretory = new File(dstDirectoryPath);
        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
            dstDiretory.mkdirs();
        }
        Archive a = null;
        try {
            a = new Archive(new File(srcRarPath));
            if (a != null) {
                //a.getMainHeader().print(); // 打印文件信息.
                FileHeader fh = a.nextFileHeader();
                while (fh != null) {
                    //防止文件名中文乱码问题的处理
                    String fileName = fh.getFileNameW().isEmpty() ? fh.getFileNameString() : fh.getFileNameW();
                    if (fh.isDirectory()) { // 文件夹
                        File fol = new File(dstDirectoryPath + File.separator + fileName);
                        //  fol.mkdirs();
                    } else { // 文件
                        File out = new File(dstDirectoryPath + File.separator + fileName.trim());
                        try {
                            if (!out.exists()) {
                                if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
                                    out.getParentFile().mkdirs();
                                }
                                out.createNewFile();
                            }
                            FileOutputStream os = new FileOutputStream(out);
                            a.extractFile(fh, os);
                            os.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    fh = a.nextFileHeader();
                }
                a.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能:压缩多个文件成一个zip文件
     *
     * @param srcfile
     *            ：源文件列表
     * @param zipfile
     *            ：压缩后的文件
     */
    public static void zipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            // ZipOutputStream类：完成文件或文件夹的压缩
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new java.util.zip.ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        // 2个源文件
        File f1 = new File(
                "D:\\tmp\\cebbank\\aixiangjia_20180112113017168_1.jpg");
        File f2 = new File(
                "D:\\tmp\\cebbank\\aixiangjia_20180112113017168_2.jpg");
        File[] srcfile = { f1, f2 };

        // 压缩后的文件
        File zipfile = new File("D:\\tmp\\cebbank\\P_20180112125042.zip");
        CompressFileUtils.zipFiles(srcfile, zipfile);

        CompressFileUtils.zipToFile("C:\\img_upload_test\\WaveGlider_发布版.zip","C:\\img_upload_test");
    }

}