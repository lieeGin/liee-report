package com.liee.report.groovy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;


public class GroovyEngine {
	 
	
	 /**
	  * 通过groovy脚本获取数据
	  * @param scriptName
	  */
 	 public static List<Map<String,Object>> getDataFromScript(String scriptName){
 		List<Map<String,Object>> result = new  ArrayList<Map<String,Object>>();
 		
 		return result;
 	 }
	
 	 
 	 
	  public static void main(String[] args) throws Exception {
          
		    String[] roots = new String[] { "C:/Users/A123/Desktop/" };  
	        //通过指定的roots来初始化GroovyScriptEngine  
	        GroovyScriptEngine gse = new GroovyScriptEngine(roots);  
	        GroovyObject groovyObject = (GroovyObject) gse.loadScriptByName("test.groovy").newInstance();  
	        
	        Map<String,Object> param = new HashMap<String,Object>();
	        param.put("name", "liee");
	        param.put("age", 26);
	        
	       Map<String,Object> result = (Map<String,Object>) groovyObject.invokeMethod("getData", param);    
	       
	       System.out.println("java output:");
	       for (Entry<String,Object> entry : result.entrySet()) {
	    	   System.out.println(entry.getKey()+":"+entry.getValue());
	       }
	  }
	  
}
