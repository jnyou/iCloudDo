package com.blithe.cms.common.tools;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * zip压缩工具类
 * @author jnyou
 */
public class ZipCompress {

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
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
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
	 * 功能:解压缩
	 * 
	 * @param zipfile
	 *            ：需要解压缩的文件
	 * @param descDir
	 *            ：解压后的目标目录
	 */
	public static void unZipFiles(File zipfile, String descDir) {
		try {
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zf.getInputStream(entry);
				OutputStream out = new FileOutputStream(descDir + zipEntryName);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
				System.out.println("解压缩完成.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能:
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 2个源文件
		File f1 = new File(
				"D:\\tmp\\cebbank\\aixiangjia_20180112113017168_1.jpg");
		File f2 = new File(
				"D:\\tmp\\cebbank\\aixiangjia_20180112113017168_2.jpg");
		File[] srcfile = { f1, f2 };

		// 压缩后的文件
		File zipfile = new File("D:\\tmp\\cebbank\\P_20180112125042.zip");
		ZipCompress.zipFiles(srcfile, zipfile);
	}
}
