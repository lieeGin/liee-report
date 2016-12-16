package com.liee.core.service.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.liee.core.dao.Systemconfig;

@Service("commonService")
public class CommonService {

	
	
	/**
	 * 获取当前配置的模版
	 * @param request
	 */
	public void freshTemplate(HttpServletRequest request){
		Systemconfig config = new Systemconfig();
		config.where(Systemconfig.KEYWORD.EQ("template_name"));
		String templateName = "default";
		try {
			List<Systemconfig> clist = config.query();
			if(clist!=null && clist.size()>0){
				templateName = clist.get(0).getValuestr();
			}
			String sessionTemplate = (String)request.getSession().getAttribute("templateName");
			if(sessionTemplate==null || !sessionTemplate.equals(templateName)){
				request.getSession().setAttribute("templateName", templateName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
