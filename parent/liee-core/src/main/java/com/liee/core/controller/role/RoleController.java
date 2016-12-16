package com.liee.core.controller.role;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liee.core.common.BasePageModel;
import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.dao.SysRole;
import com.liee.core.dao.SysRoleMenu;
import com.liee.core.log.Logger;
import com.liee.core.service.role.RoleService;
import com.liee.core.utils.ArrayUtil;
import com.liee.core.utils.StringUtil;

@Controller
@RequestMapping("/role") 
public class RoleController extends BaseController{
	
	Logger log = Logger.getLogger();
	
	@Autowired
	private RoleService roleService;
	
	
	@RequestMapping(value = "/saveOrUpdate", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel saveOrUpdate(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		String roleName = getRequestParameter(request, "roleName");
		String remark = getRequestParameter(request, "remark");
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		SysRole role = new SysRole();
		role.setRoleName(roleName);
		role.setRemark(remark);
		role.setId(id);
		
		if(StringUtil.isEmpty(roleName)){
			rm.setSuccess(false);
			rm.setErrMsg("角色名称不能为空");
			return rm;
		}
		
		try{
			roleService.saveOrUpdate(role);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/pageQuery", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel pageQuery(HttpServletRequest request,SysRole sr) {
		
		BaseReturnModel result = new BaseReturnModel();
		BasePageModel<SysRole> page = new BasePageModel<SysRole>();
		// 分页查询参数
		page.setPageSize(StringUtil.stringToInt(getRequestParameter(request, "rows")));
		page.setPageNum(StringUtil.stringToInt(getRequestParameter(request, "page")));
		try{
			page = roleService.queryPage(sr, page);
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
			roleService.delete(id);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 查询所有角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllRole", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<SysRole> getAllRole(HttpServletRequest request) {
		List<SysRole> roleList = roleService.getAllRole(); 
		return roleList;
	}
	
	
	/**
	 * 通过用户id查询该用户所分配的角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserRole", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<SysRole> getUserRole(HttpServletRequest request){
		
		int userId = StringUtil.stringToInt(getRequestParameter(request, "userId"));
		List<SysRole> roleList = roleService.getRoleListByUserId(userId); 
		return roleList;
	}
	
	
	/**
	 * 获取角色对应的菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getRoleMenu", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<Map<String,Object>> getRoleMenu(HttpServletRequest request){
		
		int roleId = StringUtil.stringToInt(getRequestParameter(request, "roleId"));
		List<Map<String,Object>> menuList = roleService.getMenuListByRoleId(roleId); 
		return menuList;
	}
	
	
	
	@RequestMapping(value = "/saveRoleMenu", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel saveRoleMenu(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		int roleId = StringUtil.stringToInt(getRequestParameter(request, "roleId"));
		String allRecord = StringUtil.nullToString(getRequestParameter(request, "allRecord"));
		try{
			List<SysRoleMenu> list= (List<SysRoleMenu>)ArrayUtil.getListFromJson(allRecord, SysRoleMenu.class);
			roleService.saveRoleMenu(roleId,list);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	

}
