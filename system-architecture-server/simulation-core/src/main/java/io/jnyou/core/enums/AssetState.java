package io.jnyou.core.enums;


/**
 * asset state enumeration
 * @author jnyou
 */
public class AssetState {

	public enum caption {
		NORMAL(0,"正常"),
		UNKNOWN(1,"正在初始化"),
		LOSTCONECT(2,"通信故障"),
		WARNING(3,"告警"),
		ERROR(4,"错误");

		private Integer code;
		private String msg;
		caption (Integer code,String msg) {
			this.code = code;
			this.msg = msg;
		}

		public Integer getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}

	}

	public enum type{
		NORMAL,
		UNKNOWN,
		LOSTCONECT,
		WARNING,
		ERROR;

		public static type MIN_SEVERE_STATE = NORMAL;

		public static type MAX_SEVERE_STATE = ERROR;

		public boolean sevierThan(type other) {
			return getSevierity(this) > getSevierity(other);
		}

		private int getSevierity(type s) {
			//normal < unknown
			if (s == UNKNOWN)
				return 1;
			else if (s == NORMAL)
				return 0;
			else
				return s.ordinal();
		}
	}

}