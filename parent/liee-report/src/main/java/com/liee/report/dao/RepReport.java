package com.liee.report.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2017-01-11 11:13:44  dao for table rep_report
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
	/**c_x_value_from*/
	public final static Fields CXVALUEFROM = new Fields("`c_x_value_from`");
	/**c_y_value_from*/
	public final static Fields CYVALUEFROM = new Fields("`c_y_value_from`");
	/**c_y_title*/
	public final static Fields CYTITLE = new Fields("`c_y_title`");
	/**c_y_unit*/
	public final static Fields CYUNIT = new Fields("`c_y_unit`");
	/**c_x_value*/
	public final static Fields CXVALUE = new Fields("`c_x_value`");
	/**c_y_value*/
	public final static Fields CYVALUE = new Fields("`c_y_value`");
	/**l_x_value_from*/
	public final static Fields LXVALUEFROM = new Fields("`l_x_value_from`");
	/**l_x_value*/
	public final static Fields LXVALUE = new Fields("`l_x_value`");
	/**l_y_value_from*/
	public final static Fields LYVALUEFROM = new Fields("`l_y_value_from`");
	/**l_y_value*/
	public final static Fields LYVALUE = new Fields("`l_y_value`");
	/**l_y_title*/
	public final static Fields LYTITLE = new Fields("`l_y_title`");
	/**l_y_unit*/
	public final static Fields LYUNIT = new Fields("`l_y_unit`");

	private int id = 0;
	private java.lang.String name;
	private java.lang.String code;
	private int dataSource = 0;
	private java.lang.String apiAddress;
	private java.lang.String groovyFile;
	private java.lang.String reportShowWay;
	private java.lang.String cXValueFrom;
	private java.lang.String cYValueFrom;
	private java.lang.String cYTitle;
	private java.lang.String cYUnit;
	private java.lang.String cXValue;
	private java.lang.String cYValue;
	private java.lang.String lXValueFrom;
	private java.lang.String lXValue;
	private java.lang.String lYValueFrom;
	private java.lang.String lYValue;
	private java.lang.String lYTitle;
	private java.lang.String lYUnit;

	public RepReport(){
		super(TABLENAME_,RepReport.class);
		super.setFields(ID,NAME,CODE,DATASOURCE,APIADDRESS,GROOVYFILE,REPORTSHOWWAY,CXVALUEFROM,CYVALUEFROM,CYTITLE,CYUNIT,CXVALUE,CYVALUE,LXVALUEFROM,LXVALUE,LYVALUEFROM,LYVALUE,LYTITLE,LYUNIT);
	}

	public RepReport(String tableName4sharding){
		super(tableName4sharding,RepReport.class);
		super.setFields(ID,NAME,CODE,DATASOURCE,APIADDRESS,GROOVYFILE,REPORTSHOWWAY,CXVALUEFROM,CYVALUEFROM,CYTITLE,CYUNIT,CXVALUE,CYVALUE,LXVALUEFROM,LXVALUE,LYVALUEFROM,LYVALUE,LYTITLE,LYUNIT);
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
	public java.lang.String getCXValueFrom(){
		return this.cXValueFrom;
	}

	public void setCXValueFrom(java.lang.String cXValueFrom){
		fieldValueMap.put(CXVALUEFROM, cXValueFrom);
		 this.cXValueFrom=cXValueFrom;
	}
	public java.lang.String getCYValueFrom(){
		return this.cYValueFrom;
	}

	public void setCYValueFrom(java.lang.String cYValueFrom){
		fieldValueMap.put(CYVALUEFROM, cYValueFrom);
		 this.cYValueFrom=cYValueFrom;
	}
	public java.lang.String getCYTitle(){
		return this.cYTitle;
	}

	public void setCYTitle(java.lang.String cYTitle){
		fieldValueMap.put(CYTITLE, cYTitle);
		 this.cYTitle=cYTitle;
	}
	public java.lang.String getCYUnit(){
		return this.cYUnit;
	}

	public void setCYUnit(java.lang.String cYUnit){
		fieldValueMap.put(CYUNIT, cYUnit);
		 this.cYUnit=cYUnit;
	}
	public java.lang.String getCXValue(){
		return this.cXValue;
	}

	public void setCXValue(java.lang.String cXValue){
		fieldValueMap.put(CXVALUE, cXValue);
		 this.cXValue=cXValue;
	}
	public java.lang.String getCYValue(){
		return this.cYValue;
	}

	public void setCYValue(java.lang.String cYValue){
		fieldValueMap.put(CYVALUE, cYValue);
		 this.cYValue=cYValue;
	}
	public java.lang.String getLXValueFrom(){
		return this.lXValueFrom;
	}

	public void setLXValueFrom(java.lang.String lXValueFrom){
		fieldValueMap.put(LXVALUEFROM, lXValueFrom);
		 this.lXValueFrom=lXValueFrom;
	}
	public java.lang.String getLXValue(){
		return this.lXValue;
	}

	public void setLXValue(java.lang.String lXValue){
		fieldValueMap.put(LXVALUE, lXValue);
		 this.lXValue=lXValue;
	}
	public java.lang.String getLYValueFrom(){
		return this.lYValueFrom;
	}

	public void setLYValueFrom(java.lang.String lYValueFrom){
		fieldValueMap.put(LYVALUEFROM, lYValueFrom);
		 this.lYValueFrom=lYValueFrom;
	}
	public java.lang.String getLYValue(){
		return this.lYValue;
	}

	public void setLYValue(java.lang.String lYValue){
		fieldValueMap.put(LYVALUE, lYValue);
		 this.lYValue=lYValue;
	}
	public java.lang.String getLYTitle(){
		return this.lYTitle;
	}

	public void setLYTitle(java.lang.String lYTitle){
		fieldValueMap.put(LYTITLE, lYTitle);
		 this.lYTitle=lYTitle;
	}
	public java.lang.String getLYUnit(){
		return this.lYUnit;
	}

	public void setLYUnit(java.lang.String lYUnit){
		fieldValueMap.put(LYUNIT, lYUnit);
		 this.lYUnit=lYUnit;
	}
}