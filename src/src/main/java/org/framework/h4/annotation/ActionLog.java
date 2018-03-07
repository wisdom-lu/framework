/*  
 * @(#)ActionLog.java V1.0 2013-11-14 下午05:14:52
 * @ org.framework.annotation
 *
 * Copyright (c) 2013, Framework All rights reserved.
 * Framework PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title: 行为日志注解</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Framework</p>
 *
 * @author Mou Lu
 * @Date：2013-11-14 下午05:14:52
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLog {
	
	/**
	 * Description:日志内容
	 * 
	 * @return  String    
	 * @throws
	 */
	String content();
	
	/**
	 * Description:日志类型
	 * 
	 * @return  int    
	 * @throws
	 */
	int logType() default 0;
	
	/**
	 * Description:日志级别INFO,DEBUG,WARN,ERROR
	 * 
	 * @return  int    
	 * @throws
	 */
	int logLevel() default 2;
}
