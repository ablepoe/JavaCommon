package com.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.entity.Product;

public class LoadExcel {

	public HSSFWorkbook loadExcel(File file){
		if(file == null){
			System.out.println("file is null");
			return null;
		}
		try {
			InputStream is = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			return workbook;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> readExcel(HSSFWorkbook workbook, int start_row, int start_coloum, int sheetNum){
		List<String> li = new ArrayList<String>();
		HSSFSheet sheet = workbook.getSheetAt(sheetNum);
		int total_row_num = sheet.getLastRowNum();
//		System.out.println(total_row_num);
//		System.out.println("--------------------");
		for (int i = start_row; i <= total_row_num; i++) {
			HSSFRow row = sheet.getRow(i);
			for (int j = start_coloum; j < row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);
				String cellValue = getCellValue(cell);
//				System.out.println(cellValue);
				li.add(cellValue);
			}
		}
		return li;
	}
	
	private String getCellValue(HSSFCell cell){
		String cellValue = "";
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			if (HSSFDateUtil.isCellDateFormatted(cell)) {  
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = cell.getDateCellValue();
                return sdf.format(date);
            }else{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
			}
		}
		if(cell.getCellType() == Cell.CELL_TYPE_STRING ){
			cellValue = cell.getStringCellValue();
			if(" ".equals(cell.getStringCellValue())){
				cellValue = "";
			}
		}
		if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
			cellValue = "";
		}
		return cellValue;
	}
	
	public XSSFWorkbook loadXssfExcel(File file){
		if(file == null){
			System.out.println("file is null");
			return null;
		}
		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			return workbook;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> readXssfExcel(XSSFWorkbook workbook, int start_row, int start_coloum, int sheetNum){
		List<String> li = new ArrayList<String>();
		XSSFSheet sheet = workbook.getSheetAt(sheetNum);
		int total_row_num = sheet.getLastRowNum();
//		System.out.println(total_row_num);
//		System.out.println("--------------------");
		for (int i = start_row; i <= total_row_num; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int j = start_coloum; j < row.getLastCellNum(); j++) {
				XSSFCell cell = row.getCell(j);
				String cellValue = getXssfCellValue(cell);
//				System.out.println(cellValue);
				li.add(cellValue);
			}
		}
		return li;
	}
	
	private String getXssfCellValue(XSSFCell cell){
		String cellValue = "";
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			if (HSSFDateUtil.isCellDateFormatted(cell)) {  
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = cell.getDateCellValue();
                return sdf.format(date);
            }else{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
			}
		}
		if(cell.getCellType() == Cell.CELL_TYPE_STRING ){
			cellValue = cell.getStringCellValue();
			if(" ".equals(cell.getStringCellValue())){
				cellValue = "";
			}
		}
		if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
			cellValue = "";
		}
		return cellValue;
	}
	
	public static void main(String[] args) {
		LoadExcel le = new LoadExcel();
		File file = new File("test.xls");
		HSSFWorkbook workbook = le.loadExcel(file);
		List<String> li = le.readExcel(workbook, 2, 1, 1);
	}
	
}
