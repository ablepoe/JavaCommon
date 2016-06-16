package test;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;

import com.db.DBUtil;
import com.entity.Product;
import com.poi.LoadExcel;

public class LoadExcelTest {
	
	
	@Test
	public void test(){
		//update sheet 0
//		testType1();
//		test241Type1();
		//update2 sheet 0 
//		testType2();
//		test241Type2();
		//insert sheet 0
//		testType3();
//		testType3_1();
//		test241Type3();
//		test241Type3_1();
		//update sheet 0
//		testType4();
		test241Type4();
	}

	public void testType1() {
		LoadExcel le = new LoadExcel();
		File file = new File("update.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 2, 0);
		List<Product> products = translistType1(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getCategory() + " | "+products.get(i).getMarketPrice() + " | "+products.get(i).getSeriesTop()  );
		}
		Connection conn = DBUtil.getConn();
		String sql = "update Product set MarketPrice=?,Category=?,SeriesTop=? where ProductId=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setDouble(1, products.get(i).getMarketPrice());
				pstmt.setString(2, products.get(i).getCategory());
				pstmt.setLong(3, products.get(i).getSeriesTop());
				pstmt.setLong(4, products.get(i).getProductid());
				pstmt.addBatch();
			}
			int[] results = pstmt.executeBatch();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}
			}
			System.out.println("total count is "+count);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
	
	public void testType2() {
		LoadExcel le = new LoadExcel();
		File file = new File("update2.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 1, 0);
		List<Product> products = translistType2(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getActivePrice() + " | "+products.get(i).getActiveBeginDate() + " | "+products.get(i).getActiveEndDate()  );
		}
		Connection conn = DBUtil.getConn();
		String sql = "update Product set ActivePrice=?,ActiveBeginDate=?,ActiveEndDate=? where ProductId=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setDouble(1, products.get(i).getActivePrice());
				pstmt.setString(2, products.get(i).getActiveBeginDate());
				pstmt.setString(3, products.get(i).getActiveEndDate());
				pstmt.setLong(4, products.get(i).getProductid());
				pstmt.addBatch();
//				System.out.println(pstmt.toString());
			}
			int[] results = pstmt.executeBatch();
			List<Integer> errli = new ArrayList<Integer>();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}else{
					errli.add(i);
				}
			}
			System.out.println("resluts.length" + results.length);
			System.out.println("total count is "+count);
			for (int i = 0; i < errli.size(); i++) {
				System.out.println(errli.get(i));
			}
			System.out.println(errli.size());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
	
	public void testType3() {
		LoadExcel le = new LoadExcel();
		File file = new File("insert.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 0, 0);
		List<Product> products = translistType3(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getName() + " | "+products.get(i).getCategory() + " | "+products.get(i).getKeywords() 
					+" | "+ products.get(i).getIntroduce() +" | "+ products.get(i).getSeriesName() +" | "+ products.get(i).getSeriesTop() +" | "+ products.get(i).getStandard() 
					+" | "+ products.get(i).getColor() +" | "+ products.get(i).getMarketPrice() +" | "+ products.get(i).getActivePrice() +" | "+ products.get(i).getActiveBeginDate() 
					+" | "+ products.get(i).getActiveEndDate() +" | "+ products.get(i).getOrdernum() +" | "+ products.get(i).getPurchase() +" | "+ products.get(i).getRelation() );
		}
		/*Connection conn = DBUtil.getConn();
		String sql = "insert into Product(Productid,Name,Category,Keywords,Introduce,SeriesName,SeriesTop,Standard,Color,MarketPrice,"
				+ "ActivePrice,ActiveBeginDate,ActiveEndDate,Ordernum,Purchase,Relation) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setLong(1, products.get(i).getProductid());
				pstmt.setString(2, products.get(i).getName());
				pstmt.setString(3, products.get(i).getCategory());
				pstmt.setString(4, products.get(i).getKeywords());
				pstmt.setString(5, products.get(i).getIntroduce());
				pstmt.setString(6, products.get(i).getSeriesName());
				pstmt.setLong(7, products.get(i).getSeriesTop());
				pstmt.setString(8, products.get(i).getStandard());
				pstmt.setString(9, products.get(i).getColor());
				pstmt.setDouble(10, products.get(i).getMarketPrice());
				pstmt.setDouble(11, products.get(i).getActivePrice());
				pstmt.setString(12, products.get(i).getActiveBeginDate());
				pstmt.setString(13, products.get(i).getActiveEndDate());
				pstmt.setInt(14, products.get(i).getOrdernum());
				pstmt.setInt(15, products.get(i).getPurchase());
				pstmt.setString(16, products.get(i).getRelation());
				pstmt.addBatch();
//				System.out.println(pstmt.toString());
			}
			int[] results = pstmt.executeBatch();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}
			}
			System.out.println("total count is "+count);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}*/
	}
	
	public void testType3_1() {
		LoadExcel le = new LoadExcel();
		File file = new File("insert.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 0, 0);
		List<Product> products = translistType3(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getName() + " | "+products.get(i).getCategory() + " | "+products.get(i).getKeywords() 
					+" | "+ products.get(i).getIntroduce() +" | "+ products.get(i).getSeriesName() +" | "+ products.get(i).getSeriesTop() +" | "+ products.get(i).getStandard() 
					+" | "+ products.get(i).getColor() +" | "+ products.get(i).getMarketPrice() +" | "+ products.get(i).getActivePrice() +" | "+ products.get(i).getActiveBeginDate() 
					+" | "+ products.get(i).getActiveEndDate() +" | "+ products.get(i).getOrdernum() +" | "+ products.get(i).getPurchase() +" | "+ products.get(i).getRelation() );
		}
		Connection conn = DBUtil.getConn();
		String sql = "update Product set Name=?,Category=?,Keywords=?,Introduce=?,SeriesName=?,SeriesTop=?,Standard=?,Color=?,MarketPrice=?,"
				+ "ActivePrice=?,ActiveBeginDate=?,ActiveEndDate=?,Ordernum=?,Purchase=?,Relation=? where Productid=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setLong(16, products.get(i).getProductid());
				pstmt.setString(1, products.get(i).getName());
				pstmt.setString(2, products.get(i).getCategory());
				pstmt.setString(3, products.get(i).getKeywords());
				pstmt.setString(4, products.get(i).getIntroduce());
				pstmt.setString(5, products.get(i).getSeriesName());
				pstmt.setLong(6, products.get(i).getSeriesTop());
				pstmt.setString(7, products.get(i).getStandard());
				pstmt.setString(8, products.get(i).getColor());
				pstmt.setDouble(9, products.get(i).getMarketPrice());
				pstmt.setDouble(10, products.get(i).getActivePrice());
				pstmt.setString(11, products.get(i).getActiveBeginDate());
				pstmt.setString(12, products.get(i).getActiveEndDate());
				pstmt.setInt(13, products.get(i).getOrdernum());
				pstmt.setInt(14, products.get(i).getPurchase());
				pstmt.setString(15, products.get(i).getRelation());
				pstmt.addBatch();
//				System.out.println(pstmt.toString());
			}
			int[] results = pstmt.executeBatch();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}
			}
			System.out.println("total count is "+count);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
	

	private List<Product> translistType1(List<String> li){
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		for (int i = 0; i < li.size(); i++) {
			int count = i%4;
//			System.out.println(count + " | "+li.get(i));
			switch(count){
			case 0:
				product = new Product();
				product.setProductid( Long.parseLong(li.get(i)) );
				break;
			case 1:
				product.setMarketPrice(Double.parseDouble(li.get(i)) );
				break;
			case 2:
				product.setCategory(li.get(i) );
				break;
			case 3:
				product.setSeriesTop( Long.parseLong(li.get(i)) );
				products.add(product);
				break;
			default:
				break;
			}
		}
		return products;
	}
	
	private List<Product> translistType2(List<String> li){
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		for (int i = 0; i < li.size(); i++) {
			int count = i%4;
			System.out.println(count + " | "+li.get(i));
			switch(count){
			case 0:
				product = new Product();
				product.setProductid( Long.parseLong(li.get(i)) );
				break;
			case 1:
				product.setActivePrice(Double.parseDouble(li.get(i)) );
				break;
			case 2:
				product.setActiveBeginDate(li.get(i));
				break;
			case 3:
				product.setActiveEndDate(li.get(i));
				products.add(product);
				break;
			default:
				break;
			}
		}
		return products;
	}
	
	private List<Product> translistType3(List<String> li){
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		for (int i = 0; i < li.size(); i++) {
			int count = i%16;
			System.out.println(count + " | "+li.get(i));
			switch(count){
			case 0:
				product = new Product();
				product.setProductid( Long.parseLong(li.get(i)) );
				break;
			case 1:
				product.setName(li.get(i));
				break;
			case 2:
				product.setCategory(li.get(i));
				break;
			case 3:
				product.setKeywords(li.get(i));
				break;
			case 4:
				product.setIntroduce(li.get(i));
				break;
			case 5:
				product.setSeriesName(li.get(i));
				break;
			case 6:
				product.setSeriesTop( Long.parseLong(li.get(i)) );
				break;
			case 7:
				product.setStandard(li.get(i));
				break;
			case 8:
				product.setColor(li.get(i));
				break;
			case 9:
				product.setMarketPrice( Double.parseDouble(li.get(i)) );
				break;
			case 10:
				product.setActivePrice( Double.parseDouble(li.get(i)) );
				break;
			case 11:
				product.setActiveBeginDate(li.get(i));
				break;
			case 12:
				product.setActiveEndDate(li.get(i));
				break;
			case 13:
				product.setOrdernum( Integer.parseInt(li.get(i)) );
				break;
			case 14:
				product.setPurchase( Integer.parseInt(li.get(i)) );
				break;
			case 15:
				product.setRelation(li.get(i));
				products.add(product);
				break;
			default:
				break;
			}
		}
		return products;
	}
	
	private List<Product> translistType4(List<String> li){
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		for (int i = 0; i < li.size(); i++) {
			int count = i%2;
//			System.out.println(count + " | "+li.get(i));
			switch(count){
			case 0:
				product = new Product();
				product.setProductid( Long.parseLong(li.get(i)) );
				break;
			case 1:
				product.setSeriesTop( Long.parseLong(li.get(i)) );
				products.add(product);
				break;
			default:
				break;
			}
		}
		return products;
	}
	
	
	public void test241Type1() {
		LoadExcel le = new LoadExcel();
		File file = new File("update.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 2, 0);
		List<Product> products = translistType1(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getCategory() + " | "+products.get(i).getMarketPrice() + " | "+products.get(i).getSeriesTop()  );
		}
		Connection conn = DBUtil.get241Conn();
		String sql = "update Product set MarketPrice=?,Category=?,SeriesTop=? where ProductId=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setDouble(1, products.get(i).getMarketPrice());
				pstmt.setString(2, products.get(i).getCategory());
				pstmt.setLong(3, products.get(i).getSeriesTop());
				pstmt.setLong(4, products.get(i).getProductid());
				pstmt.addBatch();
			}
			int[] results = pstmt.executeBatch();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}
			}
			System.out.println("total count is "+count);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
	
	public void test241Type2() {
		LoadExcel le = new LoadExcel();
		File file = new File("update2.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 1, 0);
		List<Product> products = translistType2(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getActivePrice() + " | "+products.get(i).getActiveBeginDate() + " | "+products.get(i).getActiveEndDate()  );
		}
		Connection conn = DBUtil.get241Conn();
		String sql = "update Product set ActivePrice=?,ActiveBeginDate=?,ActiveEndDate=? where ProductId=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setDouble(1, products.get(i).getActivePrice());
				pstmt.setString(2, products.get(i).getActiveBeginDate());
				pstmt.setString(3, products.get(i).getActiveEndDate());
				pstmt.setLong(4, products.get(i).getProductid());
				pstmt.addBatch();
//				System.out.println(pstmt.toString());
			}
			int[] results = pstmt.executeBatch();
			List<Integer> errli = new ArrayList<Integer>();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}else{
					errli.add(i);
				}
			}
			System.out.println("resluts.length" + results.length);
			System.out.println("total count is "+count);
			for (int i = 0; i < errli.size(); i++) {
				System.out.println(errli.get(i));
			}
			System.out.println(errli.size());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
	
	public void test241Type3() {
		LoadExcel le = new LoadExcel();
		File file = new File("insert.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 0, 0);
		List<Product> products = translistType3(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getName() + " | "+products.get(i).getCategory() + " | "+products.get(i).getKeywords() 
					+" | "+ products.get(i).getIntroduce() +" | "+ products.get(i).getSeriesName() +" | "+ products.get(i).getSeriesTop() +" | "+ products.get(i).getStandard() 
					+" | "+ products.get(i).getColor() +" | "+ products.get(i).getMarketPrice() +" | "+ products.get(i).getActivePrice() +" | "+ products.get(i).getActiveBeginDate() 
					+" | "+ products.get(i).getActiveEndDate() +" | "+ products.get(i).getOrdernum() +" | "+ products.get(i).getPurchase() +" | "+ products.get(i).getRelation() );
		}
		Connection conn = DBUtil.get241Conn();
		String sql = "insert into Product(Productid,Name,Category,Keywords,Introduce,SeriesName,SeriesTop,Standard,Color,MarketPrice,"
				+ "ActivePrice,ActiveBeginDate,ActiveEndDate,Ordernum,Purchase,Relation) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setLong(1, products.get(i).getProductid());
				pstmt.setString(2, products.get(i).getName());
				pstmt.setString(3, products.get(i).getCategory());
				pstmt.setString(4, products.get(i).getKeywords());
				pstmt.setString(5, products.get(i).getIntroduce());
				pstmt.setString(6, products.get(i).getSeriesName());
				pstmt.setLong(7, products.get(i).getSeriesTop());
				pstmt.setString(8, products.get(i).getStandard());
				pstmt.setString(9, products.get(i).getColor());
				pstmt.setDouble(10, products.get(i).getMarketPrice());
				pstmt.setDouble(11, products.get(i).getActivePrice());
				pstmt.setString(12, products.get(i).getActiveBeginDate());
				pstmt.setString(13, products.get(i).getActiveEndDate());
				pstmt.setInt(14, products.get(i).getOrdernum());
				pstmt.setInt(15, products.get(i).getPurchase());
				pstmt.setString(16, products.get(i).getRelation());
				pstmt.addBatch();
//				System.out.println(pstmt.toString());
			}
			int[] results = pstmt.executeBatch();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}
			}
			System.out.println("total count is "+count);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
	
	public void test241Type3_1() {
		LoadExcel le = new LoadExcel();
		File file = new File("insert.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 0, 0);
		List<Product> products = translistType3(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getName() + " | "+products.get(i).getCategory() + " | "+products.get(i).getKeywords() 
					+" | "+ products.get(i).getIntroduce() +" | "+ products.get(i).getSeriesName() +" | "+ products.get(i).getSeriesTop() +" | "+ products.get(i).getStandard() 
					+" | "+ products.get(i).getColor() +" | "+ products.get(i).getMarketPrice() +" | "+ products.get(i).getActivePrice() +" | "+ products.get(i).getActiveBeginDate() 
					+" | "+ products.get(i).getActiveEndDate() +" | "+ products.get(i).getOrdernum() +" | "+ products.get(i).getPurchase() +" | "+ products.get(i).getRelation() );
		}
		Connection conn = DBUtil.get241Conn();
		String sql = "update Product set Name=?,Category=?,Keywords=?,Introduce=?,SeriesName=?,SeriesTop=?,Standard=?,Color=?,MarketPrice=?,"
				+ "ActivePrice=?,ActiveBeginDate=?,ActiveEndDate=?,Ordernum=?,Purchase=?,Relation=? where Productid=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setLong(16, products.get(i).getProductid());
				pstmt.setString(1, products.get(i).getName());
				pstmt.setString(2, products.get(i).getCategory());
				pstmt.setString(3, products.get(i).getKeywords());
				pstmt.setString(4, products.get(i).getIntroduce());
				pstmt.setString(5, products.get(i).getSeriesName());
				pstmt.setLong(6, products.get(i).getSeriesTop());
				pstmt.setString(7, products.get(i).getStandard());
				pstmt.setString(8, products.get(i).getColor());
				pstmt.setDouble(9, products.get(i).getMarketPrice());
				pstmt.setDouble(10, products.get(i).getActivePrice());
				pstmt.setString(11, products.get(i).getActiveBeginDate());
				pstmt.setString(12, products.get(i).getActiveEndDate());
				pstmt.setInt(13, products.get(i).getOrdernum());
				pstmt.setInt(14, products.get(i).getPurchase());
				pstmt.setString(15, products.get(i).getRelation());
				pstmt.addBatch();
//				System.out.println(pstmt.toString());
			}
			int[] results = pstmt.executeBatch();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
//				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}
			}
			System.out.println("total count is "+count);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
	
	public void testType4() {
		LoadExcel le = new LoadExcel();
		File file = new File("update_SeriesTop.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 1, 0);
		List<Product> products = translistType4(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getCategory() + " | "+products.get(i).getMarketPrice() + " | "+products.get(i).getSeriesTop()  );
		}
		Connection conn = DBUtil.getConn();
		String sql = "update Product set SeriesTop=? where ProductId=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setLong(1, products.get(i).getSeriesTop());
				pstmt.setLong(2, products.get(i).getProductid());
				pstmt.addBatch();
			}
			int[] results = pstmt.executeBatch();
			List<Integer> errli = new ArrayList<Integer>();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}else{
					errli.add(i);
				}
			}
			System.out.println("resluts.length" + results.length);
			System.out.println("total count is "+count);
			for (int i = 0; i < errli.size(); i++) {
				System.out.println(errli.get(i));
			}
			System.out.println(errli.size());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
	
	@Test
	public void test241Type4() {
		LoadExcel le = new LoadExcel();
		File file = new File("update_SeriesTop.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		//row coloum sheetnum
		List<String> li = le.readExcel(workbook, 2, 1, 0);
		List<Product> products = translistType4(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getProductid() +" | "+ products.get(i).getCategory() + " | "+products.get(i).getMarketPrice() + " | "+products.get(i).getSeriesTop()  );
		}
		Connection conn = DBUtil.get241Conn();
		String sql = "update Product set SeriesTop=? where ProductId=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setLong(1, products.get(i).getSeriesTop());
				pstmt.setLong(2, products.get(i).getProductid());
				pstmt.addBatch();
			}
			int[] results = pstmt.executeBatch();
			List<Integer> errli = new ArrayList<Integer>();
			conn.commit();
			int count = 0;
			for (int i = 0; i < results.length; i++) {
				System.out.println(results[i]);
				if(1 == results[i]){
					count++;
				}else{
					errli.add(i);
				}
			}
			System.out.println("resluts.length" + results.length);
			System.out.println("total count is "+count);
			for (int i = 0; i < errli.size(); i++) {
				System.out.println(errli.get(i));
			}
			System.out.println(errli.size());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.closeAll();
		}
	}
}
