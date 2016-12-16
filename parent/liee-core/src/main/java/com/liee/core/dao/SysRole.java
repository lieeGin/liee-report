package com.liee.core.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-11-24 14:12:18  dao for table sys_role
 */
public class SysRole extends Table<SysRole> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "sys_role";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**role_name*/
	public final static Fields ROLENAME = new Fields("`role_name`");
	/**remark*/
	public final static Fields REMARK = new Fields("`remark`");

	private int id = 0;
	private java.lang.String roleName;
	private java.lang.String remark;

	public SysRole(){
		super(TABLENAME_,SysRole.class);
		super.setFields(ID,ROLENAME,REMARK);
	}

	public SysRole(String tableName4sharding){
		super(tableName4sharding,SysRole.class);
		super.setFields(ID,ROLENAME,REMARK);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
	}
	public java.lang.String getRoleName(){
		return this.roleName;
	}

	public void setRoleName(java.lang.String roleName){
		fieldValueMap.put(ROLENAME, roleName);
		 this.roleName=roleName;
	}
	public java.lang.String getRemark(){
		return this.remark;
	}

	public void setRemark(java.lang.String remark){
		fieldValueMap.put(REMARK, remark);
		 this.remark=remark;
	}
}