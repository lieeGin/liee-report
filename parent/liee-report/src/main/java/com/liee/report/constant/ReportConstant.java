package com.liee.report.constant;

import java.util.HashMap;
import java.util.Map;

public class ReportConstant {

	
	/**
	 * 系统参数 groovy文件存放路径参数key值
	 */
	public final static String CONFIG_KEY_GROOVY_PATH="groovy_file_path";
	
	public final static Map<String,String> compareWayMap = new HashMap<String,String>();
	static{
		compareWayMap.put("gt", ">");
		compareWayMap.put("ge", ">=");
		compareWayMap.put("lt", "<");
		compareWayMap.put("le", "<=");
		compareWayMap.put("eq", "=");
		compareWayMap.put("like", "like");
		compareWayMap.put("in", "in");
	}
	
}
