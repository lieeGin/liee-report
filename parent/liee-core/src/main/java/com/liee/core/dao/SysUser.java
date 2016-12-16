package com.liee.core.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-11-24 14:12:18  dao for table sys_user
 */
public class SysUser extends Table<SysUser> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "sys_user";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**user_name*/
	public final static Fields USERNAME = new Fields("`user_name`");
	/**display_name*/
	public final static Fields DISPLAYNAME = new Fields("`display_name`");
	/**sex*/
	public final static Fields SEX = new Fields("`sex`");
	/**password*/
	public final static Fields PASSWORD = new Fields("`password`");
	/**mobile_phone*/
	public final static Fields MOBILEPHONE = new Fields("`mobile_phone`");
	/**email*/
	public final static Fields EMAIL = new Fields("`email`");
	/**register_time*/
	public final static Fields REGISTERTIME = new Fields("`register_time`");
	/**last_login_time*/
	public final static Fields LASTLOGINTIME = new Fields("`last_login_time`");

	private int id = 0;
	private java.lang.String userName;
	private java.lang.String displayName;
	private int sex = 0;
	private java.lang.String password;
	private java.lang.String mobilePhone;
	private java.lang.String email;
	private java.util.Date registerTime;
	private java.util.Date lastLoginTime;

	public SysUser(){
		super(TABLENAME_,SysUser.class);
		super.setFields(ID,USERNAME,DISPLAYNAME,SEX,PASSWORD,MOBILEPHONE,EMAIL,REGISTERTIME,LASTLOGINTIME);
	}

	public SysUser(String tableName4sharding){
		super(tableName4sharding,SysUser.class);
		super.setFields(ID,USERNAME,DISPLAYNAME,SEX,PASSWORD,MOBILEPHONE,EMAIL,REGISTERTIME,LASTLOGINTIME);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
	}
	public java.lang.String getUserName(){
		return this.userName;
	}

	public void setUserName(java.lang.String userName){
		fieldValueMap.put(USERNAME, userName);
		 this.userName=userName;
	}
	public java.lang.String getDisplayName(){
		return this.displayName;
	}

	public void setDisplayName(java.lang.String displayName){
		fieldValueMap.put(DISPLAYNAME, displayName);
		 this.displayName=displayName;
	}
	public int getSex(){
		return this.sex;
	}

	public void setSex(int sex){
		fieldValueMap.put(SEX, sex);
		 this.sex=sex;
	}
	public java.lang.String getPassword(){
		return this.password;
	}

	public void setPassword(java.lang.String password){
		fieldValueMap.put(PASSWORD, password);
		 this.password=password;
	}
	public java.lang.String getMobilePhone(){
		return this.mobilePhone;
	}

	public void setMobilePhone(java.lang.String mobilePhone){
		fieldValueMap.put(MOBILEPHONE, mobilePhone);
		 this.mobilePhone=mobilePhone;
	}
	public java.lang.String getEmail(){
		return this.email;
	}

	public void setEmail(java.lang.String email){
		fieldValueMap.put(EMAIL, email);
		 this.email=email;
	}
	public java.util.Date getRegisterTime(){
		return this.registerTime;
	}

	public void setRegisterTime(java.util.Date registerTime){
		fieldValueMap.put(REGISTERTIME, registerTime);
		 this.registerTime=registerTime;
	}
	public java.util.Date getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime){
		fieldValueMap.put(LASTLOGINTIME, lastLoginTime);
		 this.lastLoginTime=lastLoginTime;
	}
}