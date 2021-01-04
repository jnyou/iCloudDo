package org.jnyou.anoteinventoryservice.tools.vaild;


import java.util.Date;
import java.util.regex.Pattern;


/**
 * @author jnyou
 */
public class Validates {
	private Validates(){}
	public static Boolean isMobile(String phone){
		if(phone==null){
			return false;
		}else{
			return Pattern.compile("^[1][0-9]{10}$").matcher(phone).matches();
		}
		
	}
	public static boolean isNumeric(String str){
		if(str.length() == 1&&str.charAt(0) == 48){
			return false;
		}
		for(int i=str.length();--i>=0;){
			int chr=str.charAt(i);
			if(chr<48 || chr>57)
				return false;
		}
		return true;
	}

	//金额 最多两位小数
	public static boolean isAmount(String decimal) {
		if (null == decimal || "".equals(decimal))
			return false;
		String regex = "^[0-9]+(.[0-9]{1,2})?$";
		try{
			if (!decimal.matches(regex) || Double.parseDouble(decimal) > 1000000000 || Double.parseDouble(decimal) < 0){
				return false;
			}
		}catch (NumberFormatException e){
			return false;
		}

		return true;
	}

	//金额 最多两位小数
	public static boolean isAmountZero(String decimal) {
		if (null == decimal || "".equals(decimal))
			return false;
		String regex = "^[0-9]+(.[0-9]{1,2})?$";
		if (!decimal.matches(regex) || Double.parseDouble(decimal) > 1000000000 || Double.parseDouble(decimal) < 0){
			return false;
		}
		return true;
	}

	//数字
	public static boolean isNumber(String number) {
		if (null == number || "".equals(number))
			return false;
		String regex = "^[0-9]+$";
		if (!number.matches(regex)){
			return false;
		}
		return true;
	}

	//身份证号
	public static boolean isCardId(String cardId) {
		if (null == cardId || "".equals(cardId))
			return false;
		cardId=cardId.trim();
		if("".equals(cardId))
			return false;
		String regex = "^([0-9]{15})|([0-9]{17}[0-9A-Za-z]{1})$";
		if (!cardId.matches(regex)){
			return false;
		}
		cardId = cardId.toUpperCase();//小写变大写
		if(cardId.length()==18){
			int X=0;
			Integer[] a={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
			String[] b={"1","0","X","9","8","7","6","5","4","3","2"};
			for(int i=0;i<cardId.length()-1;i++){
				String currChar=cardId.substring(i,i+1);
				Integer currInt=Integer.valueOf(currChar);
				X=X+(currInt*a[i]);
			}
			Integer Y = X % 11;
			String lastChar=cardId.substring(17,18);
			if(!lastChar.equals(b[Y])){
				return false;
			}
		}
		return true;
	}
	//台胞证
	public static boolean isTaiBao(String cardId) {
		if (null == cardId || "".equals(cardId))
			return false;
		String regex = "^[0-9]{8}$";
		if (!cardId.matches(regex)){
			return false;
		}
		return true;
	}

	//银行卡号
	public static boolean isBankNo(String bankNo) {
		if (null == bankNo || "".equals(bankNo))
			return false;
		if (!checkBankCard(bankNo)){
			return false;
		}
		return true;
	}

	//企业银行卡号
	public static boolean isEntBankNo(String bankNo) {
		if (null == bankNo || "".equals(bankNo))
			return false;
		String regex = "^[0-9]{6,30}$";
		if (!bankNo.matches(regex)){
			return false;
		}
		return true;
	}

	/**
	 * 校验银行卡卡号
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
//			throw new IllegalArgumentException("银行卡号必须为数字!");
			throw new RuntimeException("银行卡号必须为数字!");
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if(j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
	}
	/**
	 * 校验字符串非空
	 * @param str 校验字符
	 * @param type 提示信息
	 * @return
	 */
	public static String notNull(String str, String type) throws RuntimeException{
		return notNull(str, type , 50);//默认长度限制50
	}
	/**
	 * 校验字符串非空
	 * @param str 校验字符
	 * @param type 提示信息
	 * @param length 长度限制
	 * @return
	 */
	public static String notNull(String str, String type, Integer length) throws RuntimeException{
		if(str == null|| "".equals(str)){
			throw new RuntimeException(type +"不能为空 !");
		}else if(str.length()>length){
			throw new RuntimeException(type +"字符长度超出限制!");
		}
		return str;
	}
	/**
	 * 校验字符串非空
	 * @param str 校验字符
	 * @param type 提示信息
	 * @return
	 */
	public static Object notEmpty(Object str, String type) throws RuntimeException{
		if(str == null){
			throw new RuntimeException(type +"不能为空 !");
		}
		return str;
	}
	/**
	 * 校验字符串非空
	 * @param str 校验字符
	 * @param type 提示信息
	 * @return
	 */
	public static Double getDouble(Double str, String type) throws RuntimeException{
		if(str == null){
			throw new RuntimeException(type +"不能为空 !");
		}
		return str;
	}
	/**
	 * 校验字符串非空
	 * @param str 校验字符
	 * @param type 提示信息
	 * @return
	 */
	public static Integer getInteger(Integer str, String type) throws RuntimeException{
		if(str == null){
			throw new RuntimeException(type +"不能为空 !");
		}
		return str;
	}
	/**
	 * 校验字符串非空
	 * @param str 校验字符
	 * @return
	 */
	public static Double getAmount(String str, String type) throws RuntimeException{
		if(!isAmount(str)){
			throw new RuntimeException(type + "金额格式不合法 !");
		}
		return Double.parseDouble(str);
	}
	/**
	 * 校验字符串非空
	 * @param str 校验字符
	 * @return
	 */
	public static Date getDate(Date str, String type) throws RuntimeException{
		if(str == null ){
			throw new RuntimeException(type + "不为空 !");
		}
		return str;
	}
	/**
	 * 获取非空字符串
	 * @param str 校验字符
	 * @return
	 */
	public static String getDefault(String str){
		if(str == null || "".equals(str)){
			return "default";
		}
		return str;
	}
	/**
	 * 获取非空字符串
	 * @param str 校验字符
	 * @param def  默认字符串
	 * @return
	 */
	public static String getDefault(String str, String def){
		if(str == null || "".equals(str)){
			return def;
		}
		return str;
	}

	/**
	 * 校验请求流水号, 只能是数字、字母、下划线
	 * @param Str
	 * @return
	 */
	public static boolean checkRequestNo(String Str){
		String regex = "^[0-9a-zA-Z_-]+$";
		if (!Str.matches(regex)){
			return false;
		}
		return true;
	}

	/**
	 *  校验网址
	 * @param str 校验字符
	 * @return
	 */
	public static boolean checkUrl(String str){
		String url = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
		if(str.matches(url)){
			return true;
		}
		return false;
	}

	public static void main(String[] args){

	}
}
