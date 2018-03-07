/*  
 * @(#)GenericRedisDAO.java V1.0 2016-7-5 上午11:16:05
 * @ org.framework.h4.dao
 *
 * Copyright (c) 2013, Framework All rights reserved.
 * Framework PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Framework</p>
 *
 * @author Mou Lu
 * @Date：2016-7-5 上午11:16:05
 * @version 1.0
 */
public class RedisDAOBase <Key extends Serializable, Value extends Serializable> {
	 	
		@Autowired
	    protected RedisTemplate<String, Value> redisTemplate;  
	  
	    /** 
	     * 设置redisTemplate 
	     * @param redisTemplate the redisTemplate to set 
	     */  
	    public void setRedisTemplate(RedisTemplate<String, Value> redisTemplate) {  
	        this.redisTemplate = redisTemplate;  
	    }
	      
	    /**
	     * 获取Spring Redis 操作
	     * @return
	     */
	    public RedisTemplate<String, Value> getRedisTemplate() {
			return redisTemplate;
		}
	    
		/** 
	     * 获取 RedisSerializer 
	     * <br>------------------------------<br> 
	     */  
	    protected RedisSerializer<String> getRedisSerializer() {  
	        return redisTemplate.getStringSerializer();  
	    }
	    
	 // ----------------------------------------Object----------------------------------------
		/**
		 * 设置对象
		 * 
		 * @param key
		 * @param object
		 * @param timeout
		 * @param 对象class
		 * @return
		 * @throws Exception
		 */
		public boolean addObject(final String key, final Object object, final Long timeout, Value clazz) throws Exception {
				redisTemplate.opsForValue().set(key, clazz);
				return true;
		}

		/**
		 * 获得对象
		 * 
		 * @param key
		 * @return
		 */
		public Object getObject(final String key) {
			return redisTemplate.opsForValue().get(key);
		}
		
		// ---------------------------------------String-----------------------------------------

		/**
		 * 新增String ----setNX 不存在则增加 ------------------------------
		 * 
		 * @param key
		 *            键
		 * @param value
		 *            值
		 * @param timout
		 *            超时(秒)
		 * @return true 操作成功，false 已存在值
		 */
		public boolean addString(final String key, final String value, final Long timeout) {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					Boolean result = connection.setNX(key.getBytes(), value.getBytes());
					if (result == false)
						return result;
					if (timeout != null && timeout > 0)
						connection.expire(key.getBytes(), timeout);
					return result;
				}
			});
			return result;
		}

		/**
		 * 批量新增String---setNx 不存在则增加
		 * 
		 * @param keyValueList
		 *            键值对的map
		 * @param timeout
		 *            超时处理
		 * @return
		 */
		public boolean addString(final Map<String, String> keyValueList, final Long timeout) {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					for (String key : keyValueList.keySet()) {
						connection.setNX(key.getBytes(), keyValueList.get(key).getBytes());
						if (timeout != null && timeout > 0)
							connection.expire(key.getBytes(), timeout);
					}
					return true;
				}
			}, false, true);
			return result;
		}

		/**
		 * 通过key获取单个
		 * 
		 * @param key
		 * @return
		 */
		public String getString(final String key) {
			String value = redisTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] result = connection.get(key.getBytes());
					if(result != null && result.length > 0)
						return new String(result);
					return null;
				}
			});
			return value;
		}

		/**
		 * 修改 String
		 * 
		 * @param key
		 * @param value
		 * @return
		 */
		/*
		重新Set等于Update
		public boolean updateString(final String key, final String value) {
			if (getString(key) == null) {
				throw new NullPointerException("数据行不存在, key = " + key);
			}
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					connection.set(key.getBytes(), value.getBytes());
					return true;
				}
			});
			return result;
		}
		/

		// ---------------------------------------List-----------------------------------------
		/**
		 * 新增Hash ----setNX 不存在则增加 ------------------------------
		 * 
		 * @param key
		 *            键
		 * @param value
		 *            值
		 * @param timout
		 *            超时(秒)
		 * @return true 操作成功，false 已存在值
		 */
		public boolean addHash(final String key, final String field, final String value, final Long timeout) {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					Boolean result = connection.hSetNX(key.getBytes(), field.getBytes(), value.getBytes());
					if (result == false)
						return result;
					if (timeout != null && timeout > 0)
						connection.expire(key.getBytes(), timeout);
					return result;
				}
			});
			return result;
		}
		
		/**
		 * Update Hash ----setNX 不存在增加，存在更新 ------------------------------
		 * 
		 * @param key
		 *            键
		 * @param value
		 *            值
		 * @param timout
		 *            超时(秒)
		 * @return true 操作成功，false 已存在值
		 */
		public boolean updateHash(final String key, final String field, final String value, final Long timeout) {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					Boolean result = connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
					if (result == false)
						return result;
					if (timeout != null && timeout > 0)
						connection.expire(key.getBytes(), timeout);
					return result;
				}
			});
			return result;
		}

		/**
		 * 批量新增Hash ----setNX 不存在则增加 ------------------------------
		 * 
		 * @param key
		 *            键
		 * @param value
		 *            值
		 * @param timout
		 *            超时(秒)
		 * @return true 操作成功，false 已存在值
		 */
		public boolean addHash(final String key, final Map<String, String> fieldValueList, final Long timeout) {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					for (String hashKey : fieldValueList.keySet()) {
						connection.hSetNX(key.getBytes(), hashKey.getBytes(), fieldValueList.get(hashKey).getBytes());
						if (timeout != null && timeout > 0)
							connection.expire(key.getBytes(), timeout);
					}
					return true;
				}
			});
			return result;
		}

		/**
		 * 通过key获取单个
		 * 
		 * @param key
		 * @return
		 */
		public Object getHashField(final String key, final String field) {
			String value = redisTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					return new String(connection.hGet(key.getBytes(), field.getBytes()));
				}
			});
			return value;
		}
		
		/**
		 * 通过key获取整个Hash
		 * 
		 * @param key
		 * @return
		 */
		public Map<byte[], byte[]> getHashAll(final String key, final String field) {
			Map<byte[], byte[]> value = redisTemplate.execute(new RedisCallback<Map<byte[], byte[]>>() {
				public Map<byte[], byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.hGetAll(key.getBytes());
				}
			});
			return value;
		}
		
		//---------------------------------------------------通用删除-------------------------------------------------
		/**
		 * 删除单个
		 * 
		 * @param key
		 */
		public void delete(final String key) {
	        redisTemplate.execute(new RedisCallback<Long>() {
	            public Long doInRedis(RedisConnection connection) throws DataAccessException {
	                return connection.del(key.getBytes());
	            }
	        });
	    	}
		
		
		//----------------------------------------------------队列操作--------------------------------------------------
		  /** 
	     * 压栈 
	     *  
	     * @param key 
	     * @param value 
	     * @return 
	     */  
	    public Long push(String key, Value value) {  
	        return redisTemplate.opsForList().leftPush(key, value);  
	    }  
	  
	    /** 
	     * 出栈 
	     *  
	     * @param key 
	     * @return 
	     */  
	    public String pop(String key) {  
	        return (String) redisTemplate.opsForList().leftPop(key);  
	    }
	  
	    /** 
	     * 入队 
	     *  
	     * @param key 
	     * @param value 
	     * @return 
	     */  
	    public Long in(String key, Value value) {  
	        return redisTemplate.opsForList().rightPush(key, value);  
	    }  
	  
	    /** 
	     * 出队 
	     *  
	     * @param key 
	     * @return 
	     */  
	    public String out(String key) {  
	        return (String) redisTemplate.opsForList().leftPop(key);  
	    }  
	  
	    /** 
	     * 栈/队列长 
	     *  
	     * @param key 
	     * @return 
	     */  
	    public Long length(String key) {  
	        return redisTemplate.opsForList().size(key);  
	    }  
	  
	    /** 
	     * 范围检索 
	     *  
	     * @param key 
	     * @param start 
	     * @param end 
	     * @return 
	     */  
	    public List<Value> range(String key, long start, long end) {  
	        return redisTemplate.opsForList().range(key, start, end);  
	    }  
	  
	    /** 
	     * 移除 
	     *  
	     * @param key 
	     * @param i 
	     * @param value 
	     */  
	    public void remove(String key, long i, String value) {  
	        redisTemplate.opsForList().remove(key, i, value);  
	    }  
	  
	    /** 
	     * 检索 
	     *  
	     * @param key 
	     * @param index 
	     * @return 
	     */  
	    public String index(String key, long index) {  
	        return (String) redisTemplate.opsForList().index(key, index);  
	    }  
	  
	    /** 
	     * 置值 
	     *  
	     * @param key 
	     * @param index 
	     * @param value 
	     */  
	    public void set(String key, long index, Value value) {  
	        redisTemplate.opsForList().set(key, index, value);  
	    }  
	  
	    /** 
	     * 裁剪 
	     *  
	     * @param key 
	     * @param start 
	     * @param end 
	     */  
	    public void trim(String key, long start, int end) {  
	        redisTemplate.opsForList().trim(key, start, end);  
	    }  
	    
	    //---------------------------------------------------SET-----------------------------------------------
	    /**
		 * 新增Set ----setNX 不存在则增加 ------------------------------
		 * 
		 * @param key
		 *            键
		 * @param value
		 *            值
		 * @param timout
		 *            超时(秒)
		 * @return true 操作成功，false 已存在值
		 */
		public Long addSet(final String key, final String value, final Long timeout) {
			Long result = redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					Long result = connection.sAdd(key.getBytes(), value.getBytes());
					if (result == 0)
						return result;
					if (timeout != null && timeout > 0)
						connection.expire(key.getBytes(), timeout);
					return result;
				}
			});
			return result;
		}
		
		
		/**
		 * 通过key获取单个Set
		 * 
		 * @param key
		 * @return
		 */
		public Set<byte[]> getSet(final String key) {
			Set<byte[]> value = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
				public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.sMembers(key.getBytes());
				}
			});
			return value;
		}

}
