package com.liee.report.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-12-09 16:20:27  dao for table rep_report_param
 */
public class RepReportParam extends Table<RepReportParam> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "rep_report_param";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**report_id*/
	public final static Fields REPORTID = new Fields("`report_id`");
	/**name*/
	public final static Fields NAME = new Fields("`name`");
	/**code*/
	public final static Fields CODE = new Fields("`code`");
	/**type*/
	public final static Fields TYPE = new Fields("`type`");
	/**widget_type*/
	public final static Fields WIDGETTYPE = new Fields("`widget_type`");
	/**select_key*/
	public final static Fields SELECTKEY = new Fields("`select_key`");

	private int id = 0;
	private int reportId = 0;
	private java.lang.String name;
	private java.lang.String code;
	private java.lang.String type;
	private java.lang.String widgetType;
	private java.lang.String selectKey;

	public RepReportParam(){
		super(TABLENAME_,RepReportParam.class);
		super.setFields(ID,REPORTID,NAME,CODE,TYPE,WIDGETTYPE,SELECTKEY);
	}

	public RepReportParam(String tableName4sharding){
		super(tableName4sharding,RepReportParam.class);
		super.setFields(ID,REPORTID,NAME,CODE,TYPE,WIDGETTYPE,SELECTKEY);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
	}
	public int getReportId(){
		return this.reportId;
	}

	public void setReportId(int reportId){
		fieldValueMap.put(REPORTID, reportId);
		 this.reportId=reportId;
	}
	public java.lang.String getName(){
		return this.name;
	}

	public void setName(java.lang.String name){
		fieldValueMap.put(NAME, name);
		 this.name=name;
	}
	public java.lang.String getCode(){
		return this.code;
	}

	public void setCode(java.lang.String code){
		fieldValueMap.put(CODE, code);
		 this.code=code;
	}
	public java.lang.String getType(){
		return this.type;
	}

	public void setType(java.lang.String type){
		fieldValueMap.put(TYPE, type);
		 this.type=type;
	}
	public java.lang.String getWidgetType(){
		return this.widgetType;
	}

	public void setWidgetType(java.lang.String widgetType){
		fieldValueMap.put(WIDGETTYPE, widgetType);
		 this.widgetType=widgetType;
	}
	public java.lang.String getSelectKey(){
		return this.selectKey;
	}

	public void setSelectKey(java.lang.String selectKey){
		fieldValueMap.put(SELECTKEY, selectKey);
		 this.selectKey=selectKey;
	}
}