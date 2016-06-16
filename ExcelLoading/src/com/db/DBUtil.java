package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class DBUtil {

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static Statement stmt;
	private static ResultSet rs;
	
	public static Connection getConn(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.10.83:3306/dhc";
			String username = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url,username,password);
			conn.setAutoCommit(false);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection get241Conn(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.2.241:3306/dhc?characterEncoding=utf8";
			String username = "root";
			String password = "AW120brac%";
			conn = DriverManager.getConnection(url,username,password);
			conn.setAutoCommit(false);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection get2004Conn(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.200.4:1521:test";
			String username = "eshop";
			String password = "1234";
			conn = DriverManager.getConnection(url,username,password);
			conn.setAutoCommit(false);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection get290Conn(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.2.90:1521:dhcweb2";
			String username = "eshop";
			String password = "1234";
			conn = DriverManager.getConnection(url,username,password);
			conn.setAutoCommit(false);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PreparedStatement getPstmt(Connection conn, String sql){
		try {
			pstmt = conn.prepareStatement(sql);
			return pstmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Statement getStmt(Connection conn){
		try {
			stmt = conn.createStatement();
			return stmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ResultSet getRs(Statement stmt, String sql){
		try {
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ResultSet getRs(PreparedStatement pstmt){
		try {
			ResultSet rs = pstmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeConn(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeStmt(Statement stmt){
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeRs(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeAll(){
		closeConn(conn);
		closeStmt(stmt);
		closeRs(rs);
	}
}
