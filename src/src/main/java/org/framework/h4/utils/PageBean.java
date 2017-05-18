/*  
 * @(#)PageBean.java V1.0 2013-11-14 上午10:45:10
 * @ org.framework.util
 *
 * Copyright (c) 2013, Wafersystems All rights reserved.
 * Wafer PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.utils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: 分页标签</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Wafersystems</p>
 *
 * @author Mou Lu
 * @Date：2013-11-14 上午10:45:10
 * @version 1.0
 */
public class PageBean<T extends Serializable> implements Serializable {
	
	/**
	 * 给Requets里面存放值得键
	 */
	public static final String PAGEBEAN_KEY = "pageBean";
	
	/**
	 * 当前页
	 */
	private int pageNow = 1;
	
	/**
	 * 总共多少页
	 */
	private long pageTotal = 0;
	
	/**
	 * 总共多少条数据 
	 */
	private long count = 0;
	
	/**
	 * 每页多少条数据
	 */
	private int pageSize = 10;
	
	private List<T> list;
	
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}
	
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	
	public long getPageTotal() {
		return (this.count + this.pageSize-1) / this.pageSize;
	}
	
	public void setPageTotal(long pageTotal) {
		this.pageTotal = pageTotal;
	}
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	

}
