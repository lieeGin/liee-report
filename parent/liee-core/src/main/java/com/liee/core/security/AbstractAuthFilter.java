package com.liee.core.security;

import com.liee.core.common.BaseReturnModel;
import com.liee.core.dao.SysUser;


public abstract class AbstractAuthFilter {

	
	/**
	 * 验证 
	 * @return
	 */
	public abstract BaseReturnModel  validateAuth(SysUser user);
	
}
