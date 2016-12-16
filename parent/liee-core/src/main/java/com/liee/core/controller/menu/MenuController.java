package com.liee.core.controller.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.dao.SysMenu;
import com.liee.core.dao.SysRoleMenu;
import com.liee.core.service.menu.MenuService;
import com.liee.core.utils.StringUtil;

@Controller
@RequestMapping("/menu") 
public class MenuController extends BaseController{
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/saveOrUpdate", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel saveOrUpdate(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		int parentId = StringUtil.stringToInt(getRequestParameter(request, "parentId"));
		int level = StringUtil.stringToInt(getRequestParameter(request, "level"));
		String menuName = StringUtil.nullToString(getRequestParameter(request, "menuName"));
		String url = StringUtil.nullToString(getRequestParameter(request, "url"));
		int displayOrder = StringUtil.stringToInt(getRequestParameter(request, "displayOrder"));
		
		SysMenu menu = new SysMenu();
		menu.setId(id);
		menu.setParentId(parentId);
		menu.setLevel(level);
		menu.setMenuName(menuName);
		menu.setUrl(url);
		menu.setDisplayOrder(displayOrder);
		
		
		if(StringUtil.isEmpty(menuName)){
			rm.setSuccess(false);
			rm.setErrMsg("菜单名称不能为空");
			return rm;
		}
		
		try{
			menuService.saveOrUpdate(menu);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		
		
		return rm;
	}
	
	
	@RequestMapping(value = "/getMenu", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List getMenu(HttpServletRequest request) {

		List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
		int userId = (Integer)getSessionAttribute(request,"userId");
		
		// 查询该用户有权限的菜单
		menuList = menuService.getUserMenu(userId); 
		
	/*	Map<String,Object> newAdd = new HashMap<String, Object>();
		newAdd.put("parentMenuName", "临时");
		
		List<Map<String,Object>> subMenuList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> subMenu = new HashMap<String, Object>();
		subMenu.put("menuName", "角色管理");
		subMenu.put("menuId", 0);
		subMenu.put("url", "system/roleauth/sysRole.jsp");
		subMenu.put("authLevel", 2);
		
		subMenuList.add(subMenu);
		
		Map<String,Object> subMenu2 = new HashMap<String, Object>();
		subMenu2.put("menuName", "用户管理");
		subMenu2.put("menuId", 1);
		subMenu2.put("url", "system/user/sysUser.jsp");
		subMenu2.put("authLevel", 2);
		subMenuList.add(subMenu2);
		
		Map<String,Object> subMenu3 = new HashMap<String, Object>();
		subMenu3.put("menuName", "菜单管理");
		subMenu3.put("menuId", 2);
		subMenu3.put("url", "system/menu/sysMenu.jsp");
		subMenu3.put("authLevel", 3);
		subMenuList.add(subMenu3);
		
		newAdd.put("subMenuList", subMenuList);
		
		menuList.add(newAdd);*/
		
		return menuList;
	}
	
	/**
	 * 查询所有的菜单 ,用于菜单管理模块
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllMenu", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List menuTree(HttpServletRequest request) {
		List<SysMenu> menuList = menuService.getAllMenu(); 
		//手动添加根节点
		SysMenu root = new SysMenu();
		root.setId(0);
		root.setParentId(-1);
		root.setMenuName("所有菜单");
		menuList.add(0, root); 
		return menuList;
	}
	
	/**
	 * 根据ID查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getById", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel getById(HttpServletRequest request) {
		
		BaseReturnModel result = new BaseReturnModel();
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		SysMenu menu = new SysMenu();
		menu.setId(id);
		try {
			menu.setLoggerOn(true);
			menu.where(SysMenu.ID.EQ(id));
			menu = menu.queryById();
			result.setObj(menu);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMsg("查询菜单失败");
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel delete(HttpServletRequest request) {
		
		BaseReturnModel result = new BaseReturnModel();
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		SysMenu menu = new SysMenu();
		try {
			menu.where(SysMenu.ID.EQ(id));
			menu.delete();
			// 删除菜单与角色的关联关系
			SysRoleMenu rm = new SysRoleMenu();
			rm.where(SysRoleMenu.MENUID.EQ(id));
			rm.delete();
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMsg("删除菜单失败");
		}
		return result;
	}
	
}
