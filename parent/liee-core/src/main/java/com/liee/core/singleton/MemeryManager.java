package com.liee.core.singleton;

import java.util.HashMap;
import java.util.Map;

public class MemeryManager {

	private static MemeryManager manager = new MemeryManager();
	
	// 菜单权限缓存
	private static  Map<Integer,Map<Integer,Integer>>  authMap =  new HashMap<Integer,Map<Integer,Integer>>();

	private MemeryManager() {
		
	}

	public static MemeryManager getInstance() {
		return manager;
	}

	public static Map<Integer, Map<Integer, Integer>> getAuthMap() {
		return authMap;
	}

	public static void setAuthMap(Map<Integer, Map<Integer, Integer>> authMap) {
		MemeryManager.authMap = authMap;
	}
	
	

}
