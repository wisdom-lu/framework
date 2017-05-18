/*  
 * @(#)ReturnMsg.java V1.0 Feb 21, 2014 11:44:10 AM
 * @ com.wafersystems.lbs.utils
 *
 * Copyright (c) 2013, Wafersystems All rights reserved.
 * Wafer PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.utils;


import com.alibaba.fastjson.JSON;


/**
 * <p>Title: action内添加、删除、修改返回json格式数据工具类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Wafersystems</p>
 *
 * @author Mou Lu
 * @Date：Feb 21, 2014 11:44:10 AM
 * @version 1.0
 */
public class ReturnMsg {

	//操作是否成功
	private boolean success;
	
	//错做提醒
	private String msg;
	
	//如果失败了，操作异常message
	private String exception;
	
	//失败了的详细异常信息
	private String excepDetail;
	
	public ReturnMsg() {
		super();
	}
	
	/**
	 *@类名：失败没有异常明细
	 *@描述：{todo}
	 * @param msg
	 * @param exception
	 */
	public ReturnMsg(String msg, String exception) {
		super();
		this.msg = msg;
		this.exception = exception;
	}
	
	/**
	 *@类名：成功操作
	 *@描述：{todo}
	 * @param msg
	 */
	public ReturnMsg(String msg) {
		super();
		this.msg = msg;
	}
	
	/**
	 *@类名：失败有异常并且有异常明细
	 *@描述：{todo}
	 * @param msg
	 * @param exception
	 * @param excepDetail
	 */
	public ReturnMsg(String msg, String exception,String excepDetail) {
		super();
		this.msg = msg;
		this.exception = exception;
		this.excepDetail = excepDetail;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}



	public String getException() {
		return exception;
	}



	public void setException(String exception) {
		this.exception = exception;
	}
	

	@Override
	public String toString() {
		ReturnMsg rm = new ReturnMsg();
		//如果Exception 为空,说明操作是成功的
		if(StringUtils.isEmpty(this.exception)){
			rm.setSuccess(true);
		}else{
			//如果Exception 为空,说明操作是失败的
			rm.setSuccess(false);
			rm.setException(this.exception);
		}
		rm.setMsg(this.msg);
		return JSON.toJSONString(rm);
	}


	/**
	 * Description:
	 * 
	 * @param args  void    
	 * @throws
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReturnMsg ms = new ReturnMsg("xxxadd 成功");
		System.out.print(ms.toString());
		
		
	}

}
