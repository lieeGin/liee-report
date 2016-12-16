package com.liee.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.liee.core.utils.StringUtil;

public class ApplicationProperties {

	private static ApplicationProperties instance = new ApplicationProperties();
	private static List<String> sessionWhiteList = new ArrayList<String>();   // session 拦截白名单
  
	private static Properties prop = new Properties();   
	private static InputStream in = ApplicationProperties.class.getResourceAsStream("/base.properties");   
	
	static {
		 try {   
			 prop.load(in);
        	 String str = prop.getProperty("session_white_list").trim();
             if(!StringUtil.isEmpty(str)){
             	String[] strs = str.split(";");
                 if(strs!=null && strs.length>0){
                 	for (int i = 0; i < strs.length; i++) {
                 		if(!StringUtil.isEmpty(strs[i])){
                 			sessionWhiteList.add(strs[i]);
                 		}
 					}
                 }
             } 
         } catch (IOException e) {   
             e.printStackTrace();   
         } 
	}
  
    private ApplicationProperties() {  
    	 
    }   
    
    public static ApplicationProperties getInstance(){
    	return instance;
    }
    
    public static List<String> getSessionWhiteList(){
    	return sessionWhiteList;
    }
  
    public static void main(String args[]){   
    	List<String> list = ApplicationProperties.getInstance().getSessionWhiteList();
        System.out.println(list.size());   
    }   
}
