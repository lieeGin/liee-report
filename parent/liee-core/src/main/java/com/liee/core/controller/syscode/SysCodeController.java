package com.liee.core.controller.syscode;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.dao.SysCode;
import com.liee.core.service.syscode.SysCodeService;
import com.liee.core.utils.StringUtil;

@Controller
@RequestMapping("/sysCode") 
public class SysCodeController extends BaseController{
	
	@Autowired
	private SysCodeService sysCodeService;
	
	@RequestMapping(value = "/saveOrUpdate", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel saveOrUpdate(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		String parentCode = StringUtil.nullToString(getRequestParameter(request, "parentCode"));
		int sort = StringUtil.stringToInt(getRequestParameter(request, "sort"));
		String code = StringUtil.nullToString(getRequestParameter(request, "code"));
		String text = StringUtil.nullToString(getRequestParameter(request, "text"));
		String remark = StringUtil.nullToString(getRequestParameter(request, "remark"));
		int displayOrder = StringUtil.stringToInt(getRequestParameter(request, "displayOrder"));
		
		SysCode syscode = new SysCode();
		syscode.setId(id);
		syscode.setParentCode(parentCode);
		syscode.setSort(sort);
		syscode.setCode(code);
		syscode.setText(text);
		syscode.setRemark(remark);
		syscode.setDisplayOrder(displayOrder);
		
		
		if(StringUtil.isEmpty(code) || StringUtil.isEmpty(text)){
			rm.setSuccess(false);
			rm.setErrMsg("字典编号和字典文本不能为空");
			return rm;
		}
		
		try{
			sysCodeService.saveOrUpdate(syscode);
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		
		
		return rm;
	}
	
	
	/**
	 * 查询所有字典
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllCode", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List codeTree(HttpServletRequest request) {
		List<SysCode> codeList = sysCodeService.getAllCode(); 
		//手动添加根节点
		SysCode root = new SysCode();
		root.setId(0);
		root.setCode("ALL");
		root.setParentCode("");
		root.setText("所有字典");
		codeList.add(0, root); 
		return codeList;
	}
	
	/**
	 * 根据ID查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getById", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel getById(HttpServletRequest request) {
		
		BaseReturnModel result = new BaseReturnModel();
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		SysCode code = new SysCode();
		code.setId(id);
		try {
			code.setLoggerOn(true);
			code.where(SysCode.ID.EQ(id));
			code = code.queryById();
			result.setObj(code);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMsg("查询字典失败");
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel delete(HttpServletRequest request) {
		
		BaseReturnModel result = new BaseReturnModel();
		int id = StringUtil.stringToInt(getRequestParameter(request, "id"));
		SysCode code = new SysCode();
		try {
			code.where(SysCode.ID.EQ(id));
			code.delete();
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMsg("删除字典失败");
		}
		return result;
	}
	
}
