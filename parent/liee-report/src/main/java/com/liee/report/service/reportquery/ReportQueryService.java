package com.liee.report.service.reportquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.liee.core.common.BasePageModel;
import com.liee.core.exception.BaseException;
import com.liee.core.service.BaseService;
import com.liee.report.common.ColumnResult;
import com.liee.report.dao.RepReport;
import com.liee.report.dao.RepReportColumn;

@Service("reportQueryService")
public class ReportQueryService  extends BaseService {

	/**
	 * 分页查询  用于表格查询
	 * @param sr
	 * @param page
	 * @return
	 */
	public BasePageModel<Map<String,Object>> queryPage(RepReport sr,List<RepReportColumn> columnList,BasePageModel<Map<String,Object>> page,Map<String,Object> paramValue ){
		
		try {
			GridDataQueryAbstract query;
			// 判断报表来源
			int datasource = sr.getDataSource();
			if(datasource==0){
				query = new GroovyGridDataQuery(sr.getGroovyFile());
			}else if(datasource==1){
				query = new RemoteGridDataQuery();
			}else{
				query = new RemoteGridDataQuery();
			}
			
			page = query.queryGridData(paramValue, page,columnList);
			
		} catch (Exception e) {
			log.error("分页查询错误",e);
			throw new BaseException("分页查询错误", e);
		}
		
		return page;
	}
	
	
	/**
	 * 将查询出来的列表数据封装为柱状图所需要的数据格式
	 * @param sr
	 * @param columnList
	 * @param paramValue
	 * @return
	 */
	public ColumnResult queryColumnChartData(RepReport report,List<RepReportColumn> columnList,Map<String,Object> paramValue ){
		ColumnResult result = new ColumnResult();
		try {
			GridDataQueryAbstract query;
			// 判断报表来源
			int datasource = report.getDataSource();
			if(datasource==0){
				query = new GroovyGridDataQuery(report.getGroovyFile());
			}else if(datasource==1){
				query = new RemoteGridDataQuery();
			}else{
				query = new RemoteGridDataQuery();
			}
			
			BasePageModel<Map<String,Object>> page = new BasePageModel<>();
			page = query.queryGridData(paramValue, page,columnList);
			List<Map<String,Object>> dataList = page.getRows();
			
			String cXValueFrom = report.getCXValueFrom();
			String cXValue = report.getCXValue();
			String cYValueFrom  = report.getCYValueFrom();
			String cYValue = report.getCYValue();
			
			// TODO 
			
			
		} catch (Exception e) {
			log.error("分页查询错误",e);
			throw new BaseException("分页查询错误", e);
		}
		
		return result;
	}
	
}
