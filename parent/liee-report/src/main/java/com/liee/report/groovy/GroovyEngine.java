package com.liee.report.groovy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;


public class GroovyEngine {
	 
	  public static void main(String[] args) throws Exception {
          
		    String[] roots = new String[] { "C:/Users/A123/Desktop/" };  
	        //通过指定的roots来初始化GroovyScriptEngine  
	        GroovyScriptEngine gse = new GroovyScriptEngine(roots);  
	        GroovyObject groovyObject = (GroovyObject) gse.loadScriptByName("test1.groovy").newInstance();  
	        
	        Map<String,Object> param = new HashMap<String,Object>();
	        Map<String,Object> obj1 = new HashMap<String,Object>();
	        obj1.put("value", "admin");
	        obj1.put("compareWay", "like");
	        param.put("user_name", obj1);
	        
	        groovyObject.setProperty("pageNum", "1"); 
	        groovyObject.setProperty("pageSize", "10"); 
	        groovyObject.setProperty("order", "desc"); 
	        groovyObject.setProperty("sort", "id"); 
	        
	       Map<String,Object> result = (Map<String,Object>) groovyObject.invokeMethod("getData", param);    
	       
	       System.out.println("java output:");
	       for (Entry<String,Object> entry : result.entrySet()) {
	    	   System.out.println(entry.getKey()+":"+entry.getValue());
	       }
	  }
	  
}
