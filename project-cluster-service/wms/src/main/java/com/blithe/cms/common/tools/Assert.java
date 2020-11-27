package com.blithe.cms.common.tools;

import com.guoyin.amtp.exception.AmtpException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 * 
 * @author lujinjun
 * @date 2017-6-17
 *
 */
public abstract class Assert {

	public static void isBlank(String str, String message) {
		if (StringUtils.isBlank(str)) {
			throw new AmtpException(message);
		}
	}

	public static void isNull(Object object, String message) {
		if (object == null) {
			throw new AmtpException(message);
		}
	}
}
