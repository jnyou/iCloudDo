/**
 * 
 */
package com.blithe.cms.common.tools;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：字符串工具类
 * 
 */
public class StringUtil {

	public static String _HREF_URL_REGEX = "(http:|https:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#!]]*";
	public static final String EMPTY_STRING = "";
	private static char chars[] = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();
	private static final char IntChars[] = "1234567890".toCharArray();

	/**
	 * 将数据结果集中的字段值转为String
	 * 
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		return String.valueOf(null == obj ? "" : obj).trim();
	}

	/**
	 * 将数据结果集中的字段值转为String
	 *
	 * @param obj
	 * @param def
	 * @return
	 */
	public static String getString(Object obj,String def) {
		try{
			if(StringUtils.isEmpty(obj)){
				return def;
			}
			return String.valueOf(obj);
		}catch(Exception e){
			return def;
		}
	}

	public static void main(String[] args) {
		System.out.println(getInt(""));;
	}
	/**
	 * 获得int
	 * 
	 * @param val
	 * @return
	 */
	public static int getInt(Object val) {
		if (val == null)
			return -1;
		return getInt(val.toString(), -1);
	}

	/**
	 * 获得int
	 * 
	 * @param val
	 * @param def
	 * @return
	 */
	public static int getInt(Object val, int def) {
		try {
			return Integer.parseInt(val.toString().trim());
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 获得long
	 * 
	 * @param obj
	 * @return
	 */
	public static long getLong(Object obj) {
		return getLong(obj, -1);
	}

	/**
	 * 获得long
	 * 
	 * @param obj
	 * @param def
	 * @return
	 */
	public static long getLong(Object obj, long def) {
		long value = 0L;
		if (null == obj || "".equals(obj.toString().trim())) {
			return def;
		}
		try {
			value = Long.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
		return value;
	}

	/**
	 * 获得double
	 * 
	 * @param obj
	 * @return
	 */
	public static double getDouble(Object obj) {
		return getDouble(obj, 0.00);
	}

	/**
	 * 获得double
	 * 
	 * @param obj
	 * @param def
	 * @return
	 */
	public static double getDouble(Object obj, double def) {
		double value = 0.0;
		if (null == obj || "".equals(obj.toString().trim())) {
			return def;
		}
		try {
			value = Double.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
		return value;
	}
	
	/**
	 * 将数据结果集中的日期字段值转为Date
	 * @param obj
	 * @return
	 */	
	public static Date getDate(Object obj) {
		Date value = null;
		if (null == obj || "".equals(obj.toString().trim())) {
			return value;
		}
		try {
			value = (Date)obj;
		} catch (Exception e) {
			return value;
		}
		return value;
	}

	/**
	 * 获得BigDecimal
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal getBigDecimal(Object obj) {

		return BigDecimal.valueOf(getDouble(obj));
	}

	/**
	 * yes/no转换成true/false
	 * 
	 * @param aValue
	 * @return
	 */
	public static final String getBooleanFromYesNo(String aValue) {
		if (aValue.equalsIgnoreCase("yes")) {
			return "true";
		}

		return "false";
	}

	/**
	 * 替换字符串
	 * 
	 * @param text
	 *            原始文本
	 * @param replaced
	 *            要替换内容
	 * @param replacement
	 *            替换内容
	 * @return
	 */
	public static String replace(String text, String replaced,
			String replacement) {
		StringBuffer ret = new StringBuffer();
		String temp = text;

		while (temp.indexOf(replaced) > -1) {
			ret.append(temp.substring(0, temp.indexOf(replaced)) + replacement);
			temp = temp.substring(temp.indexOf(replaced) + replaced.length());
		}
		ret.append(temp);

		return ret.toString();
	}

	/**
	 * 删除不能成为文件名的字符
	 * 
	 * @param fileStr
	 * @return
	 */
	public static String removeIllegalFileChars(String fileStr) {
		String replacedFileStr = fileStr;

		replacedFileStr = replace(replacedFileStr, " ", "_");
		replacedFileStr = replace(replacedFileStr, "&", EMPTY_STRING);
		replacedFileStr = replace(replacedFileStr, "%", EMPTY_STRING);
		replacedFileStr = replace(replacedFileStr, ",", EMPTY_STRING);
		replacedFileStr = replace(replacedFileStr, ";", EMPTY_STRING);
		replacedFileStr = replace(replacedFileStr, "/", "_");

		return replacedFileStr;
	}

	/**
	 * 获得字符串中的指点子字符串
	 * 
	 * @param strIn
	 * @param start
	 * @param length
	 * @return
	 */
	public static String substring(String strIn, int start, int length) {
		String strOut = null;

		if (strIn != null) {
			strOut = EMPTY_STRING;

			if (start < strIn.length() && length > 0) {
				if (start + length < strIn.length()) {
					strOut = strIn.substring(start, start + length);
				} else {
					strOut = strIn.substring(start, strIn.length());
				}
			}
		}

		return strOut;
	}

	/**
	 * 随机生成length位数字字符串
	 * 
	 * @param length
	 * @return
	 */
	public static final String getRandomLengthDigit(int length) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length; i++) {
			str.append(IntChars[(int) (Math.random() * 10)]);
		}
		return str.toString();
	}

	/**
	 * 随即生成length位字符串
	 * 
	 * @param length
	 * @return
	 */
	public static final String getRandomLengthString(int length) {
		if (length < 1)
			return null;
		char ac[] = new char[length];
		for (int j = 0; j < ac.length; j++)
			ac[j] = chars[new Random().nextInt(71)];

		return new String(ac);
	}

	/**
	 * 随即生成i位中文
	 * 
	 * @param length
	 * @return
	 */
	public static final String getRandomLengthChineseString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = length; i > 0; i--) {
			sb.append(getRandomChinese());
		}
		return sb.toString();
	}

	/**
	 * 随机产生中文,长度范围为start-end
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getRandomLengthChiness(int start, int end) {
		StringBuilder sb = new StringBuilder();
		int length = new Random().nextInt(end + 1);
		if (length < start) {
			return getRandomLengthChiness(start, end);
		} else {
			for (int i = 0; i < length; i++) {
				sb.append(getRandomChinese());
			}
		}
		return sb.toString();
	}

	/**
	 * 随机获得中文
	 * 
	 * @return
	 */
	public static String getRandomChinese() {
		String str = null;
		int highPos, lowPos;
		Random random = new Random();
		;
		highPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = 161 + Math.abs(random.nextInt(93));
		byte[] b = new byte[2];
		b[0] = (new Integer(highPos)).byteValue();
		b[1] = (new Integer(lowPos)).byteValue();
		try {
			str = new String(b, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 删除所有空格
	 * 
	 * @param str
	 * @return
	 */
	public static String removeBlanks(String str) {
		if (str == null || str.equals(EMPTY_STRING))
			return str;
		char ac[] = str.toCharArray();
		char ac1[] = new char[ac.length];
		int i = 0;
		for (int j = 0; j < ac.length; j++)
			if (!Character.isSpaceChar(ac[j]))
				ac1[i++] = ac[j];

		return new String(ac1, 0, i);
	}

	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp = EMPTY_STRING;
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * 获得List的字符串连接
	 * 
	 * @param list
	 * @param split 可选连接符
	 * @return
	 */
	public static String getStringFromList(List<String> list, String... split) {
		if (list == null) {
			return EMPTY_STRING;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0, j = list.size(); i < j; i++) {
			sb.append(list.get(i));
			if (split != null && split.length > 0)
				sb.append(split[0]);
		}
		return sb.toString();
	}

	/**
	 * 连接对象
	 * 
	 * @param objs
	 * @return
	 */
	public static String concat(Object... objs) {

		if (objs == null || objs.length < 1)
			return EMPTY_STRING;
		StringBuffer sb = new StringBuffer();
		for (Object obj : objs) {
			sb.append(obj == null ? EMPTY_STRING : obj.toString());
		}
		return sb.toString();
	}

	/**
	 * 自由组合列表
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> combined(List<List<String>> list) {
		if (list.size() == 1) {
			return list.get(0);
		} else {
			if (list.size() == 2) {
				return combined(list.get(0), list.get(1));
			} else {
				List<String> tempList = new ArrayList<String>();
				for (int i = list.size(); i > 0; i--) {
					tempList = combined(list.get(i - 1), tempList);
				}
				return tempList;
			}
		}
	}

	/**
	 * 自由组合两列表
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String> combined(List<String> list1, List<String> list2) {
		if (list2.isEmpty())
			return list1;
		List<String> results = new ArrayList<String>();
		for (String str1 : list1) {
			for (String str2 : list2) {
				results.add(str1 + "," + str2);
			}
		}
		return results;
	}

	/**
	 * 正则替换
	 * 
	 * @param str
	 *            源字符串
	 * @param pattern
	 *            要替换的模式
	 * @param replace
	 *            替换成的内容
	 * @param connector
	 *            连接器
	 * @param def
	 *            默认值
	 * @return
	 */
	public static String regex(String str, String pattern, String replace,
			String connector, String def) {
		if (str == null || pattern == null || connector == null)
			return null;
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (i = 0; matcher.find(); i++) {
			if (i > 0)
				sb.append(connector);
			matcher.appendReplacement(sb, replace);
		}
		if (i > 0)
			matcher.appendTail(sb).append(def);
		else
			sb.append(str);
		return sb.toString();
	}

	/**
	 * 获得指定小数位
	 * 
	 * @param value
	 * @param fixed
	 * @return
	 */
	public static BigDecimal getFixedBigDecimal(String value, int fixed) {

		try {
			BigDecimal result = new BigDecimal(value);
			return result.setScale(fixed, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {

		}
		return BigDecimal.ZERO;
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirstChar(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuilder(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * 首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFirstChar(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuilder(strLen)
				.append(Character.toLowerCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * 是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj){
		if (null == obj) {
			return true;
		}
		if (obj instanceof Integer) {
			return 0 == (int) obj;
			
		}else if (obj instanceof Date) {
			return ((Date) obj).getTime() == 0;
			
		}else if (obj instanceof Long) {
			return 0 == (long) obj;
			
		}else if (obj instanceof String) {
			return 0 == ((String)obj).length();
			
		}else if (obj instanceof Boolean) {
			return !((boolean) obj);
			
		}else if (obj instanceof byte[]) {
			return 0 == ((byte[]) obj).length;
			
		}else if (obj instanceof Double) {
			return 0 == (double) obj;
			
		}else if (obj instanceof Float) {
			return 0 == (float) obj;
			
		}else if (obj instanceof Short) {
			return 0 == (short) obj;
			
		}else if (obj instanceof Time) {
			return 0 == ((Time) obj).getTime();
			
		}
		return false;
	}
	public static boolean isNullExceptNum(Object obj){
		if (null == obj) {
			return true;
		}
		if (obj instanceof Date) {
			return ((Date) obj).getTime() == 0;

		}else if (obj instanceof String) {
			return 0 == ((String)obj).length();

		}else if (obj instanceof Boolean) {
			return !((boolean) obj);

		}else if (obj instanceof byte[]) {
			return 0 == ((byte[]) obj).length;

		}else if (obj instanceof Time) {
			return 0 == ((Time) obj).getTime();

		}
		return false;
	}

	
	/**
	 * 检查字符串是否为空
	 * 
	 * @param str 字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0||"null".equals(str);
	}

	/**
	 * 检查字符串是否为空
	 * 
	 * @param str 字符串
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 去除字符串首尾的空格
	 * 
	 * @param sSrc String：源串
	 * @return String：去除空格后的结果串
	 */
	public static final String trim(String sSrc) {
		int i;
		int j;
		// 去除尾部空格
		for (i = sSrc.length() - 1; i >= 0; i--) {
			if (sSrc.charAt(i) != ' ') {
				break;
			}
		}
		if (i < 0) {
			return "";
		}
		// 去除开头空格
		for (j = 0; j < i; j++) {
			if (sSrc.charAt(j) != ' ') {
				break;
			}
		}
		return sSrc.substring(j, i + 1);// 返回从j到i的字符
	}


	/**
	 * 将字符串值转换成指定类型的对象
	 * 
	 * @param value 字符串值
	 * @param type 指定类型
	 * @return 类型的对象
	 * @throws ParseException 时间转换异常
	 */
	public static Object toObject(String value, String type) throws ParseException {
		Object reValue = value;
		if (isNotEmpty(value)) {
			if ("Double".equalsIgnoreCase(type)) {
				reValue = Double.valueOf(value);
			} else if ("Float".equalsIgnoreCase(type)) {
				reValue = Float.valueOf(value);
			} else if ("Long".equalsIgnoreCase(type)) {
				reValue = Long.valueOf(value);
			} else if ("Integer".equalsIgnoreCase(type)) {
				reValue = Integer.valueOf(value);
			} else if ("Boolean".equalsIgnoreCase(type)) {
				reValue = Boolean.valueOf(value);
			} else if ("BigDecimal".equalsIgnoreCase(type)) {
				reValue = new BigDecimal(value);
			} else if ("Date".equalsIgnoreCase(type)) {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				reValue = sf.parse(value);
			}
		}
		return reValue;
	}

	/**
	 * 将小于长度的字符串格式化为（len）位（左补位），大于给定长度的原样返回
	 * 
	 * @param str 原字符串
	 * @param len 长度
	 * @param c 左补字符
	 * @return 格式化后的字符串
	 * @author wxy
	 * @date 2014-11-24
	 */
	public static String format(String str, int len, char c) {
		String tmpString = str;
		if (tmpString == null || tmpString.length() == 0) {
			tmpString = "";
		}
		if (tmpString.length() >= len) {
			return tmpString;
		}
		char arr[] = new char[len - tmpString.length()];
		Arrays.fill(arr, 0, (len - tmpString.length()), c);
		return String.valueOf(arr) + tmpString;
	}

	/**
	 *	计算报文长度（不包括8位数字本身，不足8位）
	 *
	 */
	public static String countXmlLength(String xml){
		if (xml == null || xml.length() == 0) {
			xml = "00000000";
		}
		Pattern p = Pattern.compile(">(\\s*|\n|\t|\r)<");
		Matcher m = p.matcher(xml);
		xml = m.replaceAll("><").trim();
		return StringUtil.format(StringUtil.getString(xml.length()), 8, '0');
	}

	/**
	 * 
	 * 功能说明：检查字符串是否包含比对字符集，如果包含其中一个就返回true，否则返回false
	 * 
	 * @param str String 被检查字符串
	 * @param ary String[] 比对字符集
	 * @return boolean
	 */
	public static boolean oneOf(String str, String[] ary, boolean flag) {
		for (int i = 0; i < ary.length; i++) {
			if (flag) {
				if (str.equalsIgnoreCase(ary[i]))
					return true;
			} else {
				if (str.indexOf(ary[i]) > -1)
					return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 功能说明：检查字符串是否包含比对字符集，如果包含其中一个就返回true，否则返回false
	 * 
	 * @param str String 被检查字符串
	 * @param arys String 比对字符集(多个字符串用英文逗号间隔)
	 * @return boolean
	 */
	public static boolean oneOf(String str, String arys, boolean flag) {
		if ((arys == null || arys.trim().equalsIgnoreCase("")) && str != null
				&& !str.trim().equalsIgnoreCase(""))
			return false;// 比对字符集为空直接返回false
		return oneOf(str, arys.split(","), flag);
	}

	/**
	 * 
	 * 功能说明：检查字符串是否包含比对字符集，如果包含其中一个就返回true，否则返回false
	 * 
	 * @param str String 被检查字符串
	 * @param arys String 比对字符集(多个字符串用英文逗号间隔)
	 * @return boolean
	 */
	public static boolean oneOf(String str, String arys) {
		if ((arys == null || arys.trim().equalsIgnoreCase("")) && str != null
				&& !str.trim().equalsIgnoreCase(""))
			return false;// 比对字符集为空直接返回false
		return oneOf(str, arys.split(","), false);
	}

	/**
	 * 数据库Clob对象转换为String
	 */
	public static String clobToString(Clob clob) {
		try {
			Reader inStreamDoc = clob.getCharacterStream();

			char[] tempDoc = new char[(int) clob.length()];
			inStreamDoc.read(tempDoc);
			inStreamDoc.close();
			
			return new String(tempDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException es) {
			es.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断字符串是否由纯数字组成
	 * @param str
	 * @return
	 */
	public static  boolean isNumber(String str) {
		if(null !=str && !"".equals(str.trim()))  {
			return str.matches("^[0-9]*$");
		}
		return false;
	}
}
