package com.liee.core.service.role;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jdao.base.QueryDao;
import com.jdao.base.Where;
import com.liee.core.common.BasePageModel;
import com.liee.core.dao.SysRole;
import com.liee.core.dao.SysRoleMenu;
import com.liee.core.exception.BaseException;
import com.liee.core.service.BaseService;
import com.liee.core.utils.StringUtil;

@Service("roleService")
public class RoleService  extends BaseService {

	/**
	 * 分页查询
	 * @param sr
	 * @param page
	 * @return
	 */
	public BasePageModel<SysRole> queryPage(SysRole sr,BasePageModel<SysRole> page ){
		
		SysRole cq = new SysRole();
		
		// 查询条件
		List<Where> whereList = new ArrayList<Where>();
		if(!StringUtil.isEmpty(sr.getRoleName())){
			whereList.add(SysRole.ROLENAME.LIKE(sr.getRoleName()));
		}
		
		sr.where(whereList.toArray(new Where[whereList.size()]));
		cq.where(whereList.toArray(new Where[whereList.size()]));
		
		try {
			// 数据
			sr.limit(page.getPageSize()*(page.getPageNum()-1), page.getPageSize());
			List<SysRole> list = sr.query();
			
			// 总条数
			QueryDao qd = cq.query(SysRole.ID.count());
			Long totalCount = (Long)qd.fieldValue(1);
			
			page.setTotal(totalCount.intValue());
			page.setRows(list);
			
		} catch (Exception e) {
			log.error("分页查询错误",e);
			throw new BaseException("分页查询错误", e);
		}
		
		return page;
	}
	
	
	/**
	 * 保存或者更新
	 * @param role
	 */
	public void saveOrUpdate(SysRole role){
		
		// 根据名称判断唯一
		try {
			
			SysRole old = new SysRole();
			old.where(SysRole.ROLENAME.EQ(role.getRoleName()));
			if(role.getId()!=0){  // 更新
				old.where(SysRole.ID.NEQ(role.getId()));
			}
			List<SysRole> existList = old.query();
			if(existList!=null && existList.size()>0){
				throw new BaseException("该角色名称已经存在！");
			}
			
			if(role.getId()!=0){  //更新
				role.where(SysRole.ID.EQ(role.getId()));
				role.update();
			}else{  // 新增
				role.save();
			}
			
		} catch (Exception e) {
			log.error("保存角色失败："+e.getMessage(),e);
			throw (BaseException)e;   // 抛出异常到controller
		}
	}
	
	/**
	 * 根据id删除角色
	 * @param id
	 */
	public void delete(int id){
		try {
			SysRole old = new SysRole();
			old.where(SysRole.ID.EQ(id));
			old.delete();
		} catch (SQLException e) {
			log.error("删除角色失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());   // 抛出异常到controller
		}
	}
	
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<SysRole> getAllRole(){
		List<SysRole> roleList = new ArrayList<SysRole>();
		
		SysRole q = new SysRole();
		q.sort(SysRole.ID.asc());
		try {
			roleList = (List<SysRole>)q.query();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleList;
	}
	
	
	/**
	 * 根据用户查询角色
	 * @param userId
	 * @return
	 */
	public List<SysRole> getRoleListByUserId(int userId){
		List<SysRole> roleList = new ArrayList<SysRole>();
		StringBuilder sb = new StringBuilder("select a.id,a.role_name,a.remark from sys_role a ,sys_user_role b where a.id = b.role_id and b.user_id = ?");
		try {
			QueryDao qd = new QueryDao(sb.toString(), userId);
			List<QueryDao> daoList = qd.queryDaoList();
			if(daoList!=null && daoList.size()>0){
				for (QueryDao dao : daoList) {
					SysRole r = new SysRole();
					r.setId(dao.field2Int("id"));
					r.setRoleName(dao.field2String("role_name"));
					r.setRemark(dao.field2String("remark"));
					roleList.add(r);
				}
			}
		} catch (SQLException e) {
			log.info("查询用户角色失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());   // 抛出异常到controller
		}
	
		return  roleList;
	}
	
	/**
	 * 通过角色id查询菜单集合
	 * @return
	 */
	public List<Map<String,Object>> getMenuListByRoleId(int roleId){
		List<Map<String,Object>> roleList = new ArrayList<Map<String,Object>>();
		StringBuilder sb = new StringBuilder("select b.id, a.id menu_id,concat(ifnull(c.menu_name,''),' > ',a.menu_name) menu_name,b.auth_level from sys_role_menu b left join sys_menu a on  a.id = b.menu_id  left join  sys_menu c on a.parent_id = c.id where b.role_id = ?");
		try {
			QueryDao qd = new QueryDao(sb.toString(), roleId);
			List<QueryDao> daoList = qd.queryDaoList();
			if(daoList!=null && daoList.size()>0){
				for (QueryDao dao : daoList) {
					Map<String,Object> entity = new HashMap<String, Object>();
					entity.put("id", dao.field2Int("id"));
					entity.put("menuName", dao.field2String("menu_name"));
					entity.put("menuId", dao.field2Int("menu_id"));
					entity.put("authLevel", dao.field2Int("auth_level"));
					
					roleList.add(entity);
				}
			}
		} catch (SQLException e) {
			log.info("查询角色菜单失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());   // 抛出异常到controller
		}
	
		return  roleList;
	}
	
	
	/**
	 * 保存角色对应的菜单集合   先删后插 
	 * @param roleId
	 * @param list
	 */
	public void saveRoleMenu(int roleId,List<SysRoleMenu> list){
		try {
			// 先删除
			SysRoleMenu rm = new SysRoleMenu();
			rm.where(SysRoleMenu.ROLEID.EQ(roleId));
			rm.delete();
			// 再保存
			if(list!=null && list.size()>0){
				for (SysRoleMenu roleMenu : list) {
					roleMenu.setRoleId(roleId);
					if(roleMenu.getMenuId()!=0){
						roleMenu.save();
					}
				}
			}
		} catch (SQLException e) {
			log.error("保存用户角色失败："+e.getMessage(),e);
			throw new BaseException(e.getMessage());   // 抛出异常到controller
		}
		
		
	}
	
}
