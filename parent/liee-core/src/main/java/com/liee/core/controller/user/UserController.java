package com.liee.core.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liee.core.common.BasePageModel;
import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.dao.SysUser;
import com.liee.core.service.user.UserService;
import com.liee.core.utils.StringUtil;

@Controller
@RequestMapping("/user") 
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/saveOrUpdate", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel saveOrUpdate(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		String userName = StringUtil.nullToString(getRequestParameter(request, "userName"));
		String displayName = StringUtil.nullToString(getRequestParameter(request, "displayName"));
		String mobilePhone = StringUtil.nullToString(getRequestParameter(request, "mobilePhone"));
		String email = StringUtil.nullToString(getRequestParameter(request, "email"));
		int sex = StringUtil.stringToInt(getRequestParameter(request, "sex"));
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		
		SysUser user = new SysUser();
		user.setUserName(userName);
		user.setDisplayName(displayName);
		user.setMobilePhone(mobilePhone);
		user.setEmail(email);
		user.setSex(sex);
		user.setId(id);
		try{
			userService.saveOrUpdate(user);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/pageQuery", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel pageQuery(HttpServletRequest request,SysUser sr) {
		
		BaseReturnModel result = new BaseReturnModel();
		BasePageModel<SysUser> page = new BasePageModel<SysUser>();
		// 分页查询参数
		page.setPageSize(StringUtil.stringToInt(getRequestParameter(request, "rows")));
		page.setPageNum(StringUtil.stringToInt(getRequestParameter(request, "page")));
		try{
			page = userService.queryPage(sr, page);
			result.setObj(page);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setErrMsg(e.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel delete(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		try{
			userService.delete(id);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
	
	/**
	 * 保存用户的角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveUserRoles", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel saveUserRoles(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		int userId = StringUtil.stringToInt(getRequestParameter(request, "userId"));
		String roleIds = StringUtil.nullToString(getRequestParameter(request, "roleIds"));
		
		try{
			userService.saveUserRoles(userId,roleIds);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
	
	
	/**
	 * 修改用户密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateUserPwd", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel updateUserPwd(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		String oldPwd = StringUtil.nullToString(getRequestParameter(request, "userOldPwd"));
		String newPwd = StringUtil.nullToString(getRequestParameter(request, "userPwd"));
		
		try{
			userService.updatePassword(userId.intValue(),oldPwd,newPwd);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
}
