package com.liee.report.controller.reportquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.liee.core.common.BasePageModel;
import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.utils.StringUtil;
import com.liee.report.dao.RepReport;
import com.liee.report.dao.RepReportColumn;
import com.liee.report.dao.RepReportParam;
import com.liee.report.service.reportquery.ReportQueryService;

@Controller
@RequestMapping("/reportQuery") 
public class ReportQueryController extends BaseController{

	@Autowired
	private ReportQueryService reportQueryService;

	@RequestMapping(value = "/{code}", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView pageQuery(HttpServletRequest request,@PathVariable String code, int menuId, int authLevel) {
		ModelAndView model = new ModelAndView();
		
		RepReport r = getReport(code);
		List<RepReportParam> paramList = getParams(r.getId());
		List<RepReportColumn> columnList = getColumns(r.getId());
		
		model.addObject("report", r);
		model.addObject("params", paramList);
		model.addObject("gridColumns", columnList);
		model.addObject("menuId", menuId);
		model.addObject("authLevel", authLevel);
		
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
	
	
	@RequestMapping(value = "/get{code}GridData", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel getPageData(HttpServletRequest request,@PathVariable String code) {
		BaseReturnModel br = new BaseReturnModel();
		
		BasePageModel<Map<String,Object>> page = new BasePageModel<Map<String,Object>>();
		// 分页查询参数
		page.setPageSize(StringUtil.stringToInt(getRequestParameter(request, "rows")));
		page.setPageNum(StringUtil.stringToInt(getRequestParameter(request, "page")));
		page.setOrder(StringUtil.nullToString(getRequestParameter(request,"order")));
		page.setSort(StringUtil.nullToString(getRequestParameter(request,"sort")));
		
		
		RepReport r = getReport(code);
		List<RepReportParam> paramList = getParams(r.getId());
		List<RepReportColumn> columnList = getColumns(r.getId());
		
		// 获得查询参数
		Map<String,Object> paramValue = getParamValueFromRequest(request,paramList);  
		
		page = reportQueryService.queryPage(r,columnList,page,paramValue);
		
		br.setSuccess(true);
		br.setObj(page);
		return br;
	}
	
	
	/**
	 * 从request中获取查询参数，并且根据参数配置封装到map中
	 * @return
	 */
	public Map<String,Object> getParamValueFromRequest(HttpServletRequest request,List<RepReportParam> paramList){
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		if(paramList!=null && paramList.size() > 0){
			for (RepReportParam param : paramList) {
				String key  = StringUtil.isEmpty(param.getSourceCode())?param.getCode():param.getSourceCode();  //将 匹配源中的字段名 设置为key值，优先sourceCode，没有，就取code
				String value = getRequestParameter(request, param.getCode());
				Map<String,Object> obj = new HashMap<String,Object>();
				switch (param.getType()) {
				case "text":
					obj.put("value", StringUtil.nullToString(value));
					break;
				case "number":
					obj.put("value", StringUtil.stringToInt(value));
					break;
				default:
					break;
				}
				obj.put("compareWay", param.getCompareWay());  // 比较方式
				paramMap.put(key,obj);
			}
		}
		
		return paramMap;
	}
	
	
}
