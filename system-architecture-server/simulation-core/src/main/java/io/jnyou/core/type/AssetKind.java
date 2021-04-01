package io.jnyou.core.type;

/**
 * @author jnyou
 */
public enum AssetKind {
	
	SPACE,
	DEVICE,
	SERVICE,
	PROBE,
	CONTROL;

	private String canonicalName;
	
	AssetKind() {
		StringBuilder sb = new StringBuilder(name().toLowerCase());
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		canonicalName = sb.toString(); 
	}
	
	public String canonicalName() {
		return canonicalName;
	}

	public enum caption {
		SPACE(0, "空间"),
		DEVICE(1, "设备"),
		SERVICE(2, "服务"),
		PROBE(3, "监测器"),
		CONTROL(4, "控制器"),
		;

		private int code;
		private String msg;

		caption(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}

}
