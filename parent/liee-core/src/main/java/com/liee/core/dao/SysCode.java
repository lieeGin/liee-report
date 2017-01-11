package com.liee.core.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2017-01-11 16:42:28  dao for table sys_code
 */
public class SysCode extends Table<SysCode> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "sys_code";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**code*/
	public final static Fields CODE = new Fields("`code`");
	/**text*/
	public final static Fields TEXT = new Fields("`text`");
	/**parent_code*/
	public final static Fields PARENTCODE = new Fields("`parent_code`");
	/**sort*/
	public final static Fields SORT = new Fields("`sort`");
	/**display_order*/
	public final static Fields DISPLAYORDER = new Fields("`display_order`");
	/**remark*/
	public final static Fields REMARK = new Fields("`remark`");

	private int id = 0;
	private java.lang.String code;
	private java.lang.String text;
	private java.lang.String parentCode;
	private int sort = 0;
	private int displayOrder = 0;
	private java.lang.String remark;

	public SysCode(){
		super(TABLENAME_,SysCode.class);
		super.setFields(ID,CODE,TEXT,PARENTCODE,SORT,DISPLAYORDER,REMARK);
	}

	public SysCode(String tableName4sharding){
		super(tableName4sharding,SysCode.class);
		super.setFields(ID,CODE,TEXT,PARENTCODE,SORT,DISPLAYORDER,REMARK);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
	}
	public java.lang.String getCode(){
		return this.code;
	}

	public void setCode(java.lang.String code){
		fieldValueMap.put(CODE, code);
		 this.code=code;
	}
	public java.lang.String getText(){
		return this.text;
	}

	public void setText(java.lang.String text){
		fieldValueMap.put(TEXT, text);
		 this.text=text;
	}
	public java.lang.String getParentCode(){
		return this.parentCode;
	}

	public void setParentCode(java.lang.String parentCode){
		fieldValueMap.put(PARENTCODE, parentCode);
		 this.parentCode=parentCode;
	}
	public int getSort(){
		return this.sort;
	}

	public void setSort(int sort){
		fieldValueMap.put(SORT, sort);
		 this.sort=sort;
	}
	public int getDisplayOrder(){
		return this.displayOrder;
	}

	public void setDisplayOrder(int displayOrder){
		fieldValueMap.put(DISPLAYORDER, displayOrder);
		 this.displayOrder=displayOrder;
	}
	public java.lang.String getRemark(){
		return this.remark;
	}

	public void setRemark(java.lang.String remark){
		fieldValueMap.put(REMARK, remark);
		 this.remark=remark;
	}
}