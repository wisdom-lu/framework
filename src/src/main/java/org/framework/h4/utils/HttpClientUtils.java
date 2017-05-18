/*  
 * @(#)HttpClientUtils.java V1.0 2014-1-22 上午11:08:04
 * @ com.wafersystems.lbs.pi.utils
 *
 * Copyright (c) 2013, Wafersystems All rights reserved.
 * Wafer PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Wafersystems</p>
 *
 * @author Mou Lu
 * @Date：2014-1-22 上午11:08:04
 * @version 1.0
 */
public class HttpClientUtils {
	
	private Log log = LogFactory.getLog(super.getClass());
	
	/**
	 * 返回XML格式
	 */
	public final static String ACCEPT_XML = "application/xml";
	
	/**
	 * 返回JSON格式
	 */
	public final static String ACCEPT_JSON = "application/json";
	
	/**
	 * 成功
	 */
	public final static int CODE_SUCCESS = 200;
	
	/**
	 * MSE not found
	 */
	public final static int CODE_NOTFOUND = 404;
	
	/**
	 * MSE 服务器错误
	 */
	public final static int CODE_ERROR = 500;
	
	/**
	 * Get 请求
	 */
	public final static int GET = 0;
	
	/**
	 * Post 请求 
	 */
	public final static int POST = 1;
	
	/**
	 * Description:获取加密之后的用户名密码串
	 * 
	 * @param userName
	 * @param password
	 * @return  String    
	 */
	public String getAuth(String userName,String password){
		return new BASE64Encoder().encode((userName+":"+password).getBytes());
	}
	
	/**
	 * Description:解密Auth64解密
	 * 
	 * @param coder
	 * @return  byte[]    
	 * @throws
	 */
	public byte[] getAuthDecoder(String coder){
		try {
			return new BASE64Decoder().decodeBuffer(coder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Description:获取get请求request
	 * 
	 * @param url
	 * @param accept
	 * @param userName
	 * @param password
	 * @return  HttpPost   
	 */
	public HttpGet getRequestGet(String url,String accept){
		HttpGet request = new HttpGet(url);
		request.addHeader("Accept", accept);
		request.addHeader("Content", accept);
		//request.addHeader("Authorization", "Basic "+this.getAuth(userName, password));
		return request;
	}
	
	/**
	 * Description:获取post请求request
	 * 
	 * @param url
	 * @param accept
	 * @param userName
	 * @param password
	 * @return  HttpPost    
	 */
	public HttpPost getRequestPost(String url,String accept){
		HttpPost request = new HttpPost(url);
		request.addHeader("Accept", accept);
		request.addHeader("Content", accept);
		//request.addHeader("Authorization", "Basic "+this.getAuth(userName, password));
		return request;
	}
	
	/**
	 * Description:执行并且关闭HTTP连接
	 * 
	 * @param client
	 * @param request
	 * @return  String    
	 */
	public String getContent(HttpClient client,HttpGet request) throws Exception {
		try {
			client = WebClientDevWrapper.wrapClient(client);
			HttpResponse response = client.execute(request);
			int code = response.getStatusLine().getStatusCode();
			return EntityUtils.toString(response.getEntity(),"UTF-8");
		}  catch (ClientProtocolException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}finally{
			client.getConnectionManager().shutdown();
		}
		return null;
	}
	
	/**
	 * Description:执行并且关闭HTTP连接
	 * 
	 * @param client
	 * @param request
	 * @return  String    
	 */
	public String getContent(HttpClient client,HttpPost request,List<NameValuePair> nvps) throws Exception{
		try {
			//POST 请求加参数
			if(nvps != null || nvps.size() > 0){
	            request.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			}
			client = WebClientDevWrapper.wrapClient(client);
			HttpResponse response = client.execute(request);
			int code = response.getStatusLine().getStatusCode();
			return EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}finally{
			client.getConnectionManager().shutdown();
		}
		return null;
	}
	
	/**
	 * Description:执行请求并且解析JSON 返回对象
	 * 
	 * @param <T>
	 * @param url 
	 * @param requestType POST or GET
	 * @param accept JSON/XML
	 * @param cls 目标类型
	 * @return T 返回目标类型
	 * @throws PIException  错误码    
	 */
	public <T> T execute(String url,Class<T> cls) throws Exception{
		HttpGet response = this.getRequestGet(url, HttpClientUtils.ACCEPT_JSON);
		String json = this.getContent(new DefaultHttpClient(), response);
		//POST 方式请求
//			HttpPost response = this.getRequestPost(url, HttpClientUtils.ACCEPT_JSON,SysParam.PI_USERNAME, SysParam.PI_PWD);
//			json = this.getContent(new DefaultHttpClient(), response);
		return JSON.parseObject(json,cls);
	}
	
	/**
	 * Description:执行请求并且解析JSON 返回对象
	 * 
	 * @param <T>
	 * @param url 
	 * @param requestType POST or GET
	 * @param accept JSON/XML
	 * @param cls 目标类型
	 * @return T 返回目标类型
	 * @throws PIException  错误码    
	 */
	public String execute(String url) throws Exception{
		HttpGet response = this.getRequestGet(url, HttpClientUtils.ACCEPT_JSON);
		String json = this.getContent(new DefaultHttpClient(), response);
		return json;
	}
	
	public void setBodyParam(HttpPost request,String xml) throws Exception{
		StringEntity se = new StringEntity(xml,"UTF-8");
		request.setEntity(se);
	}
	
	public List<NameValuePair> addParam(List<NameValuePair> list,String key,String value) throws Exception{
		//POST 请求加参数
		//List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair(key,value));
        //request.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        return list;
	}
	
	public static void main(String[] args) {
		
		//LBS to CRM interface - Update CRM AP
		
		HttpClientUtils hcu = new HttpClientUtils();
		HttpPost post = hcu.getRequestPost("http://103.9.244.83/HotelCRM/custom/ah_api/Slim-2.6.2/v9.x/SetCustInOut", HttpClientUtils.ACCEPT_JSON);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		try {
			hcu.addParam(list, "Mac", "11.11.11.11");
			hcu.addParam(list, "Type", "1");
			hcu.addParam(list, "Addr", "teste");
			HttpClient hc = new DefaultHttpClient();
			String test = hcu.getContent(hc, post,list);
			System.out.println(test);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
}
