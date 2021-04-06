package io.jnyou.core.strengattr;


public abstract class WebCamera {

	private String userName;
	
	private String password;

	private String host;

	private int port;

	private String rtspUrlPattern;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}	
	
	public String getRtspUrlPattern() {
		return rtspUrlPattern;
	}

	public void setRtspUrlPattern(String rtspUrlPattern) {
		this.rtspUrlPattern = rtspUrlPattern;
	}
}
