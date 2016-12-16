package com.liee.core.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-11-24 14:12:18  dao for table sys_user_role
 */
public class SysUserRole extends Table<SysUserRole> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "sys_user_role";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**user_id*/
	public final static Fields USERID = new Fields("`user_id`");
	/**role_id*/
	public final static Fields ROLEID = new Fields("`role_id`");

	private int id = 0;
	private int userId = 0;
	private int roleId = 0;

	public SysUserRole(){
		super(TABLENAME_,SysUserRole.class);
		super.setFields(ID,USERID,ROLEID);
	}

	public SysUserRole(String tableName4sharding){
		super(tableName4sharding,SysUserRole.class);
		super.setFields(ID,USERID,ROLEID);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
	}
	public int getUserId(){
		return this.userId;
	}

	public void setUserId(int userId){
		fieldValueMap.put(USERID, userId);
		 this.userId=userId;
	}
	public int getRoleId(){
		return this.roleId;
	}

	public void setRoleId(int roleId){
		fieldValueMap.put(ROLEID, roleId);
		 this.roleId=roleId;
	}
}