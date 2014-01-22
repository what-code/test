package test;

import java.sql.*;

/**
 * Title:TestJdbc.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-8-16
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestJdbc {
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static Connection getCoonection() {
		String user = "b5m";
		String password = "iz3n3s0ft";
		String url = "jdbc:mysql://10.10.105.8:3306/b5m_you";
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return con;
	}

	public static void closeCoonection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Statement stmt = null;
		ResultSet rs = null;
		String sqlstr = "";
		try {
			stmt = getCoonection().createStatement();
			sqlstr = "select * from you_hotel_detail where id=44304;";
			rs = stmt.executeQuery(sqlstr);
			ResultSetMetaData rsmd = rs.getMetaData();
			int j = 0;
			j = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 0; i < j; i++) {
					System.out.println("<" + rsmd.getColumnName(i+1) + ">" +rs.getString(i + 1));
				}
				System.out.println();
			}
		}catch (Exception e2) {
			System.out.println("数据库存在异常！");
			System.out.println(e2.toString());
		}finally{
			try {
				stmt.close();
				closeCoonection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
}
