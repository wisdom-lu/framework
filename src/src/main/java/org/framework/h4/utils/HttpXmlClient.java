/*
 * Copyright (c) 2012,Wafer All rights reserved.
 * WAFER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Encoder;

/**
 * @Content：用httpclient实现HTTP或者HTTPS协议的Basic方式认证以及访问<BR>
 *
 * @Edit Author：
 * @Edit Date：
 * @Edit Desc：
 *
 * @Company：Wafersystems
 * @Author：Mou Lu
 * @Date：2012-7-9 上午11:20:32
 * @Version：V 1.0
 */
public class HttpXmlClient {  
    private static Log log = LogFactory.getLog(HttpXmlClient.class); 
     
    
    /**
     * Description:实现REST的POST认证以及访问<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param url
     * @param params
     * @return  String    
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static String post(String url, Map<String, String> params) {  
        HttpClient httpclient = new DefaultHttpClient();  
        String body = null; 
        log.info("create httppost:" + url);
        HttpPost post;
		try {
			post = postForm(url, params);
			body = invoke(httpclient, post);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("post request faild",e);
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			log.error("post request faild",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("post request faild",e);
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
        return body;  
    }
    
    /**
     * Description:实现REST的POST认证以及访问<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param url
     * @param params
     * @return  String    
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static String execPostXml(String url, String xml,String userName,String pwd) {  
        HttpClient httpclient = new DefaultHttpClient();  
        String body = null; 
        log.info("create httppost:" + url);
        HttpPost post = null;
		try {
			post = postXmlBody(url, xml,userName,pwd);
			body = invoke(httpclient, post);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("post request faild",e);
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("post request faild",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("post request faild",e);
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
        return body;  
    }
    
    /**
     *
     * Description:实现REST的GET认证以及访问<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param url
     * @param params
     * @return  String    
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static String get(String url, String params){  
        HttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
        log.info("create httpget:" + url);
        HttpGet get = getForm(url, params);
        try {
			body = invoke(httpclient, get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			log.error("get request faild",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("get request faild",e);
		}finally{
			httpclient.getConnectionManager().shutdown(); 
		}
        return body;  
    }  
      
    /**
     * 
     * Description:实现REST的GET认证以及访问不需要用户名密码<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param url
     * @return  String    
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static String get(String url){  
       HttpClient httpclient = new DefaultHttpClient();  
       String body = null; 
       log.info("create httpGet:" + url);  
       HttpGet get = new HttpGet(url);  
       try {
    	   body = invoke(httpclient, get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			log.error("get request faild",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("get request faild",e);
		}finally{
			 httpclient.getConnectionManager().shutdown();
		}
       return body;  
    }  
          
    
    /**
     *
     * Description:执行HTTP请求的URL<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param httpclient
     * @param httpost
     * @return  String    
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    private static String invoke(HttpClient httpclient,  
            HttpUriRequest httpost) throws ClientProtocolException, IOException {  
        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);
        return body;  
    }  
  
    /**
     * Description:解析Response协议<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param response
     * @return  String    
     */
    private static String paseResponse(HttpResponse response)throws ParseException,IOException{  
       log.info("get response from http server..");
        HttpEntity entity = response.getEntity();
        log.info("response status: " + response.getStatusLine());  
        String charset = EntityUtils.getContentCharSet(entity);  
        log.info(charset);  
        String body = EntityUtils.toString(entity);  
        log.info(body);  
        return body;  
    }  
   
    /**
     * Description:发送请求<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param httpclient
     * @param httpost
     * @return  HttpResponse    
     * @throws
     */
    private static HttpResponse sendRequest(HttpClient httpclient,  
            HttpUriRequest httpost) throws ClientProtocolException,IOException{  
        log.info("execute post...");
        HttpResponse response = null;  
        HttpClient hc = WebClientDevWrapper.wrapClient(httpclient);
        response = hc.execute(httpost);
        return response;  
    }  
  
    
    /**
     * Description:组装GET请求的发送帧<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param url
     * @param params
     * @return  HttpGet    
     * @throws
     */
    private static HttpGet getForm(String url, String params){
    	 HttpGet httpget = new HttpGet(url);
    	 httpget.addHeader("Authorization", "Basic "+params);
        // httpget.addHeader("Content-Type","Content-Type: application/x-www-form-urlencoded");
        // httpget.addHeader("Accept","*/*");
         //httpget.addHeader("Accept-Charset","utf-8");
         return httpget;
    }
    
    /**
     * Description:组装POST请求的发送帧<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param url
     * @param params
     * @return  HttpPost    
     * @throws
     */
    private static HttpPost postForm(String url, Map<String, String> params) throws UnsupportedEncodingException{  
          
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
          
        Set<String> keySet = params.keySet();  
	    for(String key : keySet) {  
	         nvps.add(new BasicNameValuePair(key, params.get(key)));  
	    }
        log.info("set utf-8 form entity to httppost");  
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        return httpost;
    }
    
    /**
     * Description:组装POST请求的发送Body Xml<BR>
     * 
     * Edit Author Edit Date
     * Edit Content
     * @param url
     * @param params
     * @return  HttpPost    
     * @throws
     */
    private static HttpPost postXmlBody(String url,String xml,String userName,String pwd) throws UnsupportedEncodingException{  
        HttpPost httpost = new HttpPost(url);
        String np = userName+":"+pwd;
    	BASE64Encoder encoder = new BASE64Encoder();
    	String base64_un = encoder.encode(np.getBytes());
    	httpost.addHeader("Authorization", "Basic "+base64_un);
        // httpget.addHeader("Content-Type","Content-Type: application/x-www-form-urlencoded");
        // httpget.addHeader("Accept","*/*");
         //httpget.addHeader("Accept-Charset","utf-8");
        StringEntity se = new StringEntity(xml);
        log.info("set utf-8 form entity to httppost");  
        httpost.setEntity(se);  
        return httpost;
    }
    
   
    
    public static void main(String[] args) {
    	String name="admin";
    	String password="Wafer123";
    	String np = "admin:Wafer123";
    	BASE64Encoder encoder = new BASE64Encoder();
    	String base64_un = encoder.encode(np.getBytes());
    	String url = "https://192.168.0.149/ise/mnt/api/Session/ActiveCount";
    	Map<String, String> params = new HashMap<String, String>();  
    	/*params.put("name", name);
    	params.put("password", password);*/
    	//params.put("Authorization", "Basic "+base64_un);
    	
    	String xml;
		xml = HttpXmlClient.get(url, base64_un);
		
		//HttpXmlClient.execPostXml(url, xml);
		log.info(xml);
    	
	}
} 

