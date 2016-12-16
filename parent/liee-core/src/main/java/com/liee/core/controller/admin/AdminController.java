package com.liee.core.controller.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.dao.SysUser;
import com.liee.core.security.AbstractAuthFilter;
import com.liee.core.security.AuthenticationManager;
import com.liee.core.service.common.CommonService;
import com.liee.core.utils.StringUtil;

@Controller
@RequestMapping("/admin") 
public class AdminController extends BaseController{
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired 
	public CommonService commonService;
	
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView loginPage(HttpServletRequest request) {
		commonService.freshTemplate(request);
		ModelAndView mv = new ModelAndView();
		String sessionTemplate = (String)request.getSession().getAttribute("templateName");
		mv.setViewName("/"+sessionTemplate+"Template/login");
		return mv;
	}
	
	

	@RequestMapping(value = "/index", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView index(HttpServletRequest request) {
		commonService.freshTemplate(request);
		ModelAndView mv = new ModelAndView();
		String sessionTemplate = (String)request.getSession().getAttribute("templateName");
		mv.setViewName("/"+sessionTemplate+"Template/index");
		return mv;
	}
	
	/**
	 * 登录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userLogin", method={RequestMethod.GET, RequestMethod.POST})   
	public BaseReturnModel login(HttpServletRequest request){
		BaseReturnModel result = new BaseReturnModel();
		
		String userName =  StringUtil.nullToString(request.getParameter("userName"));
		String password =  StringUtil.nullToString(request.getParameter("password"));
		
		SysUser user = new SysUser();
		user.setUserName(userName);
		user.setPassword(password); 
		
		List<AbstractAuthFilter> filterList = manager.getFilterList();
		for (AbstractAuthFilter filter : filterList) {
			result = filter.validateAuth(user);
			if(!result.getSuccess()){
				break;
			}
		}
		
		if(result.getSuccess()){
			user.where(SysUser.USERNAME.EQ(userName));
			try{
				List<SysUser> uList = user.query();
				if(uList!=null && uList.size()>0){
					SysUser dbUser = uList.get(0);
					request.getSession().setAttribute("userId", dbUser.getId());
					request.getSession().setAttribute("userName", dbUser.getUserName());
					request.getSession().setAttribute("displayName", dbUser.getDisplayName());
					// 更新用户的最后登录时间
					dbUser.setLastLoginTime(new Date());
					dbUser.where(SysUser.ID.EQ(dbUser.getId()));
					dbUser.update();
				}
			}catch(Exception e){
				logger.error("查询用户错误", e);
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * 退出
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/logout",method={RequestMethod.GET, RequestMethod.POST})  
    public BaseReturnModel logout(HttpServletRequest request){
		request.getSession().setAttribute("displayName",null);
		request.getSession().setAttribute("userName",null);
		request.getSession().setAttribute("userId",null);
		BaseReturnModel result = new BaseReturnModel();
		result.setSuccess(true);
        return result;  
    }  
	
}
