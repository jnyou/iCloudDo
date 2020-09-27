package com.blithe.cms.filter;

import com.alibaba.fastjson.JSONObject;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.pojo.system.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName ShiroConfig
 * @Description: 扩展shiro过滤器，拦截非登录请求。返回信息
 * @Author: 夏小颜
 * @Date: 17:29
 * @Version: 1.0
 **/
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger logger = LoggerFactory.getLogger(LoginFormAuthenticationFilter.class);

	/**
	 * 解决记住我功能session为空问题
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		// 获取session中的subject
		Subject subject = getSubject(request,response);
		Session session = subject.getSession();

		// 判断是不是通过记住我登录
		if( !subject.isAuthenticated() && subject.isRemembered() && session.getAttribute("user") == null) {
			// 说明是点击了记住我
			logger.debug("remembered me, put user in session");
			Object principal = subject.getPrincipal();
			// 不为空再去做操作
			if(null != principal){
				SysUser user = (SysUser) principal;
				session.setAttribute("user", user);
			}
		}
		return subject.isAuthenticated() || subject.isRemembered();
	}

	/**
	 * 已注销登录后，通过该过滤器时，返回true，抛出异常。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		/*
		 * 登录过程直接跳过
		 * */
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {

				return executeLogin(request, response);
			} else {
				// 放行 allow them to see the login page ;)
				return true;
			}
		} else {
			HttpServletRequest httpRequest = WebUtils.toHttp(request);
			if (isAjax(httpRequest)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = null;
				try {
					out = response.getWriter();
					out.write(JSONObject.toJSONString(R.error(503, "会话超时，请重新登录！")));
					out.flush();
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			} else {
				saveRequestAndRedirectToLogin(request, response);
			}
			return false;
		}
	}

	/**
	 * 判断ajax请求
	 *
	 * @param request
	 * @return
	 */
	boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest"
				.equals(request.getHeader("X-Requested-With").toString()));
	}

}
