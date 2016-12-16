package com.liee.core.security;

import java.util.List;

import com.liee.core.common.BaseReturnModel;
import com.liee.core.dao.SysUser;
import com.liee.core.log.Logger;
import com.liee.core.utils.Md5Util;

public class PasswdFilter extends AbstractAuthFilter {

	protected Logger logger = Logger.getLogger();
	
	@Override
	public BaseReturnModel validateAuth(SysUser user) {
		
		BaseReturnModel result = new BaseReturnModel();
		result.setSuccess(false);
		
		SysUser query = new SysUser();
		query.where(SysUser.USERNAME.EQ(user.getUserName()));
		
		try {
			List<SysUser> uList = query.query();
			if(uList!=null && uList.size()>0){
				SysUser entity = uList.get(0);
				if(!entity.getPassword().equals(Md5Util.encrypt(user.getPassword()))){
					result.setSuccess(false);
					result.setErrMsg("用户密码错误！");
				}else{
					result.setSuccess(true);
				}
			}else{
				result.setSuccess(false);
				result.setErrMsg("该用户不存在！");
			}
			
		} catch (Exception e) {
			logger.error("验证错误", e);
			e.printStackTrace();
		}
	
		return result;
	}

}
