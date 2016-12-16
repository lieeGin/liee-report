package com.liee.core.controller.systemconfig;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liee.core.common.BasePageModel;
import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.dao.Systemconfig;
import com.liee.core.log.Logger;
import com.liee.core.service.systemconfig.SystemconfigService;
import com.liee.core.utils.StringUtil;

@Controller
@RequestMapping("/systemconfig") 
public class SystemconfigController extends BaseController{
	
	Logger log = Logger.getLogger();
	
	@Autowired
	private SystemconfigService systemconfigService;
	
	
	@RequestMapping(value = "/saveOrUpdate", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel saveOrUpdate(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		String keyword = getRequestParameter(request, "keyword");
		String valuestr = getRequestParameter(request, "valuestr");
		String remark = getRequestParameter(request, "remark");
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		Systemconfig systemconfig = new Systemconfig();
		systemconfig.setKeyword(keyword);
		systemconfig.setValuestr(valuestr);
		systemconfig.setRemark(remark);
		systemconfig.setId(id);
		
		if(StringUtil.isEmpty(keyword)){
			rm.setSuccess(false);
			rm.setErrMsg("参数编号不能为空");
			return rm;
		}
		
		try{
			systemconfigService.saveOrUpdate(systemconfig);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/pageQuery", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel pageQuery(HttpServletRequest request,Systemconfig sr) {
		
		BaseReturnModel result = new BaseReturnModel();
		BasePageModel<Systemconfig> page = new BasePageModel<Systemconfig>();
		// 分页查询参数
		page.setPageSize(StringUtil.stringToInt(getRequestParameter(request, "rows")));
		page.setPageNum(StringUtil.stringToInt(getRequestParameter(request, "page")));
		try{
			page = systemconfigService.queryPage(sr, page);
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
			systemconfigService.delete(id);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	

}
