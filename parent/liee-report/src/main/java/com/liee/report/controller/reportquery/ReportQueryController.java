package com.liee.report.controller.reportquery;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.liee.core.controller.BaseController;
import com.liee.report.dao.RepReport;
import com.liee.report.dao.RepReportColumn;
import com.liee.report.dao.RepReportParam;

@Controller
@RequestMapping("/reportQuery") 
public class ReportQueryController extends BaseController{

	

	@RequestMapping(value = "/{code}", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView pageQuery(HttpServletRequest request,@PathVariable String code) {
		ModelAndView model = new ModelAndView();
		
		
		RepReport r = getReport(code);
		List<RepReportParam> paramList = getParams(r.getId());
		List<RepReportColumn> columnList = getColumns(r.getId());
		
		model.addObject("report", r);
		model.addObject("params", paramList);
		model.addObject("gridColumns", columnList);
		
		model.setViewName("easyuiTemplate/report/query/reportquery.html"); 
		return model;
	}
	
	
	/**
	 * 查询结果列配置
	 * @param reportId
	 * @return
	 */
	public List<RepReportColumn> getColumns(int reportId){
		List<RepReportColumn> list = new  ArrayList<RepReportColumn>();
		try{
			if(reportId>0){
				RepReportColumn param = new RepReportColumn();
				param.where(RepReportColumn.REPORTID.EQ(reportId));
				list = param.query();
			}
		}catch(Exception e){
			logger.error("查询报表查询结果列配置错误", e);
		}
		return list;
	}
	
	
	
	/**
	 * 查询参数配置
	 * @param reportId
	 * @return
	 */
	public List<RepReportParam> getParams(int reportId){
		List<RepReportParam> list = new  ArrayList<RepReportParam>();
		try{
			if(reportId>0){
				RepReportParam param = new RepReportParam();
				param.where(RepReportParam.REPORTID.EQ(reportId));
				list = param.query();
			}
		}catch(Exception e){
			logger.error("查询报表查询条件集合错误", e);
		}
		return list;
	}
	
	
	/**
	 * 查询报表主表
	 * @param code
	 * @return
	 */
	public RepReport getReport(String code){
		RepReport r = null;
		try{
			RepReport q = new RepReport();
			q.where(RepReport.CODE.EQ(code));
			List<RepReport> list = q.query();
			if(list!=null && list.size()>0){
				r = list.get(0);
			}
		}catch(Exception e){
			logger.error("查询报表配置错误", e);
		}
		
		return r;
	}
	
	
	
	
	
}
