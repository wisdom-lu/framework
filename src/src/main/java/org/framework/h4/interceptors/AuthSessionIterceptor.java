/*  
 * @(#)AuthSessionIterceptor.java V1.0 May 13, 2014 10:54:28 AM
 * @ com.wafersystems.wificloud.auth.interceptors
 *
 * Copyright (c) 2013, Wafersystems All rights reserved.
 * Wafer PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>Title: 用户身份Session拦截器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Wafersystems</p>
 *
 * @author Mou Lu
 * @Date：May 13, 2014 10:54:28 AM
 * @version 1.0
 */
public class AuthSessionIterceptor implements HandlerInterceptor {
	
	private static Log log = LogFactory.getLog(AuthSessionIterceptor.class);

	/**
	 * Description: 
	 *
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		log.debug("AuthSessionItercept afterCompletion is execute");

	}

	/**
	 * Description: 
	 *
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		log.debug("AuthSessionItercept postHandle is execute");

	}

	/** 
	* 在业务处理器处理请求之前被调用 
	* 如果返回false 
	*     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
	*  
	* 如果返回true 
	*    执行下一个拦截器,直到所有的拦截器都执行完毕 
	*    再执行被拦截的Controller 
	*    然后进入拦截器链, 
	*    从最后一个拦截器往回执行所有的postHandle() 
	*    接着再从最后一个拦截器往回执行所有的afterCompletion() 
	*/  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		log.debug("AuthSessionItercept preHandle is execute");
		String url = request.getRequestURI();
//		if(url == null || url.indexOf("auth") > 0 ){
//			return false;
//		}
		return true;
	}

}
