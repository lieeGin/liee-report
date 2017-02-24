package com.liee.core.servlet;

import java.sql.SQLException;

import com.jdao.base.DaoFactory;
import com.jdao.base.QueryDao;
import com.jdao.dbHandler.JdaoHandler;
import com.jdao.dbHandlerImpl.JdaoHandlerImplSingleTon;
import com.liee.core.utils.JedisUtil;
import com.mchange.v2.c3p0.cfg.C3P0ConfigXmlUtils;

public class InitServlet {
	
	public static void init(){
		initDb();
		JedisUtil.init();  // 初始化reids
		System.out.println("init()");
	}
	
	public static void initDb() {
		C3P0ConfigXmlUtils.setXmlPath("/c3p0.xml");
		DaoFactory.setJdaoHandler(getDBHandler4c3p0SingleTon_test());
	}

	public static JdaoHandler getDBHandler4c3p0SingleTon_test() {
		JdaoHandler jdaoHandler = new JdaoHandlerImplSingleTon("billiardadmin");
		try {
			QueryDao qd = jdaoHandler.executeQuery("select 1");
			System.out.println("db init ok" + qd.fieldValue(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jdaoHandler;
	}
}
