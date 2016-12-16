package com.liee.core.controller;

import javax.servlet.http.HttpServletRequest;

import com.liee.core.log.Logger;


public class BaseController {
	protected static final Logger logger = Logger.getLogger();
	
	
	public Object getSessionAttribute(HttpServletRequest request,String attrName){
		return request.getSession().getAttribute(attrName);
	}
	
	public String getRequestParameter(HttpServletRequest request,String paramName){
		return (String)request.getParameter(paramName);
	}
}
