package com.liee.report.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-12-09 16:20:34  dao for table rep_report_column
 */
public class RepReportColumn extends Table<RepReportColumn> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "rep_report_column";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**report_id*/
	public final static Fields REPORTID = new Fields("`report_id`");
	/**title*/
	public final static Fields TITLE = new Fields("`title`");
	/**field*/
	public final static Fields FIELD = new Fields("`field`");
	/**sortable*/
	public final static Fields SORTABLE = new Fields("`sortable`");
	/**formatter*/
	public final static Fields FORMATTER = new Fields("`formatter`");
	/**width*/
	public final static Fields WIDTH = new Fields("`width`");
	/**align*/
	public final static Fields ALIGN = new Fields("`align`");

	private int id = 0;
	private int reportId = 0;
	private java.lang.String title;
	private java.lang.String field;
	private int sortable = 0;
	private java.lang.String formatter;
	private int width = 0;
	private java.lang.String align;

	public RepReportColumn(){
		super(TABLENAME_,RepReportColumn.class);
		super.setFields(ID,REPORTID,TITLE,FIELD,SORTABLE,FORMATTER,WIDTH,ALIGN);
	}

	public RepReportColumn(String tableName4sharding){
		super(tableName4sharding,RepReportColumn.class);
		super.setFields(ID,REPORTID,TITLE,FIELD,SORTABLE,FORMATTER,WIDTH,ALIGN);
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
	public java.lang.String getTitle(){
		return this.title;
	}

	public void setTitle(java.lang.String title){
		fieldValueMap.put(TITLE, title);
		 this.title=title;
	}
	public java.lang.String getField(){
		return this.field;
	}

	public void setField(java.lang.String field){
		fieldValueMap.put(FIELD, field);
		 this.field=field;
	}
	public int getSortable(){
		return this.sortable;
	}

	public void setSortable(int sortable){
		fieldValueMap.put(SORTABLE, sortable);
		 this.sortable=sortable;
	}
	public java.lang.String getFormatter(){
		return this.formatter;
	}

	public void setFormatter(java.lang.String formatter){
		fieldValueMap.put(FORMATTER, formatter);
		 this.formatter=formatter;
	}
	public int getWidth(){
		return this.width;
	}

	public void setWidth(int width){
		fieldValueMap.put(WIDTH, width);
		 this.width=width;
	}
	public java.lang.String getAlign(){
		return this.align;
	}

	public void setAlign(java.lang.String align){
		fieldValueMap.put(ALIGN, align);
		 this.align=align;
	}
}