package com.create;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class creatFolder {
	
	private Date getMonthEndDate(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		cal.set(year, month, 1, 0, 0, 0);
		Date monthEnd = cal.getTime();
		return monthEnd;
	}
	
	private String getCalendar(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		String currentTime = sdf.format(date);
		System.out.println("currentTime :"+currentTime);
		return currentTime;
	}

	private void createFolder(String folderDir, String folderName, List<String> folderNameList, String readMe) throws IOException{
		StringBuilder path = new StringBuilder();
		path.append(folderDir);
		path.append("\\");
		path.append(folderName);
		File file =new File(path.toString());  
		System.out.println("path :"+path.toString());
		//如果文件夹不存在则创建    
		if(!file .exists()  && !file .isDirectory())      
		{       
		    System.out.println("Parent目录不存在,即将创建");  
		    file .mkdir();
		    System.out.println("Parent 路径:"+file.toString()+"\n 创建完成");
		    System.out.println("----------------------------");
		    buildInnerFolderName(file.toString(), folderNameList);
		    createText(file.toString(), readMe);
		} else   
		{  
		    System.out.println("Parent目录存在");  
		}  
	}
	
	private void createText(String parentPath, String fileName) throws IOException{
		StringBuilder path = new StringBuilder();	
		path.append(parentPath);
		path.append("\\");
		path.append(fileName);
		path.append(".txt");
		File file =new File(path.toString());  
		System.out.println("readMePath :"+path.toString());
		//如果文件夹不存在则创建    
		if(!file .exists()  && !file .isDirectory())      
		{       
		    System.out.println("readMe.txt不存在,即将创建");
		    file.createNewFile();
		    System.out.println("readMe.txt创建完成");
		} else   
		{  
		    System.out.println("readMe.txt存在");  
		} 
	}
	
	private void buildInnerFolderName(String parentPath, List<String> folderNameList){
		for (int i = 0; i < folderNameList.size(); i++) {
			StringBuilder path = new StringBuilder();	
			path.append(parentPath);
			path.append("\\");
			path.append(folderNameList.get(i));
			createInnerFolder(path.toString());
			path = null;
		}
	}
	
	private void createInnerFolder(String path){
		File file =new File(path.toString());  
		System.out.println("InnerPath :"+path.toString());
		//如果文件夹不存在则创建    
		if(!file .exists()  && !file .isDirectory())      
		{       
		    System.out.println("Inner目录不存在,即将创建");
		    file .mkdir();
		    System.out.println("Inner目录创建完成");
		} else   
		{  
		    System.out.println("Inner目录存在");  
		} 
	}
	
	public static void main(String[] args) throws IOException {
		String server_backup = "server_backup";
		String test_server_backup = "test_server_backup";
		String up_load = "up_load";
		List<String> list = new ArrayList<String>();
		list.add(server_backup);
		list.add(test_server_backup);
		list.add(up_load);
		
		String readMe = "readMe";
		
		/*creatFolder cf = new creatFolder();
		String folderName = cf.getCalendar();
		String folderDir = "D:\\dhc_work";
		cf.createFolder(folderDir, folderName, list, readMe);*/
		
		
		creatFolder cf = new creatFolder();
		Date current = Calendar.getInstance().getTime();
		Date monthEnd = cf.getMonthEndDate();
		
		
		Calendar cal = Calendar.getInstance();
		while(current.getTime() < monthEnd.getTime()){
			int day = cal.get(Calendar.DAY_OF_WEEK);
			System.out.println("day is "+day);
			
			if(day == 7 || day == 1){
				System.out.println(cal.get(Calendar.DATE));
				System.out.println("weekend");
			}else{
				System.out.println("weekday");
				System.out.println("date is before");
				String folderName = cf.getCalendar(current);
				String folderDir = "D:\\dhc_work";
				cf.createFolder(folderDir, folderName, list, readMe);
			}
			int date = cal.get(Calendar.DATE);
			cal.set(Calendar.DATE, date+1);
			current = cal.getTime();
			System.out.println("current is "+current);
		}
		
		
		System.out.println(monthEnd);
		
	}
}
