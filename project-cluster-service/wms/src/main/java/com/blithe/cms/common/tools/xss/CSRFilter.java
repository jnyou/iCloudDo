package com.blithe.cms.common.tools.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author jnyou
 */
public class CSRFilter implements Filter {

	private boolean verify = true;//验证Referer头
	private String[] verifyReferer = null;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("amtp");
		this.verify = Boolean.valueOf(bundle.getString("referer"));
		this.verifyReferer = bundle.getString("referer.ip").split(";");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		if(verify){
			String referer = ((HttpServletRequest)request).getHeader("Referer");
			boolean b = false;
			for(String vReferer : verifyReferer){
				if(referer==null || referer.trim().startsWith(vReferer)){
					b = true;
					chain.doFilter(request, response);
					break;
				}
			}
			if(!b){
				System.out.println("疑似CSRF攻击，referer:"+referer);
			}
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

}
