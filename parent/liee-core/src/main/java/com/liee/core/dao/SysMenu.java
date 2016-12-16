package com.liee.core.dao;

import java.io.Serializable;
import com.jdao.base.Table;
import com.jdao.base.Fields;

/**
 * @date 2016-11-24 14:12:18  dao for table sys_menu
 */
public class SysMenu extends Table<SysMenu> implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static String TABLENAME_ = "sys_menu";
	/**id*/
	public final static Fields ID = new Fields("`id`");
	/**menu_name*/
	public final static Fields MENUNAME = new Fields("`menu_name`");
	/**parent_id*/
	public final static Fields PARENTID = new Fields("`parent_id`");
	/**level*/
	public final static Fields LEVEL = new Fields("`level`");
	/**url*/
	public final static Fields URL = new Fields("`url`");
	/**display_order*/
	public final static Fields DISPLAYORDER = new Fields("`display_order`");

	private int id = 0;
	private java.lang.String menuName;
	private int parentId = 0;
	private int level = 0;
	private java.lang.String url;
	private int displayOrder = 0;

	public SysMenu(){
		super(TABLENAME_,SysMenu.class);
		super.setFields(ID,MENUNAME,PARENTID,LEVEL,URL,DISPLAYORDER);
	}

	public SysMenu(String tableName4sharding){
		super(tableName4sharding,SysMenu.class);
		super.setFields(ID,MENUNAME,PARENTID,LEVEL,URL,DISPLAYORDER);
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		fieldValueMap.put(ID, id);
		 this.id=id;
	}
	public java.lang.String getMenuName(){
		return this.menuName;
	}

	public void setMenuName(java.lang.String menuName){
		fieldValueMap.put(MENUNAME, menuName);
		 this.menuName=menuName;
	}
	public int getParentId(){
		return this.parentId;
	}

	public void setParentId(int parentId){
		fieldValueMap.put(PARENTID, parentId);
		 this.parentId=parentId;
	}
	public int getLevel(){
		return this.level;
	}

	public void setLevel(int level){
		fieldValueMap.put(LEVEL, level);
		 this.level=level;
	}
	public java.lang.String getUrl(){
		return this.url;
	}

	public void setUrl(java.lang.String url){
		fieldValueMap.put(URL, url);
		 this.url=url;
	}
	public int getDisplayOrder(){
		return this.displayOrder;
	}

	public void setDisplayOrder(int displayOrder){
		fieldValueMap.put(DISPLAYORDER, displayOrder);
		 this.displayOrder=displayOrder;
	}
}