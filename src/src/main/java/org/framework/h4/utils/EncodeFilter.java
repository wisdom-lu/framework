/*  
 * @(#)EncodeFilter.java V1.0 2013-11-14 上午09:55:12
 * @ org.framework.util
 *
 * Copyright (c) 2013, Framework All rights reserved.
 * Framework PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: 字符集过滤器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Framework</p>
 *
 * @author Mou Lu
 * @Date：2013-11-15 上午09:20:06
 * @version 1.0
 */
public class EncodeFilter implements Filter {

	private String encode;

	/**
	 * Constructor of the object.
	 */
	public EncodeFilter() {
		super();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding(this.getEncode());
		arg2.doFilter(arg0, arg1);
		arg1.setCharacterEncoding(this.getEncode());

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.encode = arg0.getInitParameter("encode");
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	private String getEncode() {
		return this.encode == null ? "UTF-8" : this.encode;
	}

}
