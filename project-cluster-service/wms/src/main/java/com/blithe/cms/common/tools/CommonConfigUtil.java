package com.blithe.cms.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 公共配置信息
 * 
 *
 * @author jnyou
 */
public class CommonConfigUtil {
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	//存储进度条
	public static Map<String, Integer> progressInfo=new HashMap<>();
	// 配置文件最后修改日期
	private static long lastModified;
	
	// 单利对象
	private static CommonConfigUtil commonConfig;
	private CommonConfigUtil() throws Exception{
		try{
			Properties p = new Properties();
			p.load(CommonConfigUtil.class.getResourceAsStream("/amtp.properties"));
			this.imessageUrl = p.getProperty("imessageUrl");
		}catch(IOException e){
			throw new Exception(e);
		}
	}
	
	/**
	 * 单例对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public static CommonConfigUtil getInstance() throws Exception{
		
        File file = new File("/amtp.properties");
        long lastModifiedTemp = file.lastModified();
        if(lastModifiedTemp != lastModified){
            lastModified = lastModifiedTemp;
            commonConfig = new CommonConfigUtil();
        }
		return commonConfig;
	}
	
	// 消息系统地址
	private String imessageUrl;
	
	/**
	 * 消息系统地址
	 * @return url
	 */
	public String getImessageUrl() {
		return imessageUrl;
	}
}
