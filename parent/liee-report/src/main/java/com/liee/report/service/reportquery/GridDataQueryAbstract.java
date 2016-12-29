package com.liee.report.service.reportquery;

import java.util.List;
import java.util.Map;

import com.liee.core.common.BasePageModel;
import com.liee.core.log.Logger;
import com.liee.report.dao.RepReportColumn;

public abstract class GridDataQueryAbstract implements GridDataQueryInterface{

	public Logger log = Logger.getLogger();
	
	/**
	 * 查询数据总入口
	 */
	@Override
	public BasePageModel<Map<String,Object>> queryGridData(Map<String,Object> paramValue,BasePageModel<Map<String,Object>> page,List<RepReportColumn> resultColumn) {
		log.info(">>>准备查询数据");
		Map<String,Object> queryResult = queryData(paramValue,page);
		assemblyResultList(queryResult,resultColumn,page);
		log.info("<<<查询数据完毕");
		return page;
	}
	
	
	/**
	 * 查询数据
	 * @param paramMap
	 * @return
	 */
	protected abstract Map<String,Object> queryData(Map<String,Object> paramMap,BasePageModel<Map<String,Object>> page);
	
	
	/**
	 * 将数据封装成map的集合形式返回,是最终前台使用到的数据
	 * @param queryResult
	 * @param resultColumn
	 * @return
	 */
	private void assemblyResultList(Map<String,Object> queryResult,List<RepReportColumn> resultColumn,BasePageModel<Map<String,Object>> page){
		int totalRowNum = getTotalRowNum(queryResult);
		List<Map<String,Object>> listResult = getDataList(queryResult,resultColumn);
		page.setRows(listResult);
		page.setTotal(totalRowNum);
	}
	
	/**
	 * 获取总条数
	 * @param queryResult
	 * @return
	 */
	protected abstract int getTotalRowNum(Map<String,Object> queryResult);
	
	
	/**
	 * 获取并封装数据
	 * @param queryResult
	 * @param resultColumn
	 */
	protected abstract List<Map<String,Object>> getDataList(Map<String,Object> queryResult,List<RepReportColumn> resultColumn); 
	
}
