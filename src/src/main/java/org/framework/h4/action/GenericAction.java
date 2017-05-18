/*  
 * @(#)GenericAction.java V1.0 2013-11-14 上午10:04:58
 * @ org.framework.action
 *
 * Copyright (c) 2013, Wafersystems All rights reserved.
 * Wafer PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.framework.h4.dao.IDao;
import org.framework.h4.service.IService;
import org.framework.h4.utils.AppContext;
import org.framework.h4.utils.GenericsUtils;
import org.framework.h4.utils.PageBean;
import org.framework.h4.utils.ReturnMsg;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>Title: 基类Action</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Wafersystems</p>
 *
 * @author Mou Lu
 * @Date：2013-11-14 上午10:04:58
 * @version 1.0
 */
public class GenericAction<T,EntityDao extends Serializable ,Entity extends Serializable,PK extends Serializable> {
	
	private final String LOGIN_USER_KEY = "long_user_key";
	
	/**
	 * 获取response 文本输出
	 */
	public final String RES_TYPE_TEXT = "text/html";
	
	protected final Log _log = LogFactory.getLog(getClass());
	
	/**
	 * 获取JSP上下文，注意在Web.xml中开启 org.springframework.web.context.request.RequestContextListener监听
	 */
	protected RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected HttpSession session;
	
	/**
	 * Description:获取档期登陆用户信息
	 * 
	 * @param <T>
	 * @param beanName
	 * @param cls
	 * @return  T    
	 * @throws
	 */
	public <T extends Object> T getLoginUser(Class<T> cls){
		T objClass = null;
		try {
			objClass = (T) this.getSession().getAttribute(this.LOGIN_USER_KEY);
		} catch (Exception e) {
			_log.error("GenricAction getLoginUser error:",e);
			return null;
		}
		return objClass;
	}
	
	/**
	 * Description:设置登陆用户信息
	 * 
	 * @param <T>
	 * @param beanName
	 * @param cls
	 * @return  T    
	 * @throws
	 */
	public void setLoginUser(Object t){
		this.getSession().setAttribute(this.LOGIN_USER_KEY, t);
	}
	
	private IService<EntityDao,Entity,PK> genericService;
	
	private Class<IService> getEntityClass() {
		return GenericsUtils.getSuperClassGenricType(getClass());
	}
	
	@SuppressWarnings("unchecked")
	protected IService getGenericService(){
		String beanName = this.getEntityClass().getSimpleName().substring(1)+"Impl";
		return AppContext.getBean(beanName, this.getEntityClass());
	}
	
	public ModelAndView findAll(Entity entity,PageBean pageBean) throws Exception {
		ModelAndView mv = new ModelAndView("user");//返回view 的名字
		List<Entity> list = ((IService)genericService).findAll();
		mv.addObject("list",list);
		
		return mv;
	}
	
	/**
	 * Description:添加或者删除的时候返回JSON格式数据
	 * 
	 * @param msg 提示信息
	 * @return void
	 * @throws IOException      
	 */
	public void returnJson(String msg) throws IOException{
		ReturnMsg ms = new ReturnMsg(msg);
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = this.response.getWriter();
		pw.write(ms.toString());
		pw.close();
	}
	
	/**
	 * Description:添加或者删除的时候返回JSON格式数据
	 * 
	 * @param msg 提示信息
	 * @param exception 错误异常
	 * @return void
	 * @throws IOException      
	 */
	public void returnJson(String msg,String exception) throws IOException{
		ReturnMsg ms = new ReturnMsg(msg,exception == null ? "" : exception);
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = this.response.getWriter();
		pw.write(ms.toString());
		pw.close();
	}
	
	/**
	 * Description:添加或者删除的时候返回JSON格式数据
	 * 
	 * @param msg 提示信息
	 * @param exception 异常信息
	 * @param detail 异常明细
	 * @return void
	 * @throws IOException      
	 */
	public void returnJson(String msg,String exception,String detail) throws IOException{
		ReturnMsg ms = new ReturnMsg(msg,exception == null ? "" : exception,detail == null ? "" : detail);
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = this.response.getWriter();
		pw.write(ms.toString());
		pw.close();
	}
	
	/**
	 * Description:获取输出PrintWriter
	 * 
	 * @param String type 输出类型（RES_TYPE_TEXT,RES_TYPE_PDF）
	 * @return PrintWriter
	 * @throws IOException      
	 */
	public PrintWriter getOutput(String type) throws IOException{
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType(type+";charset=UTF-8");
		PrintWriter pw = this.response.getWriter();
		return pw;
	}
	
	
	
	/***
	 * 在每个Action方法执行前都会执行此方法
	 * Description:
	 * 
	 * @param request
	 * @param response  void    
	 * @throws
	 */
	@ModelAttribute
	public void setReqAndResp(HttpServletRequest request,HttpServletResponse response){
		this.setRequest(request);
		this.setResponse(response);
		this.setSession(request.getSession());
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
	

}
