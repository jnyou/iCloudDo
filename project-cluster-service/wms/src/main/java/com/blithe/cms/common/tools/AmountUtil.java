package com.blithe.cms.common.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 
 */
public class AmountUtil {

	private static final int DEF_DIV_SCALE = 16; // 使用double默认精度和vb相同，应该能满足要求
	private static final Double MILLION = 10000.0;
	private static final Double MILLIONS = 1000000.0;
	private static final Double BILLION = 100000000.0;

	/**
	 * 功能描述：人民币转成大写
	 * @param value 需要转换的金额
	 * @return String 转换后的字符串
	 */
	public static String rmbToBigString(BigDecimal value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] decimal = { '角', '分', '厘', '毫', '丝' };
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		String valStr = value.toPlainString(); // 转化成字符串
		String[] valueArr = valStr.split("\\.");
		String head = ""; // 整数部分
		String rail = "00"; // 小数部分
		if (valueArr.length > 1) {
			head = valueArr[0];
			rail = valueArr[1];
		} else {
			head = valueArr[0];
		}
		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果

		// 处理小数部分（如果有）
		if (!rail.equals("00")) {
			int railZeroCount = 0;
			char[] chRail = rail.toCharArray();
			for (int i = 0; i < chRail.length; i++) {
				if (chRail[i] == '0') {
					railZeroCount++;
				} else {
					if (railZeroCount > 0) {
						suffix += digit[0];
					}
					railZeroCount = 0;
					suffix += digit[chRail[i] - '0'];
					suffix += decimal[i];
				}
			}
		}

		if (!"0".equals(head)) {
			// 处理小数点前面的数
			char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
			byte zeroSerNum = 0; // 连续出现0的次数
			for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
				int idx = (chDig.length - i - 1) % Integer.parseInt("4"); // 取段内位置
				int vidx = (chDig.length - i - 1) / Integer.parseInt("4"); // 取段位置
				if (chDig[i] == '0') { // 如果当前字符是0
					zeroSerNum++; // 连续0次数递增
				} else {
					if (zeroSerNum > 0) {
						prefix += digit[0];
					}
					zeroSerNum = 0;
					prefix += digit[chDig[i] - '0'];
					if (idx > 0) {
						prefix += hunit[idx - 1];
					}
				}

				if (idx == 0 && zeroSerNum < Integer.parseInt("4")) {
					if (vidx > 0) {
						prefix += vunit[vidx - 1];
					}
				}
			}
			prefix += "元";
		}

		String resAIW = prefix + suffix;

		// 处理结果
		if (resAIW.equals("")) { // 零元
			resAIW = "零" + "元";
		}
		if (suffix.equals("")) { // ...元整
			resAIW += "整";
		}
		return resAIW; // 返回正确表示
	}

	/**
	 * 数字格式化
	 * 
	 * @param number 数字
	 * @param format 格式
	 * @return
	 */
	public static final String formatNumber(BigDecimal number, String format) {
		int decs = 0;// 要保留的小数位数
		String format1 = format;
		if (format1.trim().length() == 0) {
			format1 = "#0.00";
		}
		int i = format1.indexOf(".");
		// 先按照format中指定的小数位数对number进行round，再格式化
		if (i >= 0) {
			for (decs = ++i; decs < format1.length(); decs++) {
				if (format1.charAt(decs) != '0' && format1.charAt(decs) != '#') {
					break;
				}
			}
			decs -= i;
		}
		if (format1.indexOf("%") >= 0) {
			decs += Integer.parseInt("2");// 百分比
		}

		return (new DecimalFormat(format1)).format(round(number, decs));
	}


	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v 需要四舍五入的数字
	 * @param lDecs 小数点后保留几位
	 * @param bTrunc 是否四舍五入
	 * @return 四舍五入后的结果
	 */
	public static final BigDecimal round(BigDecimal v, int lDecs, boolean bTrunc) {
		if (lDecs < 0) {
			return v;
		}
		BigDecimal one = new BigDecimal("1");
		return v.divide(one, lDecs, bTrunc ? BigDecimal.ROUND_DOWN : BigDecimal.ROUND_HALF_UP);
	}
	
	public static final Double round(Double v, int lDecs, boolean bTrunc) {
		return round(BigDecimal.valueOf(v), lDecs, bTrunc).doubleValue();
	}

	/**
	 * 
	 * 保留小数点后多少位
	 * 
	 * @param v 数值
	 * @param lDecs 小数点位数
	 * @return double
	 * @author yaojie
	 * @date 2014-11-27
	 */
	public static final BigDecimal round(BigDecimal v, int lDecs) {
		
		return round(v, lDecs, false);
	}
	
	public static final Double round(Double v, int lDecs) {
		return round(BigDecimal.valueOf(v), lDecs).doubleValue();
	}

	/**
	 * 功能说明：提供精确的加法运算(此方法为底层调用方法)。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @param v3 加数
	 * @param n 参数个数
	 * @return n个参数的和
	 */
	private static final BigDecimal add(BigDecimal v1, BigDecimal v2, BigDecimal v3, int n) {
		
		switch (n) {
			case 2:
				return v1.add(v2);
			case 3:
				return v1.add(v2).add(v3);
			default:
				return BigDecimal.ZERO;
		}
	}
	
	/**
	 * 功能说明：提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static final BigDecimal add(BigDecimal v1, BigDecimal v2) {
		
		return add(v1, v2, new BigDecimal(0), 2);
	}
	
	public static final Double add(Double v1, Double v2) {
		return add(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 功能说明：提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @param v3 加数
	 * @return 三个参数的和
	 */
	public static final BigDecimal add(BigDecimal v1, BigDecimal v2, BigDecimal v3) {
		
		return add(v1, v2, v3, 3);
	}
	
	public static final Double add(Double v1, Double v2, Double v3) {
		return add(new BigDecimal(v1), new BigDecimal(v2), new BigDecimal(v3)).doubleValue();
	}

	/**
	 * 功能说明：提供精确的减法运算。
	 * 
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static final BigDecimal sub(BigDecimal v1, BigDecimal v2) {
		
		return add(v1, mul(v2, new BigDecimal(-1)), new BigDecimal(0), 2);
	}
	
	public static final Double sub(Double v1, Double v2) {
		return sub(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 提供精确的乘法运算.
	 */
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
		return adjustDouble(v1.multiply(v2));
	}
	
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2, BigDecimal v3) {
		return mul(mul(v1, v2), v3);
	}
	
	public static Double mul(Double v1, Double v2) {
		return mul(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}
	
	public static Double mul(Double v1, Double v2, Double v3) {
		return mul(mul(v1, v2), v3).doubleValue();
	}

	/**
	 * 提供精确的乘法运算，并对运算结果截位.
	 * 
	 * @param scale 运算结果小数后精确的位数
	 */
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		return round(adjustDouble(v1.multiply(v2)), scale);
	}
	
	public static Double mul(Double v1, Double v2, int scale) {
		return mul(new BigDecimal(v1), new BigDecimal(v2), scale).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算.
	 * 
	 * @see #div(double, double, int)
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}
	
	public static Double div(Double v1, Double v2) {
		return div(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算. 由scale参数指定精度，以后的数字四舍五入.
	 * 
	 * @param v1  被除数
	 * @param v2  除数
	 * @param scale 表示表示需要精确到小数点以后几位
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static Double div(Double v1, Double v2, int scale) {
		return div(new BigDecimal(v1), new BigDecimal(v2), scale).doubleValue();
	}

	/**
	 * 功能说明：Math.pow的结果最多可以有17位有效数字，本函数控制最多15位，和VB的double兼容
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static final BigDecimal pow(BigDecimal v1, BigDecimal v2) {
		return adjustDouble(new BigDecimal(Math.pow(v1.doubleValue(), v2.doubleValue())));
	}
	
	public static final Double pow(Double v1, Double v2) {
		return pow(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 功能说明：把java产生的double值转换成15位最多有效数字的值，多于小数四舍五入，整数不处理
	 * 
	 * @param v1
	 * @return
	 */
	public static final BigDecimal adjustDouble(BigDecimal v1) {
		// E多少的问题还有div也可能存在问题

		String strRes = String.valueOf(v1).toUpperCase();
		int i, len = strRes.length();
		int e = strRes.lastIndexOf('E'); // 考虑科学记数法!
		int d = strRes.indexOf('.');

		if (len <= DEF_DIV_SCALE || d < 0)
			return v1;

		for (i = 0; i < len; i++)
			if (strRes.charAt(i) >= '1' && strRes.charAt(i) <= '9')
				break;
		i = len - (d > i ? i + 1 : i) - (e >= 0 ? len - e : 0);// 得出有效位数
		if (i >= DEF_DIV_SCALE) {
			if (e < 0)
				v1 = round(v1, len - d - 2 - i + DEF_DIV_SCALE);
			else
				// 带科学记数法的处理: 先处理E前面的部分，再附加E部分，再转换成double
				v1 = round(v1, e - d - 1 - Integer.parseInt(strRes.substring(e + 1))
						- (i - DEF_DIV_SCALE));
		}

		return v1;
	}
	
	/**
	 * 移动加权法计算卖出成本
	 * 
	 * @param dKcMoney	持仓成本
	 * @param dKcAmount	持仓数量
	 * @param dSaleAmount 卖出数量
	 * @param iCb8ws
	 * @return
	 */
	public static double curOutPrice( double dKcMoney,double dKcAmount,double dSaleAmount,String iCb8ws)  {
		
	      double dSalecb = 0;
	      if (dKcAmount > dSaleAmount) {
	         if (iCb8ws.length() != 0 && !iCb8ws.equalsIgnoreCase("0")) {
	            dSalecb = AmountUtil.mul(AmountUtil.round(AmountUtil.div(dKcMoney, dKcAmount), Integer.parseInt(iCb8ws)), dSaleAmount,2);
	         }
	         else {
	            dSalecb = AmountUtil.mul(AmountUtil.div(dKcMoney,dKcAmount),dSaleAmount,2);
	         }
	      }
	      else {
	         //库存数量要么大于卖出数量，要么相等 相等时卖出成本＝库存金额
	         dSalecb = dKcMoney;
	      }
	      return dSalecb;
	}

	/**
	 *  生成带两位小数点的千分位金额
	 * @param value
	 * @return
	 */
	public static final String thousands(String value){
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(Double.valueOf(value));
	}
	/**
	 *  生成带三位小数点的千分位金额
	 * @param value
	 * @return
	 */
	public static final String thousandsThree(String value){
		DecimalFormat df = new DecimalFormat("#,##0.000");
		return df.format(Double.valueOf(value));
	}

	/**
	 *  生成带四位位小数点的千分位金额
	 * @param value
	 * @return
	 */
	public static final String thousandsFour(String value){
		DecimalFormat df = new DecimalFormat("#,##0.0000");
		return df.format(Double.valueOf(value));
	}

	/**
	 * 将数字转换成以万为单位和以亿为单位
	 * @param amount 金额
	 * @param unit MILLIONS万单位 BILLION亿单位
	 * @return
	 */
	public static double amountConversion(double amount,String unit){
		//最终返回的结果值
		double result = amount;
		//四舍五入后的值
		double value = 0;
		//转换后的值
		double tempValue = 0;
		//余数
		double remainder = 0;
		//金额大于1百万小于1亿
		if("MILLIONS".equals(unit)){
			tempValue = amount/MILLION;
			remainder = amount%MILLION;
			result = new BigDecimal(tempValue).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		}else if("BILLION".equals(unit)){
			tempValue = amount/BILLION;
			remainder = amount%BILLION;

			//余数小于50000000则不进行四舍五入
			if(remainder < (BILLION/2)){
				value = round(tempValue,2,true);
			}else{
				value = round(tempValue,2,false);
			}
			result = Double.valueOf(value);
		}
		return result;
	}

	public static void main(String[] args) {
//		System.out.println(amountConversion(10000000,"MILLIONS"));
//
//		System.out.println(AmountUtil.round(148.1680000000,2));
		NumberFormat nf = NumberFormat.getInstance();
		System.out.println(nf.format(30.2000000000000000));
	}
}
