package com.liee.report.service.reportquery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.liee.core.common.BasePageModel;
import com.liee.core.exception.BaseException;
import com.liee.core.service.BaseService;
import com.liee.report.common.ChartResult;
import com.liee.report.common.ChartSerie;
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
	 * 查询list
	 * @param sr
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> queryList(RepReport sr,List<RepReportColumn> columnList,Map<String,Object> paramValue ){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
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
			
			list = query.queryList(paramValue,columnList);
			
		} catch (Exception e) {
			log.error("list查询错误",e);
			throw new BaseException("list查询错误", e);
		}
		
		return list;
	}
	
	
	
	/**
	 * 将查询出来的列表数据封装为柱状图所需要的数据格式
	 * @param sr
	 * @param columnList
	 * @param paramValue
	 * @return
	 */
	public ChartResult<ChartSerie> queryColumnChartData(RepReport report,List<RepReportColumn> columnList,Map<String,Object> paramValue ){
		ChartResult<ChartSerie> result = new ChartResult<ChartSerie>();
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
			
			List<Map<String,Object>> dataList = query.queryList(paramValue,columnList);
			
			String cXValueFrom = report.getCXValueFrom();
			String cXValue = report.getCXValue();
			String cYValueFrom  = report.getCYValueFrom();   // 通过x轴判断。
			String cYValue = report.getCYValue();
			
			switch(cXValueFrom){
			case "columnName":
				// x轴是指定了若干个列名，x轴一定是取某一列的值
				result = getResultFromX(cXValue,cYValue,dataList,columnList);
				break;
			case "columnValue":
				// x轴指定取某一列的值，y轴则默认一定是若干列的列名
				result = getResultFromY(cYValue,cXValue,dataList,columnList);
				break;
				
			}
			
		} catch (Exception e) {
			log.error("分页查询错误",e);
			throw new BaseException("分页查询错误", e);
		}
		
		return result;
	}
	
	
	/**
	 * 通过X取值来源组装数据
	 * @param cXValueFrom
	 * @param cYValue
	 * @param dataList
	 * @return
	 */
	public ChartResult<ChartSerie> getResultFromX(String XValue,String YValue,List<Map<String,Object>> dataList,List<RepReportColumn> columnList){
		ChartResult<ChartSerie> result = new ChartResult<ChartSerie>();
	
		List<ChartSerie> series = new ArrayList<ChartSerie>();
		
		String[] xcs = XValue.split(",");
	
		for (Map<String,Object> map : dataList) {
			BigDecimal[] datas = new BigDecimal[xcs.length];
			ChartSerie cs = new ChartSerie();
			cs.setName((String)map.get(YValue));
			
			if(xcs!=null && xcs.length>0){
				for (int i = 0; i < xcs.length; i++) {
					String xc = xcs[i];
					datas[i]=(BigDecimal)map.get(xc);
				}
			}
			cs.setData(datas); 
			series.add(cs);
		}
		
		String[] xShow = new String[xcs.length];
		for (int i = 0; i < xcs.length; i++) {
			String xc = xcs[i];
			for (RepReportColumn column : columnList) {
				if(column.getField().equals(xc)){
					xShow[i] = column.getTitle();
				}
			}
		}
		
		result.setCategories(xShow);
		result.setSeries(series);
		return result;
	}
	
	
	/**
	 * 通过Y取值来源组装数据
	 * @param cXValueFrom
	 * @param cYValue
	 * @param dataList
	 * @return
	 */
	public ChartResult<ChartSerie> getResultFromY(String YValue,String XValue,List<Map<String,Object>> dataList,List<RepReportColumn> columnList){
		ChartResult<ChartSerie> result = new ChartResult<ChartSerie>();
	
		List<ChartSerie> series = new ArrayList<ChartSerie>();
		
		String[] xcs = new String[dataList.size()];
		String[] values = YValue.split(",");
		
		for (int j = 0; j < values.length; j++) {
			BigDecimal[] datas = new BigDecimal[values.length];
			ChartSerie cs = new ChartSerie();
			
			for (RepReportColumn column : columnList) {
				if(column.getField().equals(values[j])){
					cs.setName(column.getTitle());
				}
			}
			
			for (int i = 0; i < dataList.size(); i++) {
				Map<String,Object> map = dataList.get(i);
				datas[i] = (BigDecimal)map.get(values[j]);
				xcs[i] = (String)map.get(XValue);
			}
			cs.setData(datas); 
			series.add(cs);
			
		}

		result.setCategories(xcs);
		result.setSeries(series);
		return result;
	}
	
	
	
	
	/**
	 * 将查询出来的列表数据封装为曲线图所需要的数据格式
	 * @param sr
	 * @param columnList
	 * @param paramValue
	 * @return
	 */
	public ChartResult<ChartSerie> queryLineChartData(RepReport report,List<RepReportColumn> columnList,Map<String,Object> paramValue ){
		ChartResult<ChartSerie> result = new ChartResult<ChartSerie>();
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
			
			List<Map<String,Object>> dataList = query.queryList(paramValue,columnList);
			
			String lXValueFrom = report.getLXValueFrom();
			String lXValue = report.getLXValue();
			String lYValueFrom  = report.getLYValueFrom();   // 通过x轴判断。
			String lYValue = report.getLYValue();
			
			switch(lXValueFrom){
			case "columnName":
				// x轴是指定了若干个列名，x轴一定是取某一列的值
				result = getResultFromX(lXValue,lYValue,dataList,columnList);
				break;
			case "columnValue":
				// x轴指定取某一列的值，y轴则默认一定是若干列的列名
				result = getResultFromY(lYValue,lXValue,dataList,columnList);
				break;
				
			}
			
		} catch (Exception e) {
			log.error("分页查询错误",e);
			throw new BaseException("分页查询错误", e);
		}
		
		return result;
	}
	
}
