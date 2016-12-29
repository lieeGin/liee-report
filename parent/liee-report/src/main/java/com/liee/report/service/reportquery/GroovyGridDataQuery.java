package com.liee.report.service.reportquery;

import java.util.List;
import java.util.Map;

import com.liee.core.common.BasePageModel;
import com.liee.report.dao.RepReportColumn;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;

/**
 * 使用groovy查询报表
 * @author lieeGin
 *
 */
public class GroovyGridDataQuery extends GridDataQueryAbstract{
	
	GroovyObject groovyObject;
	
	public GroovyGridDataQuery(String filePath) {
		int index = filePath.lastIndexOf("/");
		
		 String[] roots = new String[] { filePath.substring(0, index+1) };  
		 try {
		  //通过指定的roots来初始化GroovyScriptEngine  
		  GroovyScriptEngine gse = new GroovyScriptEngine(roots);
		  groovyObject = (GroovyObject) gse.loadScriptByName(filePath.substring(index+1,filePath.length())).newInstance();  
		} catch (Exception e) {
			log.error("初始化groovy查询错误",e);
		}  
	}
	
	@Override
	public Map<String, Object> queryData(Map<String, Object> paramMap, BasePageModel<Map<String, Object>> page) {
		// 添加分页排序条件
		groovyObject.setProperty("pageNum", page.getPageNum());
		groovyObject.setProperty("pageSize", page.getPageSize());
		groovyObject.setProperty("order", page.getOrder());
		groovyObject.setProperty("sort", page.getSort());
		
	    Map<String,Object> result = (Map<String,Object>) groovyObject.invokeMethod("getData", paramMap);    
		return result;
	}

	@Override
	public int getTotalRowNum(Map<String, Object> queryResult) {
		
		return (int)queryResult.get("total");
	}

	@Override
	public List<Map<String, Object>> getDataList(Map<String, Object> queryResult, List<RepReportColumn> resultColumn) {
		List<Map<String, Object>> list =  (List<Map<String, Object>>)queryResult.get("rows");
		return list;
	}

	
}
