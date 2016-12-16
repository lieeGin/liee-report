package com.liee.core.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-11-24 14:12:18  dao for table systemconfig
 */
public class Systemconfig extends Table<Systemconfig> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "systemconfig";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**keyword*/
	public final static Fields KEYWORD = new Fields("`keyword`");
	/**valuestr*/
	public final static Fields VALUESTR = new Fields("`valuestr`");
	/**remark*/
	public final static Fields REMARK = new Fields("`remark`");

	private int id = 0;
	private java.lang.String keyword;
	private java.lang.String valuestr;
	private java.lang.String remark;

	public Systemconfig(){
		super(TABLENAME_,Systemconfig.class);
		super.setFields(ID,KEYWORD,VALUESTR,REMARK);
	}

	public Systemconfig(String tableName4sharding){
		super(tableName4sharding,Systemconfig.class);
		super.setFields(ID,KEYWORD,VALUESTR,REMARK);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
	}
	public java.lang.String getKeyword(){
		return this.keyword;
	}

	public void setKeyword(java.lang.String keyword){
		fieldValueMap.put(KEYWORD, keyword);
		 this.keyword=keyword;
	}
	public java.lang.String getValuestr(){
		return this.valuestr;
	}

	public void setValuestr(java.lang.String valuestr){
		fieldValueMap.put(VALUESTR, valuestr);
		 this.valuestr=valuestr;
	}
	public java.lang.String getRemark(){
		return this.remark;
	}

	public void setRemark(java.lang.String remark){
		fieldValueMap.put(REMARK, remark);
		 this.remark=remark;
	}
}