package com.liee.core.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-11-24 14:12:18  dao for table sys_role_menu
 */
public class SysRoleMenu extends Table<SysRoleMenu> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "sys_role_menu";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**role_id*/
	public final static Fields ROLEID = new Fields("`role_id`");
	/**menu_id*/
	public final static Fields MENUID = new Fields("`menu_id`");
	/**auth_level*/
	public final static Fields AUTHLEVEL = new Fields("`auth_level`");

	private int id = 0;
	private int roleId = 0;
	private int menuId = 0;
	private int authLevel = 0;

	public SysRoleMenu(){
		super(TABLENAME_,SysRoleMenu.class);
		super.setFields(ID,ROLEID,MENUID,AUTHLEVEL);
	}

	public SysRoleMenu(String tableName4sharding){
		super(tableName4sharding,SysRoleMenu.class);
		super.setFields(ID,ROLEID,MENUID,AUTHLEVEL);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
	}
	public int getRoleId(){
		return this.roleId;
	}

	public void setRoleId(int roleId){
		fieldValueMap.put(ROLEID, roleId);
		 this.roleId=roleId;
	}
	public int getMenuId(){
		return this.menuId;
	}

	public void setMenuId(int menuId){
		fieldValueMap.put(MENUID, menuId);
		 this.menuId=menuId;
	}
	public int getAuthLevel(){
		return this.authLevel;
	}

	public void setAuthLevel(int authLevel){
		fieldValueMap.put(AUTHLEVEL, authLevel);
		 this.authLevel=authLevel;
	}
}