/*  
 * @(#)ResourceMsg.java V1.0 Feb 25, 2014 9:32:25 AM
 * @ com.wafersystems.lbs.utils
 *
 * Copyright (c) 2013, Framework All rights reserved.
 * Framework PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.utils;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.support.RequestContext;

/**
 * <p>Title: 类里面根据key获取资源文件的值工具类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Framework</p>
 *
 * @author Mou Lu
 * @Date：Feb 25, 2014 9:32:25 AM
 * @version 1.0
 */
public class ResourceMsg {
	
	private Log log = LogFactory.getLog(getClass());
	
	private static ResourceBundle rb;
	
	/**
	 * Description:获取资源文件的内容
	 * 
	 * @param key
	 * @return  String    
	 * @throws
	 */
	public static String getMsg(String ... keys){
		//ResourceBundle rb = ResourceBundle.getBundle("messages");//资源文件通用名
		//String welcomeCalvin =   MessageFormat.format(welcome,new String[]{"calvin"});
		String msg = "";
		for(String k : keys){
			msg += rb.getString(k)+" ";
		}
		return msg;//键值
	}
	
	/**
	 * Description:获取浏览器语音国际化key对应的value
	 * 
	 * @param request
	 * @param key
	 * @return  String    
	 * @throws
	 */
	public static String getMsg(HttpServletRequest request,String key){
		RequestContext requestContext = new RequestContext(request);
        return requestContext.getMessage(key);
	}
	
	/**
	 * Description:获取浏览器语音国际化key对应的value
	 * 
	 * @param request
	 * @param key
	 * @param param[] 是对应占位符的值
	 * @return  String    
	 * @throws
	 */
	public static String getMsg(HttpServletRequest request,String key,String ... param){
		RequestContext requestContext = new RequestContext(request);
		String keyVal = requestContext.getMessage(key);
		if(param != null && param.length > 0){
			for (int i = 0; i < param.length; i++) {
				keyVal = keyVal.replace("{"+i+"}", param[i]);
			}
		}
        return keyVal;
	}
	
	/**
	 * Description:获取资源文件的内容
	 * 
	 * @param key
	 * @return  String    
	 * @throws
	 */
	public static String getMsg(String  key){
		return rb.getString(key);//键值
	}
	
	/**
	 * 加载资源文件对象
	 */
	static {
		rb = ResourceBundle.getBundle("messages");//资源文件通用名
	}
	
}
