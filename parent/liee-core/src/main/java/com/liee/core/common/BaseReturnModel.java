package com.liee.core.common;

/**
 * ajax请求返回前端的通用对象
 * 
 * @author Administrator
 *
 */
public class BaseReturnModel {
	
	/**
	 * 是否成功
	 */
	private Boolean success;
	
	/**
	 * 错误消息/提示消息
	 */
	private String errMsg;
	
	/**
	 * 返回内容
	 */
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
