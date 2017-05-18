/*  
 * @(#)PageTagV1.java V1.0 2013-11-14 上午10:32:34
 * @ org.framework.taglib
 *
 * Copyright (c) 2013, Wafersystems All rights reserved.
 * Wafer PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.framework.h4.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.framework.h4.utils.PageBean;
import org.framework.h4.utils.ResourceMsg;

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
 * @Date：2013-11-14 上午10:32:34
 * @version 1.0
 */
public class PageTag extends TagSupport{

	private String id;
	
	private String cbfName;
	
	private int pageLimitShow;
	
	private Logger log = Logger.getLogger(this.getClass().getName());

	public PageTag() {

	}
	
	public int doStartTag() throws JspException {
		return (SKIP_BODY);
	}
	
	public int doEndTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		HttpSession session = request.getSession();
		Object obj = request.getAttribute(this.id);
		PageBean pageBean = null;
		if(obj != null ){
			pageBean = (PageBean)obj;
		}else{
			pageBean = new PageBean();
		}
		if(this.cbfName == null || "".equals(this.cbfName)){
			this.cbfName = "";
		}
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div class='fixed-table-pagination' >");
		sb.append("<div  style='float:left;height:53px;line-height:53px;' >"
				+ResourceMsg.getMsg(request,"org.framework.h4.taglib.pagetag.doendtag.total")
				+pageBean.getCount()+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetag.doendtag.nunber"));
		
		//每页多少条
		sb.append("&nbsp;");
		sb.append("<select style='width: 100px;' name='pageSize' onchange=\"Pager.goPager('"+this.id+"',1,"+pageBean.getPageTotal()+",'"+this.cbfName+"')\">' >");
		sb.append("<option value='10' "+(pageBean.getPageSize()== 10 ? "selected" : "")+" >"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetagv1.doendtag.pagenumber")+"10"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetag.doendtag.nunber")+"</option>");
		sb.append("<option value='20' "+(pageBean.getPageSize()== 20 ? "selected" : "")+" >"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetagv1.doendtag.pagenumber")+"20"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetag.doendtag.nunber")+"</option>");
		sb.append("<option value='50' "+(pageBean.getPageSize()== 50 ? "selected" : "")+" >"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetagv1.doendtag.pagenumber")+"50"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetag.doendtag.nunber")+"</option>");
		sb.append("<option value='100' "+(pageBean.getPageSize()== 100 ? "selected" : "")+" >"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetagv1.doendtag.pagenumber")+"100"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetag.doendtag.nunber")+"</option>");
		sb.append("<option value='200' "+(pageBean.getPageSize()== 200 ? "selected" : "")+" >"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetagv1.doendtag.pagenumber")+"200"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetag.doendtag.nunber")+"</option>");
		sb.append("</select>");
		
		sb.append("</div>");
		sb.append("<div  style='float:right;'>");
		sb.append("<div class='pull-right pagination' style='margin-top:0px;'>");
		sb.append("<input type='hidden' name='pageNow' id='"+this.id+"_pageNow' value='"+pageBean.getPageNow()+"'>");
		sb.append("<ul class='pagination'>");
		if(pageBean.getPageNow() <= 1){
			//如果是首页则禁用首页按钮
			sb.append("<li class='page-first'><a class=\"vd_grey\" href=\"javascript:void(0);\">&lt;&lt;</a></li>");
			sb.append("<li class='page-pre'><a class=\"vd_grey\" href=\"javascript:void(0);\">&lt;</a></li>");
		}else{
			sb.append("<li class='page-first'><a href=\"javascript:Pager.goPager('"+this.id+"',1,3,'"+this.cbfName+"')\">&lt;&lt;</a></li>");
			sb.append("<li class='page-pre'><a href=\"javascript:Pager.goPager('"+this.id+"',"+(pageBean.getPageNow()-1)+","+pageBean.getPageTotal()+",'"+this.cbfName+"')\">&lt;</a></li>");
		}
		
		int[] pageShow =  this.getInterval(pageBean, this.getPageLimitShow());
		if(pageShow[0] > 1){
			sb.append("<li class='page-number'><a href=\"javascript:void(0);\">...</a></li>");
		}
		//判断页数，显示几个
		for (int i = pageShow[0]; i <= pageShow[1]; i++) {
			if(pageBean.getPageNow() == i){
				sb.append("<li class='page-number active'><a href=\"javascript:Pager.goPager('"+this.id+"',"+pageBean.getPageNow()+","+pageBean.getPageTotal()+",'"+this.cbfName+"')\">"+pageBean.getPageNow()+"</a></li>");
			}else{
				sb.append("<li class='page-number'><a href=\"javascript:Pager.goPager('"+this.id+"',"+i+","+pageBean.getPageTotal()+",'"+this.cbfName+"')\">"+i+"</a></li>");
			}
		}
		if(pageShow[1] < pageBean.getPageTotal()){
			sb.append("<li class='page-number'><a href=\"javascript:void(0);\">...</a></li>");
		}
		
		if(pageBean.getPageNow() >= pageBean.getPageTotal()){
			//则禁用尾页按钮
			sb.append("<li class='page-next'><a class=\"vd_grey\" href=\"javascript:void(0);\">&gt;</a></li>");
			sb.append("<li class='page-last'><a class=\"vd_grey\" href=\"javascript:void(0);\">&gt;&gt;</a></li>");
		}else{
			sb.append("<li class='page-next'><a href=\"javascript:Pager.goPager('"+this.id+"',"+(pageBean.getPageNow()+1)+","+pageBean.getPageTotal()+",'"+this.cbfName+"')\">&gt;</a></li>");
			sb.append("<li class='page-last'><a href=\"javascript:Pager.goPager('"+this.id+"',"+pageBean.getPageTotal()+","+pageBean.getPageTotal()+",'"+this.cbfName+"')\">&gt;&gt;</a></li>");
		}
		
		sb.append("</ul>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div><!-- end 分页 -->");
		try {
			writer.print(sb.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return (SKIP_BODY);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCbfName() {
		return cbfName;
	}

	public void setCbfName(String cbfName) {
		this.cbfName = cbfName;
	}
	
	public int getPageLimitShow() {
		if(pageLimitShow ==0){
			pageLimitShow = 10;
		}
		return pageLimitShow;
	}

	public void setPageLimitShow(int pageLimitShow) {
		this.pageLimitShow = pageLimitShow;
	}

	/**
	 * Description:根据分页信息显示分页上的页数
	 * 
	 * @param PageBean pageBean
	 * @param upperLimit
	 * @return  int[]    
	 * @throws
	 */
	public int[] getInterval(PageBean pageBean,int upperLimit){
		int neHalf = (int)Math.ceil(upperLimit/2);
		int start = (int)(pageBean.getPageNow() >= neHalf ? Math.max(pageBean.getPageNow() - neHalf, 1) : 1);
		int end = (int) (pageBean.getPageNow() >= neHalf ? Math.min(pageBean.getPageNow() + neHalf, pageBean.getPageTotal()) : Math.min(upperLimit, pageBean.getPageTotal()));
		return new int[]{start,end};
	}
	
	public static void main(String[] args) {
		PageBean pageBean = new PageBean();
		pageBean.setPageNow(6);
		pageBean.setCount(10000);
		//double[] d = PageTagV1.getInterval(pageBean, 5);
		//System.out.println(d);
	}

}
