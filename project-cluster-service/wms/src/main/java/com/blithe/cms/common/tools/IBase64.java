package com.blithe.cms.common.tools;
import org.apache.commons.codec.binary.Base64;

/**
 * @author jnyou
 */
public class IBase64 {
	
	/**
	 * 使用commons-codec的base64 加密字符串
	 * */
	public static String CCBase64Encoder(String str)
	{
		 
		return new String(Base64.encodeBase64(str.getBytes()));
	}
	
	/**
	 * 使用commons-codec的base64 解密字符串
	 * */
	public static String CCBase64Decoder(String str)
	{
		return new String(Base64.decodeBase64(str.getBytes()));
	}
}
