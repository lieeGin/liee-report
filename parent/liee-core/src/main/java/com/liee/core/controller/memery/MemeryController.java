package com.liee.core.controller.memery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liee.core.common.BaseReturnModel;
import com.liee.core.controller.BaseController;
import com.liee.core.log.Logger;
import com.liee.core.singleton.MemeryManager;
import com.liee.core.utils.StringUtil;

@Controller
@RequestMapping("/memery") 
public class MemeryController extends BaseController{
	
	Logger log = Logger.getLogger();
	
	
	@RequestMapping(value = "/getUserMenuAuth", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<Map<String,Object>> getUserMenuAuth(HttpServletRequest request) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
  		 Map<Integer,Map<Integer,Integer>>  authMap = MemeryManager.getInstance().getAuthMap() ;
  		 if(authMap!=null){
  			 for (Entry<Integer, Map<Integer, Integer>> entry : authMap.entrySet()) {
				Map<String,Object> record = new HashMap<>();
				record.put("userId", entry.getKey());
				record.put("menuAuthStr", JSONObject.valueToString(entry.getValue()));
				result.add(record);
			}
  		 }
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/clearUserMenuAuth", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseReturnModel clearUserMenuAuth(HttpServletRequest request) {
		BaseReturnModel rm = new BaseReturnModel();
		int userId = StringUtil.stringToInt(getRequestParameter(request, "userId"));
		try{
	   		 Map<Integer,Map<Integer,Integer>>  authMap = MemeryManager.getInstance().getAuthMap() ;
	   		 authMap.put(userId, null);  // 清空
			rm.setSuccess(true);
		}catch(Exception e){
			rm.setSuccess(false);
			rm.setErrMsg(e.getMessage());
		}
		return rm;
	}
	

}
