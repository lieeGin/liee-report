package com.liee.report.service.reportquery;

import java.util.List;
import java.util.Map;

import com.liee.core.common.BasePageModel;
import com.liee.report.dao.RepReportColumn;

public interface GridDataQueryInterface {

	/**
	 * 分页查询
	 * @param paramValue
	 * @param page
	 * @param resultColumn
	 * @return
	 */
	public BasePageModel<Map<String,Object>> queryGridData(Map<String,Object> paramValue,BasePageModel<Map<String,Object>> page,List<RepReportColumn> resultColumn);
	
	/**
	 * 列表查询 不分页
	 * @param paramValue
	 * @param resultColumn
	 * @return
	 */
	public List<Map<String,Object>> queryList(Map<String,Object> paramValue,List<RepReportColumn> resultColumn);
}
