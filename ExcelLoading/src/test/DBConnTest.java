package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Ignore;
import org.junit.Test;

import com.db.DBUtil;

public class DBConnTest {

	@Test
	@Ignore
	public void test() {
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.getStmt(conn);
		String sql = "select * from Active";
		ResultSet rs = DBUtil.getRs(stmt, sql);
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			 int columnCount = rsmd.getColumnCount();   
			    // 输出列名   
			    for (int i=1; i<=columnCount; i++){   
			        System.out.print(rsmd.getColumnName(i));   
			        System.out.print("(" + rsmd.getColumnTypeName(i) + ")");   
			        System.out.print(" | ");   
			    }   
			    System.out.println();   
			    // 输出数据   
			    while (rs.next()){   
			        for (int i=1; i<=columnCount; i++){   
			            System.out.print(rs.getString(i) + " | ");   
			        }   
			        System.out.println();   
			    }   
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeAll();
		}
	}
	
	@Test
	@Ignore
	public void testPstmt(){
		Connection conn = DBUtil.getConn();
		String sql = "select * from Product where productid = ?";
		PreparedStatement pstmt = DBUtil.getPstmt(conn, sql);
		try {
			pstmt.setString(1, "510310235");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet rs = DBUtil.getRs(pstmt);
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			 int columnCount = rsmd.getColumnCount();   
			    // 输出列名   
			    for (int i=1; i<=columnCount; i++){   
			        System.out.print(rsmd.getColumnName(i));   
			        System.out.print("(" + rsmd.getColumnTypeName(i) + ")");   
			        System.out.print(" | ");   
			    }   
			    System.out.println();   
			    // 输出数据   
			    while (rs.next()){   
			        for (int i=1; i<=columnCount; i++){   
			            System.out.print(rs.getString(i) + " | ");   
			        }   
			        System.out.println();   
			    }   
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeAll();
		}
	}

	@Test
//	@Ignore
	public void test241() {
		Connection conn = DBUtil.get241Conn();
		Statement stmt = DBUtil.getStmt(conn);
		String sql = "select * from Active";
		ResultSet rs = DBUtil.getRs(stmt, sql);
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			 int columnCount = rsmd.getColumnCount();   
			    // 输出列名   
			    for (int i=1; i<=columnCount; i++){   
			        System.out.print(rsmd.getColumnName(i));   
			        System.out.print("(" + rsmd.getColumnTypeName(i) + ")");   
			        System.out.print(" | ");   
			    }   
			    System.out.println();   
			    // 输出数据   
			    while (rs.next()){   
			        for (int i=1; i<=columnCount; i++){   
			            System.out.print(rs.getString(i) + " | ");   
			        }   
			        System.out.println();   
			    }   
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeAll();
		}
	}
	
}
