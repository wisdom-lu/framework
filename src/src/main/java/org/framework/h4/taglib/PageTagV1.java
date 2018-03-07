/*  
 * @(#)PageTagV1.java V1.0 2013-11-14 上午10:32:34
 * @ org.framework.taglib
 *
 * Copyright (c) 2013, Framework All rights reserved.
 * Framework PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
 * <p>Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Framework</p>
 *
 * @author Mou Lu
 * @Date：2013-11-14 上午10:32:34
 * @version 1.0
 */
public class PageTagV1 extends TagSupport{

	private int formIndex;

	private String pagerId;
	
	private String backFunName;//0是调用，1不调用
	
	private Logger log = Logger.getLogger(this.getClass().getName());

	public PageTagV1() {

	}
	
	public int doStartTag() throws JspException {
		return (SKIP_BODY);
	}
	
	public int doEndTag() throws JspException {
		/*
		 * ModuleConfig config = (ModuleConfig)
		 * pageContext.getServletContext().getAttribute(
		 * org.apache.struts.Globals.MODULE_KEY);
		 */
		JspWriter writer = pageContext.getOut();

		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		/*
		 * HttpServletResponse response = (HttpServletResponse)
		 * pageContext.getResponse(); String root = request.getContextPath();
		 */
		HttpSession session = request.getSession();
		long pageCount = 0;
		long pageNum = 1;
		PageBean pageBean = null;
		Object obj = request.getAttribute("pageBean");
		if(obj != null){
			pageBean = (PageBean) obj;
			pageCount = pageBean.getPageTotal();
			pageNum = pageBean.getPageNow();
		}else{
			pageCount = 0;
			pageNum = 1;
		}
		
		int pageSize =pageBean.getPageSize();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div id='pager' style='background:#efefef;border:1px solid #ccc;width:99.9%;'></div>");
		sb.append("<script type='text/javascript'>");

		sb.append("function loadPager(pagerId,pageCount,formIndex,pageNum){");
		sb.append("$('#'+pagerId).pagination({");
		sb.append("total:pageCount,");
		sb.append("pageNumber:pageNum,");
		sb.append("pageSize:"+pageSize+",");
		sb.append("displayMsg:' ',");
		sb.append("showPageList:false,");
		sb.append("beforePageText:"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetagv1.doendtag.msg")+",");
		sb.append("afterPageText:"+ResourceMsg.getMsg(request,"org.framework.h4.taglib.pagetag.doendtag.total")
				+"'{pages}'"+ResourceMsg.getMsg(request, "org.framework.h4.taglib.pagetag.doendtag.nunber")+",");
		sb.append("onSelectPage:function(pageNumber, pageSize){");
		sb.append("$(this).pagination('loading');");
		sb.append("document.forms[formIndex].pagenum.value = pageNumber;");
		if(this.backFunName != null && !"".equals(this.backFunName)){
			sb.append(this.backFunName);
		}else{
			sb.append("document.forms[formIndex].submit();");
		}
		sb.append(" $(this).pagination('loaded');");
		sb.append("},");
		sb.append("buttons:[{");
		sb.append("iconCls:'icon-go',");
		sb.append("handler:function(){");
		sb.append("document.forms[formIndex].pagenum.value = $('.pagination-num').val();");
		sb.append("document.forms[formIndex].submit();");
		sb.append(" $(this).pagination('loaded');");
		sb.append("}");
		sb.append("}]");
		sb.append("});");
		sb.append("}");

		sb.append("loadPager('" + this.pagerId + "'," + pageCount * pageSize
				+ "," + this.formIndex + "," + pageNum + ");");
		sb.append("</script>");
		try {
			writer.print(sb.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return (SKIP_BODY);
	}

	public int getFormIndex() {
		return formIndex;
	}

	public void setFormIndex(int formIndex) {
		this.formIndex = formIndex;
	}

	public String getPagerId() {
		return pagerId;
	}

	public void setPagerId(String pagerId) {
		this.pagerId = pagerId;
	}

	public String getBackFunName() {
		return backFunName;
	}

	public void setBackFunName(String backFunName) {
		this.backFunName = backFunName;
	}
}
