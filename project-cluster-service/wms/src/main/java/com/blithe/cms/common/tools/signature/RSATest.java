package com.blithe.cms.common.tools.signature;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA密钥对生成及测试类
 *
 * @author cizing
 */
public class RSATest {

	public static void main(String[] args) throws Exception {
		String filepath="D:/opt/";  
		
		RSATest.genKeyPair(filepath);
		
        System.out.println("---------------签名过程------------------");  
        String content="itouzi_这是用于签名的原始数据";  
        String signstr=RSASignature.sign(content, RSATest.loadPrivateKeyByFile(filepath));  
        System.out.println("签名原串："+content);  
        System.out.println("签名串："+signstr);  
        System.out.println();  
          
        System.out.println("---------------验签过程------------------");  
        content="itouzi_这是用于签名的原始数据";  
        System.out.println("签名原串："+content);  
        System.out.println("签名串："+signstr);  
        System.out.println("验签结果："+RSASignature.verify(content, signstr, RSATest.loadPublicKeyByFile(filepath)));  
        System.out.println(); 
	}
	
	/**
	 * 随机生成密钥对
	 */
	public static void genKeyPair(String filePath) {
		
		try {
			// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 初始化密钥对生成器，密钥大小为96-1024位
			keyPairGen.initialize(1024, new SecureRandom());
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();		
						
			// 得到公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 得到公钥字符串
			String publicKeyString = Base64.encodeBase64String(publicKey
					.getEncoded());
			// 将密钥对写入到文件
			FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
			BufferedWriter pubbw = new BufferedWriter(pubfw);
			pubbw.write(publicKeyString);
			pubbw.flush();
			pubbw.close();
			pubfw.close();
						
			// 得到私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 得到私钥字符串
			String privateKeyString = Base64.encodeBase64String(privateKey
					.getEncoded());
			
			FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
			BufferedWriter pribw = new BufferedWriter(prifw);
			pribw.write(privateKeyString);
			pribw.flush();
			pribw.close();
			prifw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String loadPublicKeyByFile(String path) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path
					+ "/publicKey.keystore"));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @param keyFileName
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public static String loadPrivateKeyByFile(String path) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path
					+ "/privateKey.keystore"));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}
}
