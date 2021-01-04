package org.jnyou.anoteinventoryservice.tools.signature;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名验签类
 * @author jnyou
 */
public class RSASignature {

	/**
	 * 签名算法
	 */
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
	 * 字符编码
	 */
	private static final String DEFAULT_ENCODE = "UTF-8";

	/**
	 * RSA签名
	 * 
	 * @param content 待签名数据
	 * @param privateKey 商户私钥
	 * @param encode 字符集编码
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String encode) {
		
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(encode));
			byte[] signed = signature.sign();

			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA签名(默认字符集：UFT-8)
	 * @param content 待签名数据
	 * @param privateKey 商户私钥
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey) {
		
		return sign(content, privateKey, DEFAULT_ENCODE);
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content 待签名数据
	 * @param sign 签名值
	 * @param publicKey 分配给开发商公钥
	 * @param encode 字符集编码
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign, String publicKey, String encode) {
		
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decodeBase64(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(encode));

			boolean bverify = signature.verify(Base64.decodeBase64(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 */
	public static boolean verify(String content, String sign, String publicKey) {

		return verify(content, sign, publicKey, DEFAULT_ENCODE);
	}
}
