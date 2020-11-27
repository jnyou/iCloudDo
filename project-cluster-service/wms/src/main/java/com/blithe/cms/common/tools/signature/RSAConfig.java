package com.blithe.cms.common.tools.signature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * RSA配置读取
 * 
 * @author lujinjun
 * @date 2017-6-25
 *
 */
public class RSAConfig {

	private static final Logger logger = LoggerFactory.getLogger(RSAConfig.class);
	//密钥文件名
	private static final String PRIKEY = "privateKey.keystore";
	private static final String PUBKEY = "publicKey.keystore";

	//单例对象
	private static RSAConfig rsaConfig = null;
	
	/**
	 * 本方私钥：用于内容签名； 
	 * 本方同时将公钥线下发送给对手方；
	 */
	private String privateKey;
	
	/**
	 * 对方公钥：用于内容验签；
	 * 公钥内容应该由对手方提供给本方；
	 */
	private String publicKey;
	
	/**
	 * 私有对象
	 */
	private RSAConfig() {
		this.loadRSAKeyFromFile();
	}

	/**
	 * 单例
	 * @return
	 */
	public static RSAConfig getConfig() {
		synchronized (RSAConfig.class) {
			if (null == rsaConfig) {
				rsaConfig = new RSAConfig();
			}
			return rsaConfig;
		}
	}

	/**
	 * 加载密钥文件
	 */
	public void loadRSAKeyFromFile() {
		
		InputStream in = null;
		BufferedReader bfReader = null;
		try {
			String readLine = null;
			StringBuilder sbr = new StringBuilder();
			
			//读取私钥
			in = RSAConfig.class.getClassLoader().getResourceAsStream(PRIKEY);
			bfReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			while ((readLine = bfReader.readLine()) != null) {
				sbr.append(readLine);
			}
			bfReader.close();
			in.close();
			this.privateKey = sbr.toString();
			logger.info("私钥：" + this.privateKey);
			
			//读取公钥
			in = RSAConfig.class.getClassLoader().getResourceAsStream(PUBKEY);
			bfReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			sbr.setLength(0);
			while ((readLine = bfReader.readLine()) != null) {
				sbr.append(readLine);
			}
			bfReader.close();
			in.close();
			this.publicKey = sbr.toString();
			logger.info("公钥：" + this.publicKey);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("密钥数据流读取错误", e);
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error("密钥输入流为空", e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取密钥文件异常", e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getPrivateKey() {
		return privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}
}
