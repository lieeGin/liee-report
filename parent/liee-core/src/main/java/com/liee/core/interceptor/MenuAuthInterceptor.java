package com.liee.core.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jdao.base.QueryDao;
import com.liee.core.dao.SysRoleMenu;
import com.liee.core.dao.SysUserRole;
import com.liee.core.singleton.MemeryManager;
import com.liee.core.utils.StringUtil;

/**
 * 针对菜单的操作权限拦截器   
 * @author lieeGin
 *
 */
public class MenuAuthInterceptor implements HandlerInterceptor {

	//public static  Map<Integer,Map<Integer,Integer>>  authMap =  new HashMap<Integer,Map<Integer,Integer>>();
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		 String path = request.getRequestURL().toString();
         int menuId = StringUtil.stringToInt(request.getParameter("menuId"));
         int userId = (Integer)request.getSession().getAttribute("userId");
         
         // 拦截  后台请求，判断是否需要检查权限
         if(menuId!=0 && userId!=0){
        	 if(!checkAuth(userId,menuId,path,request)){  // 没有权限
        		 if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //如果是ajax请求响应头会有，x-requested-with  
     	            response.setHeader("sessionstatus", "hasNoAccess");//在响应头设置session状态  没有操作权限  
     	            
     	            response.getWriter().write("对不起，你没有该操作的权限");
     	        }
        	 }else{
        		 return true;
        	 }
         }else{
        	 return true;
         }
		
		return false;
	}
	
	
	  
    /**
     * 检查用户权限
     * @param userId
     * @param menuId
     * @param path
     * @return
     */
    public boolean checkAuth(int userId,int menuId,String path,HttpServletRequest request){
   	 //请求为 一下几种情况，才检查是否有权限   saveOrUpdate  save  update  delete remove;
   		 // session 中获取该用户对应该菜单的权限
   		 Map<Integer,Map<Integer,Integer>>  authMap = MemeryManager.getInstance().getAuthMap() ;//(Map<Integer,Map<Integer,Integer>>)request.getSession().getAttribute("baseAuthMap");
   		 if(authMap==null){
   			 authMap = new HashMap<Integer,Map<Integer,Integer>>();
   			 request.getSession().setAttribute("baseAuthMap", authMap);
   		 }
   		 //获取用户对应菜单的权限
   		 Map<Integer,Integer> userMenuMap  = authMap.get(userId);
   		 if(userMenuMap==null){
   			 userMenuMap = new HashMap<Integer,Integer>();
   			 authMap.put(userId, userMenuMap);
   		 }
   		 
		 Integer menuAuth = userMenuMap.get(menuId);
		 if(menuAuth==null || menuAuth==0){
			 // 从数据库里面查询
			 menuAuth = getUserMenuAuth(userId,menuId);
			 userMenuMap.put(menuId, menuAuth);
		 }
		 
		 if(menuAuth!=2){  // 不是读写权限
			 return false;
		 }
		 return true;
    }
    
    
    /**
     * 根据用户id 和菜单id 查询权限等级
     * @return
     */
    public int getUserMenuAuth(int userId,int menuId){
   	int result = 0;
   	 try {
   		 SysUserRole ur=  new SysUserRole();
       	 ur.where(SysUserRole.USERID.EQ(userId));
			 List<SysUserRole> urList = ur.query();
			 List<Integer> roleIdList = new ArrayList<Integer>();
			 if(urList!=null && urList.size()>0){
				 for (SysUserRole sysUserRole : urList) {
					 roleIdList.add(sysUserRole.getRoleId());
				}
			 }else{
				 // 未分配任何角色
				 return result;
			 }
			 
			 SysRoleMenu rm = new SysRoleMenu();
			 rm.where(SysRoleMenu.MENUID.EQ(menuId),SysRoleMenu.ROLEID.IN(roleIdList.toArray(new Integer[roleIdList.size()])));
			 QueryDao resultQd = rm.query(SysRoleMenu.AUTHLEVEL.max());
			 
			 if(resultQd.fieldValue(1)==null){
				 // 角色没有该菜单的权限
				 return result;
			 }
			 
			 result =  (Integer)resultQd.fieldValue(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
   	 
    }

}
