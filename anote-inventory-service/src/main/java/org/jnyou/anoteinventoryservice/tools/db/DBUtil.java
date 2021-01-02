package org.jnyou.anoteinventoryservice.tools.db;

import org.jnyou.anoteinventoryservice.tools.date.DateUtils;

import java.util.Date;

/**
 * 数据库转换函数类
 */
public class DBUtil {

	final public static int DB_SQL = 0;
	final public static int DB_ORA = 1;
	final public static int DB_DB2 = 2;
	final public static int DB_MSL = 3; // mysql

	final static String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";

	private static int dbType = 3;

	public static void setDbType(int type) {

		switch (type) {
		case 1:
			DBUtil.dbType = DB_ORA;
			break;
		case 3:
			DBUtil.dbType = DB_MSL;
			break;
		default:
			break;
		}
	}

	/**
	 * instr方法函数
	 * 
	 * @param str1
	 * @param str2
	 * @param iStart
	 * @return
	 */
	public static String sqlInstr(String str1, String str2, int iStart) {
		switch (dbType) {
		case DB_ORA:
			return "instr(" + str1 + "," + str2
					+ (iStart == 0 ? "" : ("," + iStart)) + ")";
		case DB_SQL:
			return "charindex(" + str2 + "," + str1
					+ (iStart == 0 ? "" : ("," + iStart)) + ")";
		case DB_DB2:
			return "locate(" + str2 + "," + str1
					+ (iStart == 0 ? "" : ("," + iStart)) + ")";
		case DB_MSL:
			return "instr(" + str1 + "," + str2 + ")";
		default:
			return "";
		}
	}

	public static String sqlInstr(String sStr1, String sStr2) {
		return sqlInstr(sStr1, sStr2, 0);
	}

	/**
	 * substring 方法函数
	 * 
	 * @param str
	 * @param iStart
	 * @param iLen
	 * @return
	 */
	public static String sqlSubStr(String str, String iStart, String iLen) {
		iStart = "0".equals(iStart) ? "1" : iStart;
		switch (dbType) {
		case DB_SQL:
		case DB_MSL:
			return "substring(" + str + "," + iStart + "," + iLen + ")";
		default:
			return "substr(" + str + "," + iStart + "," + iLen + ")";
		}
	}

	/**
	 * substring 方法函数
	 * 
	 * @param str
	 * @param iStart
	 * @param iLen
	 * @return
	 */
	public static String sqlSubStr(String str, String iStart) {
		switch (dbType) {
		case DB_SQL:
		case DB_MSL:
			return "substring(" + str + "," + iStart + ")";
		default:
			return "substr(" + str + "," + iStart + ")";
		}
	}

	/**
	 * substring 方法函数
	 * 
	 * @param str
	 * @param iStart
	 * @param iLen
	 * @return
	 */
	public static String sqlSubStr(String str, int iStart, int iLen) {
		return sqlSubStr(str, String.valueOf(iStart), String.valueOf(iLen));
	}

	/**
	 * substring 方法函数
	 * 
	 * @param str
	 * @param iStart
	 * @param iLen
	 * @return
	 */
	public static String sqlSubStr(String str, int iStart) {
		return sqlSubStr(str, String.valueOf(iStart));
	}

	/** left，第二个参数也可用整数 */
	public static String sqlLeft(String sStr, String sLen) {
		switch (dbType) {
		case DB_ORA:
			return "SubStr(" + sStr + ",1," + sLen + ")";
		case DB_MSL:
			return "substring(" + sStr + ",1," + sLen + ")";
		default:
			return "LEFT(" + sStr + "," + sLen + ")";
		}
	}

	/**
	 * left 方法函数
	 * 
	 * @param str
	 * @param iLen
	 * @return
	 */
	public static String sqlLeft(String str, int iLen) {
		switch (dbType) {
		case DB_ORA:
			return "substr(" + str + ",1," + iLen + ")";
		case DB_MSL:
			return "substring(" + str + ",1," + iLen + ")";
		default:
			return "left(" + str + "," + iLen + ")";
		}
	}

	/**
	 * right 方法函数
	 * 
	 * @param str
	 * @param iLen
	 * @return
	 */
	public static String sqlRight(String str, int iLen) {
		switch (dbType) {
		case DB_ORA:
			return "DECODE(" + iLen + ",0,'',SUBSTR(" + str + ",-(" + iLen
					+ ")))";
		case DB_MSL:
			return "substring(" + str + ",-" + iLen + ")";
		default:
			return "right(" + str + "," + iLen + ")";
		}
	}

	/** right，第二个参数也可用整数 */
	public static String sqlRight(String sStr, String sLen) {

		if (dbType == DB_ORA)
			return "decode(" + sLen + ",0,'',substr(" + sStr + ",-(" + sLen
					+ ")))";
		if (dbType == DB_MSL)
			return "substring(" + sStr + ",-" + sLen + ")";
		return "right(" + sStr + "," + sLen + ")";
	}

	/**
	 * len 方法函数
	 * 
	 * @param str
	 * @return
	 */
	public static String sqlLen(String str) {
		switch (dbType) {
		case DB_SQL:
			return "len(" + str + ")";
		case DB_MSL:
			return "char_length(" + str + ")";
		default:
			return "length(" + str + ")";
		}
	}

	/**
	 * 返回sql语句中的有效日期
	 * 
	 * @param sDate
	 * @return
	 */
	public static String sqlDate(String sDate) {
		return sqlDate(sDate, dbType, false);
	}

	/**
	 * 返回sql语句中的有效日期
	 * 
	 * @param dDate
	 * @return
	 */
	public static String sqlDate(Date dDate) {
		return sqlDate(dDate, dbType, false);
	}

	/**
	 * 返回sql语句中的有效日期 btime=true也保存时间
	 *
	 * @param sDate
	 * @param sFormat
	 * @return
	 */
	public static String sqlToDate(String sDate, boolean bTime) {
		return sqlDate(sDate, dbType, bTime);
	}

	public static String sqlToDate(Date dDate, boolean bTime) {
		return sqlDate(dDate, dbType, bTime);
	}

	private static String sqlDate(String sDate, int dbtype, boolean bTime) {
		if (dbtype == DB_ORA)
			return ("to_date('" + sDate + "','" + PATTERN_yyyy_MM_dd
					+ (bTime ? "hh24:mi:ss" : "") + "')");
		if (dbtype == DB_SQL)
			return "'" + sDate + "'";
		if (dbtype == DB_DB2)
			return (bTime ? "TIMESTAMP" : "DATE") + "('" + sDate + "')";
		if (dbtype == DBUtil.DB_MSL)
			return "str_to_date('" + sDate + "',"
					+ (bTime ? "'%Y-%m-%d %T'" : "'%Y-%m-%d'") + ")";
		return "";
	}

	/** 日期类型参数的sqlDate...btime=true也保存时间 */
	public static String sqlDate(Date dDate, int dbtype, boolean bTime) {

		String dformat = PATTERN_yyyy_MM_dd + (bTime ? " HH:mm:ss" : "");
		String sDate = DateUtils.format(dDate, dformat);
		return sqlDate(sDate, dbtype, bTime);

	}

	/**
	 * 转换日期为字符串
	 * 
	 * @param str
	 * @param sFormat
	 * @return
	 */
	public static String sqlDateToChar(String str, String sFormat) {
		switch (dbType) {
		case DB_ORA:
			return "f(" + str + ",'" + sFormat + "')";
		case DB_SQL:
			return "case(" + str + " as datetime)";
		case DB_DB2:
			if ("yyyymm".equals(sFormat)) {
				return "char(replace(char(" + str + ",iso),'-',''),6)";
			} else {
				return (sFormat.indexOf(':') > 0 ? "TIMESTAMP" : "DATE")
						+ "("
						+ (sFormat.indexOf('/') > 0 ? "replace(" + str
								+ ",'/','-')" : str) + ")";
			}
		case DB_MSL:
			return "date_format(" + str + ",'" + sFormat + "')";
		default:
			return str;
		}
	}

	public static String sqlDateToChar(String str) {
		return sqlDateToChar(str, PATTERN_yyyy_MM_dd);
	}

	/**
	 * 日期计算
	 * 
	 * @param sDate
	 * @param iDay
	 * @param dateType
	 *            <P>
	 *            TIP:在预编译的sql中调用，只能对目标字段做运算
	 *            </P>
	 * @return
	 */
	public static String sqlAddDate(String sDate, int iDay, String dateType) {
		switch (dbType) {
		case DB_DB2:
			if (dateType.equalsIgnoreCase("year")) {
				return " " + sDate + " + " + iDay + " years ";
			} else if (dateType.equalsIgnoreCase("month")) {
				return " " + sDate + " + " + iDay + " months ";
			} else if (dateType.equalsIgnoreCase("day")) {
				return " " + sDate + " + " + iDay + " days ";
			}
		case DB_SQL:
			return " dateadd(" + dateType + "," + iDay + "," + sDate + " ) ";
		case DB_ORA:
			if (dateType.equalsIgnoreCase("day")) {
				return " " + sDate + " + " + iDay + " ";
			} else if (dateType.equalsIgnoreCase("month")) {
				return "add_months(" + sDate + "," + iDay + ")";
			} else if (dateType.equalsIgnoreCase("year")) {
				return "add_months(" + sDate + "," + iDay + "*12)";
			}
		case DB_MSL:
			if (dateType.equalsIgnoreCase("day")) {
				return "date_add(" + sDate + ", interval " + iDay + " day)";
			} else if (dateType.equalsIgnoreCase("month")) {
				return "date_add(" + sDate + ", interval " + iDay + " month)";
			} else if (dateType.equalsIgnoreCase("year")) {
				return "date_add(" + sDate + ", interval " + iDay + " year)";
			}
		default:
			return "";
		}
	}

	/**
	 * 日期相减
	 * 
	 * @param sDate1
	 * @param sDate2
	 * @param dateType
	 * @return
	 */
	public static String sqlDateDiff(String sDate1, String sDate2,
			String dateType) {
		switch (dbType) {
		case DB_DB2:
			if (dateType.equalsIgnoreCase("day")) {
				return "days(" + sDate2 + ")-days(" + sDate1 + ")";
			} else if (dateType.equalsIgnoreCase("month")) {
				return "months(" + sDate2 + ")-months(" + sDate1 + ")";
			} else if (dateType.equalsIgnoreCase("year")) {
				return "years(" + sDate2 + ")-years(" + sDate1 + ")";
			}
		case DB_SQL:
			if (dateType.equalsIgnoreCase("day")) {
				return "datediff(day," + sDate1 + "," + sDate2 + ")";
			} else if (dateType.equalsIgnoreCase("month")) {
				return "datediff(month," + sDate1 + "," + sDate2 + ")";
			} else if (dateType.equalsIgnoreCase("year")) {
				return "datediff(year," + sDate1 + "," + sDate2 + ")";
			}
		case DB_ORA:
			return "to_date('" + sDate2 + "','yyyy-mm-dd') - to_date('"
					+ sDate1 + "','yyyy-mm-dd')";
		case DB_MSL:
			return "datediff(" + sDate2 + "," + sDate1 + ")";
		default:
			return sDate2 + "-" + sDate1;
		}
	}

	/**
	 * year 函数
	 * 
	 * @param sDate
	 * @return
	 */
	public static String sqlYear(String sDate) {
		switch (dbType) {
		case DB_ORA:
			return "to_number(to_char(" + sDate + "," + "'yyyy'))";
		default:
			return "YEAR(" + sDate + ")";
		}
	}

	/**
	 * month 函数
	 * 
	 * @param sDate
	 * @return
	 */
	public static String sqlMonth(String sDate) {
		switch (dbType) {
		case DB_ORA:
			return "to_number(to_char(" + sDate + "," + "'mm'))";
		default:
			return "month(" + sDate + ")";
		}
	}

	/**
	 * day 函数
	 * 
	 * @param sDate
	 * @return
	 */
	public static String sqlDay(String sDate) {
		switch (dbType) {
		case DB_ORA:
			return "to_number(to_char(" + sDate + "," + "'dd'))";
		default:
			return "day(" + sDate + ")";
		}
	}

	/**
	 * 字符串转换成数字函数
	 * 
	 * @param sStr1
	 *            字符串
	 * @return
	 */
	public static String sqlNumber(String sStr1) {
		switch (dbType) {
		case DB_ORA:
			return "to_number(" + sStr1 + ")";
		case DB_MSL:
			return "cast(" + sStr1 + " as Decimal(18,2))";
		default:
			return "cast(" + sStr1 + " as numeric(18,2))";
		}

	}

	/**
	 * 字符串转换成数字函数
	 * 
	 * @param sStr1
	 *            字符串
	 * @param len
	 *            --要转换数据Number的长度
	 * @param ws
	 *            --小数后保留位数
	 */
	public static String sqlNumber(String sStr1, int len, int ws) {
		switch (dbType) {
		case DB_ORA:
			return "to_number(" + sStr1 + ")";
		case DB_MSL:
			return "cast(" + sStr1 + " as Decimal(" + len + "," + ws + "))";
		default:
			return "cast(" + sStr1 + " as numeric(" + len + "," + ws + "))";
		}
	}

	/**
	 * trim函数
	 * 
	 * @param str
	 * @return
	 */
	public static String sqlTrim(String str) {
		switch (dbType) {
		case DB_ORA:
		case DB_MSL:
			return "Trim(" + str + ")";
		default:
			return "RTrim(LTrim(" + str + "))";
		}
	}

	/**
	 * QL的Isnull 函数
	 * 
	 * @param str1
	 * @param str2
	 *            <P>
	 *            <B>TIP:DB2数据库调用此方法时，参数类型应保持一致</B>
	 *            </P>
	 * @return
	 */
	public static String sqlIsNull(String str1, String str2) {
		switch (dbType) {
		case DB_ORA:
			return "NVL(" + str1 + "," + str2 + ")";
		case DB_SQL:
			return "IsNull(" + str1 + "," + str2 + ")";
		default:
			return "(case when " + str1 + " is null then " + str2 + " else "
					+ str1 + " end)";
		}
	}

	public static String sqlIsNull(String str1) {
		return sqlIsNull(str1, "0");
	}

	/**
	 * 字符串连接重构方法
	 * 
	 * @param str1
	 *            左字符串
	 * @param str2
	 *            右字符串
	 * @param str3
	 *            缺省参数，可连接多个字符串
	 * @return str1 || str2
	 */
	public static String sqlJN(String str1, String str2, String... str3) {
		switch (dbType) {
		case DB_SQL:
			return str1 + "+" + str2
					+ (str3 == null || str3.length <= 0 ? "" : ("+" + str3));
		case DB_MSL:
			return "concat(" + str1 + "," + str2
					+ (str3 == null || str3.length <= 0 ? "" : ("," + str3))
					+ ")";
		default:
			return str1 + "||" + str2
					+ (str3 == null || str3.length <= 0 ? "" : ("||" + str3));
		}
	}

	/**
	 * 整除
	 * 
	 * @param str1
	 * @param str2
	 * @param bAbs
	 * @return
	 */
	public static String sqlDiv(String str1, String str2, boolean bAbs) {
		switch (dbType) {
		case DB_ORA:
			return "floor(abs("
					+ str1
					+ ")/abs("
					+ str2
					+ ")) "
					+ (bAbs ? ""
							: ("* sign(" + str1 + ") * sign(" + str2 + ")"));
		default:
			return (bAbs ? "abs(" : "") + "(" + str1 + ") / (" + str2 + ")"
					+ (bAbs ? ")" : "");
		}
	}

	/**
	 * 取模
	 * 
	 * @param str1
	 * @param str2
	 * @param bAbs
	 * @return
	 */
	public static String sqlMod(String str1, String str2, boolean bAbs) {
		switch (dbType) {
		case DB_ORA:
			return (bAbs ? "abs(" : "") + "mod(" + str1 + "," + str2 + ")"
					+ (bAbs ? ")" : "");
		case DB_SQL:
			return (bAbs ? "abs(" : "") + "(" + str1 + ") % (" + str2 + ")"
					+ (bAbs ? ")" : "");
		default:
			return (bAbs ? "abs(" : "") + "mod(" + str1 + ", " + str2 + ")"
					+ (bAbs ? ")" : "");
		}
	}

	/**
	 * 拼接sql中where IN条
	 * 
	 * @param str
	 *            "," 简隔字符串
	 * @return String
	 */
	public static String sqlIN(String str) {
		String[] arr = str.split(",");
		StringBuffer tmpStr = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			tmpStr.append("'").append(arr[i].trim()).append("',");
		}
		if (tmpStr.length() > 0) {
			tmpStr.setLength(tmpStr.length() - 1);
		}
		return tmpStr.toString();
	}

	/**
	 * 支持各数据库下空串的判断
	 * 
	 * @param sStr
	 *            String
	 * @return String
	 */
	public static String sqlTrimNull(String sStr) {
		if (dbType == DB_SQL)
			return "len(" + sqlTrim(sStr) + ") = 0";
		else if (dbType == DB_DB2)
			return "length(" + sqlTrim(sStr) + ") = 0";
		else if (dbType == DB_MSL)
			return "length(" + sqlTrim(sStr) + ") = 0";
		else
			return "trim(" + sStr + ") is null";
	}

	/**
	 * 取得数据表中前N条数据
	 * 
	 * @param topNum
	 *            取得的条数
	 * @param fields
	 *            字段各列名
	 * @param table
	 *            表名
	 * @param whereStr
	 *            其他WHERE条件
	 * @param orderStr
	 *            排序
	 * @return
	 */
	public static String sqlTop(int topNum, String fields, String table,
			String whereStr, String orderStr) {
		if (dbType == DB_SQL) {
			return "SELECT TOP "
					+ topNum
					+ " "
					+ fields
					+ " FROM "
					+ table
					+ (whereStr.length() > 0 ? " WHERE " + whereStr + " " : " ")
					+ orderStr;
		} else if (dbType == DB_ORA) {
			return "SELECT " + fields + " FROM (SELECT * FROM " + table + " "
					+ orderStr + ") WHERE ROWNUM<=" + topNum
					+ (whereStr.length() > 0 ? " AND " + whereStr + " " : " ");
		} else if (dbType == DB_DB2) {
			return "SELECT "
					+ fields
					+ " FROM "
					+ table
					+ (whereStr.length() > 0 ? " WHERE " + whereStr + " " : " ")
					+ orderStr + " FETCH FIRST " + topNum + " ROWS ONLY ";
		} else if (dbType == DB_MSL) {
			return "SELECT "
					+ fields
					+ " FROM "
					+ table
					+ (whereStr.length() > 0 ? " WHERE " + whereStr + " " : " ")
					+ orderStr + " LIMIT 0, " + topNum;
		}
		return "";
	}

	/**
	 * 拼接分页sql语句
	 * 
	 * @param sql
	 *            查询数据的sql语句
	 * @param pageSize
	 *            每页存放数据条数
	 * @param pageIndex
	 *            页序号
	 * @return
	 */
	public static String getFySql(String sql, int pageSize, int pageIndex) {
		StringBuffer _sql = new StringBuffer();
		if (pageSize == 0) {
			return sql;
		}
		int starNum = pageSize * pageIndex + 1;
		_sql.append(" select * from ( ");
		if (dbType == DBUtil.DB_SQL) {
			_sql.append("select TOP ").append(String.valueOf(pageSize))
					.append("st,a.* from ( ").append(sql).append(" ) a )");
		} else if (dbType == DBUtil.DB_ORA) {
			_sql.append("select rownum st,a.* from ( ").append(sql)
					.append(" ) a)").append(" where st between ")
					.append(starNum).append(" and ")
					.append(starNum + pageSize - 1);
		} else if (dbType == DBUtil.DB_DB2) {
			_sql.append("select a.*,rownumber() over() as st from ( ")
					.append(sql).append(" ) a )").append(" where st between ")
					.append(starNum).append(" and ")
					.append(starNum + pageSize - 1);
		} else if (dbType == DBUtil.DB_MSL) {
			_sql.append("select 1 as st ,a.* from ( ")
					.append(sql)
					.append(" ) a limit  " + (starNum - 1) + ","
							+ (starNum + pageSize - 1) + ") t");
		}
		return _sql.toString();
	}

	/**
	 * 将数字转化为字符型，并保留相应的位数
	 * 
	 * @param integer
	 *            整数部分位数
	 * @param pointer
	 *            小数部分位数
	 */
	public static String sqlNumberToChar(String fieldStr, int integer,
			int pointer) {
		String format = "";
		for (int i = 0; i < integer; i++) {
			format += "9";
		}
		if (pointer > 0) {
			format += "D";
		}
		for (int i = 0; i < pointer; i++) {
			format += "9";
		}
		switch (dbType) {
		case DB_ORA:
			return format.length() == 0 ? "NVL(to_char(" + fieldStr + "),' ')"
					: "NVL(to_char(" + fieldStr + ",'" + format + "'),' ')";
		case DB_SQL:
			return "IsNull(cast(" + fieldStr + " as varchar),' ')";
		default:
			// 将case改为cast,将varchar改为char,或者将varchar加上位数，如：varchar(100)
			return "(case when cast(" + fieldStr
					+ " as char) is null then ' ' else cast(" + fieldStr
					+ " as char) end)";
		}
	}

	/**
	 * 日期加减天数
	 * 
	 * @param sDate
	 *            String：日期，如果是字符串字段，则要调用函数转换成sql日期
	 * @param sDays
	 *            String：天数
	 * @return String
	 */
	public static String sqlDateAdd(String sDate, String sDays) {
		if (dbType == DB_DB2)
			return " (" + sDate + " + (" + sDays + ") days) ";
		if (dbType == DBUtil.DB_MSL)
			return "date_add(" + sDate + ", interval " + sDays + " day)";
		return " (" + sDate + " + " + sDays + ") ";
	}

	/**
	 * rollup 查询，　str 格式是 用逗号隔开的字段名称，如“setcode，fzqdm，fzqmc”
	 * 
	 * @param str
	 * @return
	 */
	public static String sqlRollUp(String str) {
		if (str == null || str.equalsIgnoreCase("")) {
			return "";
		}
		String sql = null;
		if (dbType == DB_ORA) {
			sql = "ROLLUP(" + str + ")";
		} else if (dbType == DB_DB2) {
			sql = "ROLLUP(" + str + ")";
		} else if (dbType == DB_MSL) {
			sql = str + " WITH ROLLUP";
		}
		return sql;
	}

	/**
	 * 功能说明：具体实现表和视图的存在判断
	 * 
	 * @param sTable
	 *            String：表名
	 * @param bView
	 *            boolean true:sTable为视图名 fase:表名
	 * @throws YssException
	 * @return boolean：存在返回true
	 */
	public static String sqlTableExist(String tableName) {
		String sql = null;
		if (dbType == DB_ORA) {
			sql = "select table_name from user_tables where table_name='"
					+ tableName.toUpperCase().replace("%Y-%M-%D", "%Y-%m-%d")
					+ "'";
		} else if (dbType == DB_SQL) {
			sql = "select top 1 name from sysobjects where name='" + tableName
					+ "' and type='U'";
		} else if (dbType == DB_DB2) {
			sql = "select name from sysibm.systables" + " where  name='"
					+ tableName.toUpperCase().replace("%Y-%M-%D", "%Y-%m-%d")
					+ "'";
		}
		return sql;
	}

	/**
	 * 返回适用于当前数据库的时间字符串转换成日期的函数
	 * 
	 * @param sDate
	 *            日期
	 * @param format
	 *            字符格式
	 * @param flag
	 *            是否字符串加单引号
	 * @return 转换后字符串
	 */
	public static String toDate(String dateStr, String format, boolean flag) {
		if (flag) {
			dateStr = "'" + dateStr + "'";
		}
		return "to_date(" + dateStr + ",'" + format + "')";
	}

	/**
	 * 返回适用于当前数据库的时间字符串转换成日期的函数(默认为yyyy-MM-dd)
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 转换后字符串
	 */
	public static String toDate(String dateStr) {
		return toDate(dateStr, "yyyy-MM-dd", true);
	}

	/**
	 * 返回适用于当前数据库的时间字段转换成日期字符串的函数
	 * 
	 * @param sDate
	 *            日期
	 * @param format
	 *            字符格式
	 * @param flag
	 *            是否字符串加单引号
	 * @return 转换后字符串
	 */
	public static String toChar(String dateStr, String format, boolean flag) {
		if (flag) {
			dateStr = "'" + dateStr + "'";
		}
		return "to_char(" + dateStr + ",'" + format + "')";
	}

	/**
	 * 返回适用于当前数据库的时间字段转换成日期字符串的函数(默认为yyyy-MM-dd)
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 转换后字符串
	 */
	public static String toChar(String dateStr) {
		return toDate(dateStr, "yyyy-MM-dd", true);
	}

	/**
	 * 将数据结果集中的日期字段值转为Date
	 * 
	 * @param obj
	 * @return
	 */
	public static Date getDate(Object obj) {

		Date value = null;
		if (null == obj || "".equals(obj.toString().trim())) {
			return value;
		}
		try {
			value = (Date) obj;
		} catch (Exception e) {
			return value;
		}
		return value;
	}

	/**
	 * 
	 * sql中涉及计算使用的dual。
	 * 
	 * @return String
	 * @author luotaiping
	 * @date 2015-9-23
	 */
	public static String sqlDual() {
		String sql = null;
		if (dbType == DB_DB2) {
			sql = " SYSIBM.SYSDUMMY1 ";
		} else {
			sql = " dual ";
		}
		return sql;
	}
}
