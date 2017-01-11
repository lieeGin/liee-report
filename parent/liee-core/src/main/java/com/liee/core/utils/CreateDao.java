package com.liee.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdao.base.QueryDao;
import com.jdao.util.CreateDaoUtil;

public class CreateDao {
	static Connection conn = null;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String driverUrl = "jdbc:mysql://localhost:3306/mydatabase?useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf-8&amp;zeroDateTimeBehavior=convertToNull";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(driverUrl, username, password);
		} catch (Exception e) {
		}
	}

	public void createDao4jdbc(String packageName, String tableName, String descFile) throws Exception {

		if (tableName == null || "".equals(tableName)) {// 如果没传表名，则生成整个数据库的DO
			List<String> tableNames = showTables(conn);
			for (String tableName1 : tableNames) {
				try {
					CreateDaoUtil.createFileForce(packageName, tableName1, descFile, conn, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			CreateDaoUtil.createFileForce(packageName, tableName, descFile, conn, "utf-8");
		}
	}

	public static List<String> showTables(Connection conn) throws SQLException {
		String sql = "show tables";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		List<String> tableNames = new ArrayList<String>();
		while (rs.next()) {
			tableNames.add(rs.getString(1));
		}
		return tableNames;
	}

	public static void test() throws Exception {
		String sql = "SELECT * from sys_user";
		QueryDao qd = new QueryDao(conn, sql);
		System.out.println(qd.size());
	}

	public static void main(String[] args) throws Exception {
		new CreateDao().createDao4jdbc("com.liee.core.dao", "sys_code", System.getProperty("user.dir") + "\\src\\main\\java\\com\\liee\\core\\dao");
		// test();
	}
}
