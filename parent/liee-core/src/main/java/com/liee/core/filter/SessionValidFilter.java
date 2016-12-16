package com.liee.core.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liee.core.common.ApplicationProperties;
import com.liee.core.utils.StringUtil;

/**
 * 针对登录的过滤器
 * @author lieeGin
 *
 */
public class SessionValidFilter implements Filter  {
	
	
	
     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,  FilterChain chain) throws IOException, ServletException {
         
         //对request和response进行一些预处理
    	servletRequest.setCharacterEncoding("UTF-8");
    	servletResponse.setCharacterEncoding("UTF-8");
    	servletResponse.setContentType("text/html;charset=UTF-8");
        
         HttpServletRequest request = (HttpServletRequest) servletRequest; 
         HttpServletResponse response = (HttpServletResponse) servletResponse;   
     	
         String path = request.getRequestURL().toString();
      //   System.out.println("filter 拦截的请求路径 :"+path);
         String loginPage = request.getContextPath()+"/admin/login";
         
        List<String> whiteList = ApplicationProperties.getInstance().getSessionWhiteList();
         
         
         // 拦截  后台请求，验证是否登录。 
         if(needCheckSession(whiteList,path)){
        	 String userName = (String)request.getSession().getAttribute("userName");
        	 if(StringUtil.isEmpty(userName)){
    		   if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //如果是ajax请求响应头会有，x-requested-with  
 		            response.setHeader("sessionstatus", "timeOut");//在响应头设置session状态  
 		            response.addHeader("loginPath", loginPage);
 		        } else {
 		        	response.getWriter().write(getScript(loginPage).toString());
 		        }
    		    
        	 }else{
        		 chain.doFilter(servletRequest, servletResponse);  //让目标资源执行，放行
        	 }
         }else{
        	 chain.doFilter(servletRequest, servletResponse);  //让目标资源执行，放行
         }
         
     }
     
     /**
      * 检查路径是否在session过滤的白名单中
      * @param whiteList
      * @param path
      * @return
      */
     public boolean needCheckSession(List<String> whiteList,String path){
    	 boolean result = true;
    	 for (String url : whiteList) {
    		if(url.startsWith("*") && !url.endsWith("*")){  // 以* 开头  ，非* 结尾
    			 if(path.endsWith(url.substring(1, url.length()))){  
    				result = false;
    				break;
    			 }
    		} else if(!url.startsWith("*") && url.endsWith("*")){
    			 if(path.indexOf(url.substring(0, url.length()-1)) != -1){  
     				result = false;
     				break;
     			 }
    		}else if(path.indexOf(url) != -1){
				result = false;
				break;
			}
		}
    	 
    	 return result;
     }
    
    
    /**
	 * 当session无效时转发（适用于ajax访问）到页面要执行的js脚本
	 * 
	 * @param context
	 * @return
	 */
	private String getScript(String context) {
		StringBuffer script = new StringBuffer(
				"<script language='javascript'>var win=window;var i=0;while(win.parent!=win&&(i++<10)){win=win.parent;} " 
						+ " win.top.location.href='" + context + "'; </script>");
		return script.toString().trim();
	}
    

	public void destroy() {
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
 
	
}
