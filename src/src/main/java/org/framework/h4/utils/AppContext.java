/*  
 * @(#)SpingContextUtils.java V1.0 2013-11-28 下午03:37:25
 * @ org.framework.util
 *
 * Copyright (c) 2013, Framework All rights reserved.
 * Framework PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.utils;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;

/**
 * <p>Title: 获取Bean,必须把此类注册到Spring中为一个Bean
 * 			1,必须在spring 配置文件里面添加此句	<bean id="AppContext" class="org.framework.util.AppContext"> </bean>	
 * </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Framework</p>
 *
 * @author Mou Lu
 * @Date：2013-11-28 下午03:37:25
 * @version 1.0
 */
public class AppContext extends ApplicationObjectSupport{
	
	private static Log log = LogFactory.getLog(AppContext.class);

	private static ApplicationContext applicationContext;
	
	@PostConstruct
	public void init(){
		applicationContext = super.getApplicationContext();
	}
	
	public static void setAppConetxt(ApplicationContext appContext){
		applicationContext = appContext;
	}
	
	public static <T extends Object> T getBean(String beanName,Class<T> cls){
		T objClass = null;
		try {
			objClass = applicationContext.getBean(beanName, cls);
		} catch (Exception e) {
			log.error("AppContext getBeans error:",e);
			return null;
		}
		return objClass;
	}
	
	/**
	 * @param beanName
	 * @param cls
	 * @param appContext 第三方的Application获取Bean
	 * @return
	 */
	public static <T extends Object> T getBean(String beanName,Class<T> cls,ApplicationContext appContext){
		T objClass = null;
		try {
			objClass = appContext.getBean(beanName, cls);
		} catch (Exception e) {
			log.error("AppContext getBeans error:",e);
			return null;
		}
		return objClass;
	}
	
}
