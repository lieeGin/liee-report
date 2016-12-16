package com.liee.report.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-12-12 18:18:45  dao for table rep_report
 */
public class RepReport extends Table<RepReport> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "rep_report";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**name*/
	public final static Fields NAME = new Fields("`name`");
	/**code*/
	public final static Fields CODE = new Fields("`code`");
	/**data_source*/
	public final static Fields DATASOURCE = new Fields("`data_source`");
	/**api_address*/
	public final static Fields APIADDRESS = new Fields("`api_address`");
	/**groovy_file*/
	public final static Fields GROOVYFILE = new Fields("`groovy_file`");
	/**report_show_way*/
	public final static Fields REPORTSHOWWAY = new Fields("`report_show_way`");

	private int id = 0;
	private java.lang.String name;
	private java.lang.String code;
	private int dataSource = 0;
	private java.lang.String apiAddress;
	private java.lang.String groovyFile;
	private java.lang.String reportShowWay;

	public RepReport(){
		super(TABLENAME_,RepReport.class);
		super.setFields(ID,NAME,CODE,DATASOURCE,APIADDRESS,GROOVYFILE,REPORTSHOWWAY);
	}

	public RepReport(String tableName4sharding){
		super(tableName4sharding,RepReport.class);
		super.setFields(ID,NAME,CODE,DATASOURCE,APIADDRESS,GROOVYFILE,REPORTSHOWWAY);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
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
	public int getDataSource(){
		return this.dataSource;
	}

	public void setDataSource(int dataSource){
		fieldValueMap.put(DATASOURCE, dataSource);
		 this.dataSource=dataSource;
	}
	public java.lang.String getApiAddress(){
		return this.apiAddress;
	}

	public void setApiAddress(java.lang.String apiAddress){
		fieldValueMap.put(APIADDRESS, apiAddress);
		 this.apiAddress=apiAddress;
	}
	public java.lang.String getGroovyFile(){
		return this.groovyFile;
	}

	public void setGroovyFile(java.lang.String groovyFile){
		fieldValueMap.put(GROOVYFILE, groovyFile);
		 this.groovyFile=groovyFile;
	}
	public java.lang.String getReportShowWay(){
		return this.reportShowWay;
	}

	public void setReportShowWay(java.lang.String reportShowWay){
		fieldValueMap.put(REPORTSHOWWAY, reportShowWay);
		 this.reportShowWay=reportShowWay;
	}
}