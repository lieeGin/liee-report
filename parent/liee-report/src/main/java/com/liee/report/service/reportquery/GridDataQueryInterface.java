package com.liee.report.service.reportquery;

import java.util.List;
import java.util.Map;

import com.liee.core.common.BasePageModel;
import com.liee.report.dao.RepReportColumn;

public interface GridDataQueryInterface {

	public BasePageModel<Map<String,Object>> queryGridData(Map<String,Object> paramValue,BasePageModel<Map<String,Object>> page,List<RepReportColumn> resultColumn);
}
