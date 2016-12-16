package com.liee.report.controller.reportconfig;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liee.core.common.BasePageModel;
import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.log.Logger;
import com.liee.core.utils.ArrayUtil;
import com.liee.core.utils.StringUtil;
import com.liee.report.dao.RepReport;
import com.liee.report.dao.RepReportColumn;
import com.liee.report.dao.RepReportParam;
import com.liee.report.service.reportconfig.ReportConfigService;

@Controller
@RequestMapping("/report") 
public class ReportConfigController extends BaseController{
	
	Logger log = Logger.getLogger();
	
	@Autowired
	private ReportConfigService repReportService;
	
	
	@RequestMapping(value = "/saveOrUpdate", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel saveOrUpdate(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		
		RepReport report =  getReportFromRequest(request);
		List<RepReportParam> paramList = getParamListFromRequest(request);
		List<RepReportColumn> columnList = getColumnListFromRequest(request);
		
		String groovyStr = StringUtil.nullToString(getRequestParameter(request, "groovyStr")); 
		
		String checkResult = checkAttributeForSave(report,paramList,columnList);
		if(!StringUtil.isEmpty(checkResult)){
			rm.setSuccess(false);
			rm.setErrMsg(checkResult);
			return rm;
		}
		
		try{
			repReportService.saveOrUpdate(report,paramList,columnList,groovyStr);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 获取参数列表
	 * @param request
	 * @return
	 */
	public List<RepReportParam> getParamListFromRequest(HttpServletRequest request){
		String paramString = getRequestParameter(request, "paramData");
		List<RepReportParam> list= new ArrayList<RepReportParam>();
		if(!StringUtil.isEmpty(paramString)){
			list = (List<RepReportParam>)ArrayUtil.getListFromJson(paramString, RepReportParam.class);
		}
		return list;
	}
	
	
	/**
	 * 获取结果列的列表
	 * @param request
	 * @return
	 */
	public List<RepReportColumn> getColumnListFromRequest(HttpServletRequest request){
		String columnString = getRequestParameter(request, "columnData");
		List<RepReportColumn> list= new ArrayList<RepReportColumn>();
		if(!StringUtil.isEmpty(columnString)){
			list = (List<RepReportColumn>)ArrayUtil.getListFromJson(columnString, RepReportColumn.class);
		}
		return list;
	}
	
	

	/**
	 * 检查保存必须字段 
	 * @param match
	 * @return
	 */
	public String checkAttributeForSave(RepReport report,List<RepReportParam> paramList,List<RepReportColumn> columnList){
		String result = "";
		if(StringUtil.isEmpty(report.getName())){
			return "名称不能为空";
		}
		if(StringUtil.isEmpty(report.getCode())){
			return "编号不能为空";
		}
		
		if(paramList==null || paramList.size()==0){
			return "报表查询参数不能为空";
		}
		
		if(columnList==null || columnList.size()==0){
			return "报表查询结果列不能为空";
		}
		
		return result;
	}
	
	/**
	 * 从request中获取参数封装到RepReport中
	 * @param request
	 * @return
	 */
	public RepReport getReportFromRequest(HttpServletRequest request){
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		String name = StringUtil.nullToString(getRequestParameter(request, "name"));
		String code = StringUtil.nullToString(getRequestParameter(request, "code"));
		int dataSource = StringUtil.stringToInt(getRequestParameter(request, "dataSource"));
		String apiAddress = StringUtil.nullToString(getRequestParameter(request, "apiAddress"));
		
		RepReport report = new RepReport();
		report.setId(id);
		report.setApiAddress(apiAddress);
		report.setName(name);
		report.setCode(code);
		report.setDataSource(dataSource);
		
		return report;
	}
	
	@RequestMapping(value = "/pageQuery", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel pageQuery(HttpServletRequest request,RepReport sr) {
		
		BaseReturnModel result = new BaseReturnModel();
		BasePageModel<RepReport> page = new BasePageModel<RepReport>();
		// 分页查询参数
		page.setPageSize(StringUtil.stringToInt(getRequestParameter(request, "rows")));
		page.setPageNum(StringUtil.stringToInt(getRequestParameter(request, "page")));
		page.setOrder(StringUtil.nullToString(getRequestParameter(request,"order")));
		page.setSort(StringUtil.nullToString(getRequestParameter(request,"sort")));
		
		try{
			page = repReportService.queryPage(sr, page);
			result.setObj(page);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setErrMsg(e.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel delete(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		try{
			repReportService.delete(id);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
	
	
	@RequestMapping(value = "/queryColumnByReportId", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<RepReportColumn> queryColumnByReportId(HttpServletRequest request) {
		List<RepReportColumn> list = new  ArrayList<RepReportColumn>();
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		try{
			if(id>0){
				RepReportColumn col = new RepReportColumn();
				col.where(RepReportColumn.REPORTID.EQ(id));
				list = col.query();
			}
		}catch(Exception e){
			logger.error("查询报表结果列错误", e);
		}
		return list;
	}
	
	
	

	@RequestMapping(value = "/queryParamByReportId", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<RepReportParam> queryParamByReportId(HttpServletRequest request) {
		List<RepReportParam> list = new  ArrayList<RepReportParam>();
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		try{
			if(id>0){
				RepReportParam param = new RepReportParam();
				param.where(RepReportParam.REPORTID.EQ(id));
				list = param.query();
			}
		}catch(Exception e){
			logger.error("查询报表查询条件集合错误", e);
		}
		return list;
	}
	

}
