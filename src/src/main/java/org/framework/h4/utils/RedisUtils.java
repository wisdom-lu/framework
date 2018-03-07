/*  
 * @(#)RedisUtils.java V1.0 2016-7-5 上午10:33:47
 * @ org.framework.h4.utils
 *
 * Copyright (c) 2013, Framework All rights reserved.
 * Framework PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.utils;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * <p>Title: Redis 数据库操作类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Framework</p>
 *
 * @author Mou Lu
 * @Date：2016-7-5 上午10:33:47
 * @version 1.0
 */
public class RedisUtils {
	
	
	/**
	 * 数据库连接
	 */
	private Jedis jedis = null;
	
	public RedisUtils(){
		
	}
	
	/**
	 *@类名：RedisUtils.java初始化构造连接
	 *@描述：{todo}
	 * @param host
	 * @param port
	 * @param pwd
	 */
	public RedisUtils(String host,int port,String pwd){
		this.setConnect(host, port, pwd);
	}
	
	/**
	 * Description:连接数据库连接
	 * 
	 * @param host
	 * @param port
	 * @param pwd  void    
	 * @throws
	 */
	public void setConnect(String host,int port,String pwd){
		Jedis jedis =new Jedis(host,port);
		jedis.auth(pwd);
		this.jedis = jedis;
	}
	
	
	/**
	 * Description:获取连接好的数据库连接
	 * 
	 * @return  Jedis    
	 * @throws
	 */
	public Jedis getConnect(){
		return jedis;
	}
	
	
	/**
	 * Description:获取list 数据
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return  List<String>    
	 * @throws
	 */
	public List<String> getLrange(String key,int start,int end){
		return this.getConnect().lrange(key, start, end);
	}

	/**
	 * Description:
	 * 
	 * @param args  void    
	 * @throws
	 */
	public static void main(String[] args) {
		RedisUtils ru =  new RedisUtils("192.168.0.201",6379,"moboplus!#123");
		
		for (int i = 5131; i < 100000; i++) {
			ru.getConnect().hset("MAC_USERS","AA-BB-CC-EE-DD-FFD"+i, "{a:0,o:'2017-07-01 00:00:00',e:'2017-07-01 23:00:00'}");
		}
		//String value = ru.getConnect().hget("MAC_USERS", "AA-BB-CC-EE-DD-FF10000");
		//System.out.println(value);
		//List<String> list = ru.getLrange("CustomAp", 0, 100);
		//System.out.println("get Lrange success");
		
	}

}
