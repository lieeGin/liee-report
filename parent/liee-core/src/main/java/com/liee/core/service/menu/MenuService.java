package com.liee.core.service.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jdao.base.QueryDao;
import com.liee.core.dao.SysMenu;
import com.liee.core.exception.BaseException;
import com.liee.core.service.BaseService;
import com.liee.core.utils.StringUtil;

@Service("menuService")
public class MenuService extends BaseService {

	
	/**
	 * 查询用户的菜单
	 * 目前只支持两级菜单   TODO 扩展支持无限多级
	 * @param userId
	 * @return
	 *  map{
	 *  	parentMenuName, 
	 *      subMenuList[
	 *      	{
	 *      		menuName,
	 *      		menuId,
	 *      		url,
	 *      		authLevel
	 *      	}
	 *      ]
	 *  }
	 */
	public List<Map<String,Object>> getUserMenu(int userId){
		List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
		// 查询该用户有权限的菜单
		try {
			QueryDao queryDao = new QueryDao("select "
					+" ifnull(pm.id,0) parent_menu_id, pm.menu_name parent_menu_name ,m.id menu_id ,m.menu_name,m.url,rm.auth_level "
					+ " from sys_user u left join  sys_user_role ur on u.id = ur.user_id"
					+ " left join sys_role_menu rm on ur.role_id = rm.role_id"
					+ " left join sys_menu m on rm.menu_id = m.id  "
					+ " left join sys_menu pm on m.parent_id = pm.id"
					+" where u.id = ? and m.menu_name is not null order by pm.display_order, m.display_order , m.id ", userId);
			// 所有菜单列表，包含一级和二级菜单
			List<QueryDao> result = queryDao.queryDaoList();
			
			if(result!=null && result.size() > 0){
				
				Map<Integer,Map<String,Object>> parentMap = new HashMap<Integer, Map<String,Object>>();
				for (QueryDao dao : result) {
					if(dao.fieldValue("menu_name")==null || dao.field2Int("parent_menu_id")==0){   //  不存在名称的记录  或者是 一级菜单（一级菜单是通过二级菜单获取的）
						continue;
					}
					int parentId = dao.field2Int("parent_menu_id");  // 父菜单
					
					Map<String,Object> menuObj = parentMap.get(parentId);
					if(menuObj==null ){   //  一级菜单
						menuObj = new HashMap<String,Object>();
						menuObj.put("parentMenuName",dao.field2String("parent_menu_name"));
						menuObj.put("subMenuList", new ArrayList<Map<String,String>>());
						
						menuList.add(menuObj);
						
						// 记录当一级菜单
						parentMap.put(parentId,menuObj);
					}
					//一级菜单下的二级菜单
					List<Map<String,String>> subMenuList = (ArrayList<Map<String,String>>)menuObj.get("subMenuList");
					
					Map<String,String> subMenuObj = new HashMap<String, String>();
					subMenuObj.put("menuName",dao.field2String("menu_name"));
					subMenuObj.put("menuId",dao.field2String("menu_id"));
					subMenuObj.put("url",dao.field2String("url"));
					subMenuObj.put("authLevel",dao.field2String("auth_level"));
					
					int index = -1;
					String subMenuId = menuObj.get("subMenuId")==null?"":((String)menuObj.get("subMenuId"));
					if(!StringUtil.isEmpty(subMenuId)){
						// 检查该菜单是否已经添加到集合中
						String[] subMenuIdArr = subMenuId.split(",");
						Arrays.sort(subMenuIdArr);  // 排序  
						index = Arrays.binarySearch(subMenuIdArr, dao.field2String("menu_id"));    //搜索该id  二分查找
					}
					
					if(index < 0 ){  // 不存在 ，添加到集合中
						subMenuList.add(subMenuObj);
						menuObj.put("subMenuId",subMenuId+dao.field2String("menu_id")+",");
					}
					
				}
			}
			
		} catch (SQLException e) {
			log.error("查询菜单错误", e);
			throw new BaseException(e.getMessage());
		}
		
		return menuList;
	}
	
	/**
	 * 查询所有菜单
	 * @return
	 */
	public List<SysMenu> getAllMenu(){
		List<SysMenu> menuList = new ArrayList<SysMenu>();
		
		SysMenu q = new SysMenu();
		q.sort(SysMenu.LEVEL.asc(),SysMenu.DISPLAYORDER.asc());
		try {
			menuList = (List<SysMenu>)q.query();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return menuList;
	}
	
	
	/**
	 * 保存
	 * @param menu
	 */
	public void saveOrUpdate(SysMenu menu){
		// 根据名称判断唯一
		try {
			
			SysMenu old = new SysMenu();
			old.where(SysMenu.MENUNAME.EQ(menu.getMenuName()));
			if(menu.getId()!=0){  // 更新
				old.where(SysMenu.ID.NEQ(menu.getId()));
			}
			List<SysMenu> existList = old.query();
			if(existList!=null && existList.size()>0){
				throw new BaseException("该菜单名称已经存在！");
			}
			
			if(menu.getId()!=0){  //更新
				menu.where(SysMenu.ID.EQ(menu.getId()));
				menu.update();
			}else{  // 新增
				// 设置level
				if(menu.getParentId()!=0){
					SysMenu parent = new SysMenu();
					parent.where(SysMenu.ID.EQ(menu.getParentId()));
					parent = parent.queryById();
					if(parent.getLevel()>1){
						throw new BaseException("目前只支持两级菜单！");
					}else{
						menu.setLevel(parent.getLevel()+1); 
					}
				}else{
					menu.setLevel(1); 
				}
				menu.save();
			}
			
		} catch (Exception e) {
			log.error("保存菜单失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());   // 抛出异常到controller
		}
	}
	
}
