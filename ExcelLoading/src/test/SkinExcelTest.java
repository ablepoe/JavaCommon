package test;


import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;

import com.db.DBUtil;
import com.entity.skin.Skin;
import com.poi.LoadExcel;

public class SkinExcelTest {
	
	@Test
	@Ignore
	public void test2004() {
		LoadExcel le = new LoadExcel();
		File file = new File("skin.xlsx");
		XSSFWorkbook workbook = le.loadXssfExcel(file);
		//row coloum sheetnum
		List<String> li = le.readXssfExcel(workbook, 1, 0, 0);
		List<Skin> products = translist(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getINFO_NO() +" | "+ products.get(i).getBIG_KUBEN_NAME() + " | "
					+products.get(i).getBIG_KUBEN_CD() + " | "+products.get(i).getGOOD_CD() + " | "+products.get(i).getGOOD_NAME()
					+ " | "+products.get(i).getGOOD_DETAILS() + " | "+products.get(i).getGOOD_VOLUME() );
		}
		Connection conn = DBUtil.get2004Conn();
		String sql = "insert into T_NEW_PAGE_INFO(INFO_NO,BIG_KUBEN_CD,BIG_KUBEN_NAME,GOOD_CD,GOOD_NAME,GOOD_DETAILS,GOOD_VOLUME,UPDATE_TIME) "
				+ "values(?,?,?,?,?,?,?,sysdate)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setLong(1, products.get(i).getINFO_NO());
				pstmt.setString(2, products.get(i).getBIG_KUBEN_CD());
				pstmt.setString(3, products.get(i).getBIG_KUBEN_NAME());
				pstmt.setString(4, products.get(i).getGOOD_CD());
				pstmt.setString(5, products.get(i).getGOOD_NAME());
				pstmt.setString(6, products.get(i).getGOOD_DETAILS());
				pstmt.setString(7, products.get(i).getGOOD_VOLUME());
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
	
	@Test
//	@Ignore
	public void test290() {
		LoadExcel le = new LoadExcel();
		File file = new File("skin.xlsx");
		XSSFWorkbook workbook = le.loadXssfExcel(file);
		//row coloum sheetnum
		List<String> li = le.readXssfExcel(workbook, 1, 0, 0);
		List<Skin> products = translist(li);
		System.out.println(products.size());
		for (int i = 0; i < 2; i++) {
			System.out.println(products.get(i).getINFO_NO() +" | "+ products.get(i).getBIG_KUBEN_NAME() + " | "
					+products.get(i).getBIG_KUBEN_CD() + " | "+products.get(i).getGOOD_CD() + " | "+products.get(i).getGOOD_NAME()
					+ " | "+products.get(i).getGOOD_DETAILS() + " | "+products.get(i).getGOOD_VOLUME() );
		}
		Connection conn = DBUtil.get290Conn();
		String sql = "insert into T_NEW_PAGE_INFO(INFO_NO,BIG_KUBEN_CD,BIG_KUBEN_NAME,GOOD_CD,GOOD_NAME,GOOD_DETAILS,GOOD_VOLUME,UPDATE_TIME) "
				+ "values(?,?,?,?,?,?,?,sysdate)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < products.size(); i++) {
				pstmt.setLong(1, products.get(i).getINFO_NO());
				pstmt.setString(2, products.get(i).getBIG_KUBEN_CD());
				pstmt.setString(3, products.get(i).getBIG_KUBEN_NAME());
				pstmt.setString(4, products.get(i).getGOOD_CD());
				pstmt.setString(5, products.get(i).getGOOD_NAME());
				pstmt.setString(6, products.get(i).getGOOD_DETAILS());
				pstmt.setString(7, products.get(i).getGOOD_VOLUME());
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
	
	private List<Skin> translist(List<String> li){
		List<Skin> skins = new ArrayList<Skin>();
		Skin skin = new Skin();
		for (int i = 0; i < li.size(); i++) {
			int count = i%7;
			System.out.println(count + " | "+li.get(i));
			switch(count){
			case 0:
				skin = new Skin();
				skin.setINFO_NO( Long.parseLong(li.get(i)) );
				break;
			case 1:
				skin.setBIG_KUBEN_NAME(li.get(i));
				break;
			case 2:
				skin.setBIG_KUBEN_CD(li.get(i));
				break;
			case 3:
				skin.setGOOD_CD(li.get(i));
				break;
			case 4:
				skin.setGOOD_NAME(li.get(i));
				break;
			case 5:
				skin.setGOOD_DETAILS(li.get(i));
				break;
			case 6:
				skin.setGOOD_VOLUME(li.get(i));
				skins.add(skin);
				break;
			default:
				break;
			}
		}
		return skins;
	}
}
