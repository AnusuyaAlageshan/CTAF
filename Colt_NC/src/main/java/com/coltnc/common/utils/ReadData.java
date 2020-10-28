package com.coltnc.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadData {
	@SuppressWarnings("deprecation")
	public static String getcolvalue(String file_name,String sheet_name, String iScript, String iSubScript, String col_name )throws IOException, InterruptedException {
	/*----------------------------------------------------------------------
	Method Name : getcolvalue
	Purpose     : to get the column value of the required column name
	Designer    : 
	Created on  : 
	Input       : file_name,sheet_name, iScript, iSubScript, col_name
	Output      : sVal3
	 ----------------------------------------------------------------------*/	
	String sResult = null;
	
		for (int i = 1; i < 10; i++) {
			try {
				FileInputStream file_stream = new FileInputStream(file_name);
				@SuppressWarnings("resource")
				XSSFWorkbook work_book = new XSSFWorkbook(file_stream);
				XSSFSheet work_sheet = work_book.getSheet(sheet_name);
				//int row_count = work_sheet.getlastCellNum();
				XSSFRow row = work_sheet.getRow(0);
				int last_row = work_sheet.getLastRowNum();
				int last_Col = row.getLastCellNum();
				//XSSFCell cell_val =  work_sheet.getRow(0).getCell(1);
				int isScript = 0,iter = 0,isSubScript = 0,act_row = 0,act_col = 0;
				String sVal3 = "";
				for (iter = 0; iter<=last_Col-1; iter++) {
					if (row.getCell(iter).getStringCellValue().trim().equals("iScript"))
						isScript = iter;
					if (row.getCell(iter).getStringCellValue().trim().equals("iSubScript"))
						isSubScript = iter;
					if (row.getCell(iter).getStringCellValue().trim().equals(col_name))
						act_col = iter;
				}
				
				for (int iRow = 1; iRow <= last_row; iRow++) {
					   row = work_sheet.getRow(iRow);
					   Cell cell = row.getCell(isScript);
					   cell.setCellType(CellType.STRING);
					   String sVal1 = row.getCell(isScript).getStringCellValue();
					   String sVal2 = row.getCell(isSubScript).getStringCellValue();
					   if (sVal1.equalsIgnoreCase(iScript) && sVal2.equalsIgnoreCase(iSubScript))
					   {
						   act_row = iRow;
						   row = work_sheet.getRow(act_row);
						   try {
							   sVal3 = row.getCell(act_col).getStringCellValue();
							   sResult = sVal3;
							   break;
						   } catch (Exception e) {
								return "";
							}
					   }
				   }
				  
			} catch (Exception e) {
				Thread.sleep(2500);
				continue;
			}
		}
		return sResult;
	}
	
	
	public static void setcolvalue(String file_name,String sheet_name, String iScript, String iSubScript, String col_name, String col_value )throws IOException, InterruptedException
	/*----------------------------------------------------------------------
	Method Name : fnsetcolvalue
	Purpose     : to enter the value to required cell
	Designer    : 
	Created on  : 
	Input       : file_name,sheet_name, iScript, iSubScript, col_name, col_value
	Output      : sVal3
	 ----------------------------------------------------------------------*/	
	{
		for (int i=0; i<10; i++) {
			try {
				FileInputStream file_stream = new FileInputStream(file_name);
				@SuppressWarnings("resource")
				XSSFWorkbook work_book = new XSSFWorkbook(file_stream);
				XSSFSheet work_sheet = work_book.getSheet(sheet_name);
				//int row_count = work_sheet.getlastCellNum();
				XSSFRow row = work_sheet.getRow(0);
				int last_row = work_sheet.getLastRowNum();
				int last_Col = row.getLastCellNum();
				//XSSFCell cell_val =  work_sheet.getRow(0).getCell(1);
				int isScript = 0,iter = 0,isSubScript = 0,act_row = 0,act_col = 0;
				for (iter = 0; iter<=last_Col-1; iter++) {
					if (row.getCell(iter).getStringCellValue().trim().equals("iScript"))
						isScript = iter;
					if (row.getCell(iter).getStringCellValue().trim().equals("iSubScript"))
						isSubScript = iter;
					if (row.getCell(iter).getStringCellValue().trim().equals(col_name))
						act_col = iter;
				}
			   for (int iRow = 1; iRow <= last_row; iRow++) {
				   row = work_sheet.getRow(iRow);
				   Cell cell = row.getCell(isScript);
				   cell.setCellType(CellType.STRING);
				   String sVal1 = row.getCell(isScript).getStringCellValue();
				   String sVal2 = row.getCell(isSubScript).getStringCellValue();
				   if (sVal1.equalsIgnoreCase(iScript) && sVal2.equalsIgnoreCase(iSubScript))
				   {
					   act_row = iRow;
					   //System.out.println("actual row value "+act_row);
					   row = work_sheet.getRow(act_row);
					   cell = row.getCell(act_col);
					   cell.setCellType(CellType.STRING);
					   cell.setCellValue(col_value);
					   //row.getCell(act_col).setCellValue(col_value);
					   file_stream.close();
					   FileOutputStream fos = new FileOutputStream(file_name);
					   work_book.write(fos);
					   fos.close();
					   break;
				   }
			   }
			} catch (Exception e) {
				Thread.sleep(2500);
				continue;
			}
		}
	}
	
	public static int fngetrowcount(String file_name,String sheet_name ) throws IOException
	/*----------------------------------------------------------------------
	Method Name : fngetrowcount
	Purpose     : to get used row count of a particular excel sheet
	Designer    : 
	Created on  : 
	Input       : file_name,sheet_name
	Output      : last_row
	 ----------------------------------------------------------------------*/	
	{
		FileInputStream file_stream = new FileInputStream(file_name);
		@SuppressWarnings("resource")
		XSSFWorkbook work_book = new XSSFWorkbook(file_stream);
		XSSFSheet work_sheet = work_book.getSheet(sheet_name);
		int last_row = work_sheet.getLastRowNum();
		return last_row;
	}
	
	public static String fngetScriptval(String file_name,String sheet_name, int iRow, String rSript)throws IOException
	/*----------------------------------------------------------------------
	Method Name : fngetScriptval
	Purpose     : to get column Value of iScript on a current iteration
	Designer    : 
	Created on  : 
	Input       : file_name,sheet_name, iRow, rSript
	Output      : sVal
	 ----------------------------------------------------------------------*/	
	{
		FileInputStream file_stream = new FileInputStream(file_name);
		@SuppressWarnings("resource")
		XSSFWorkbook work_book = new XSSFWorkbook(file_stream);
		XSSFSheet work_sheet = work_book.getSheet(sheet_name);
		XSSFRow row = work_sheet.getRow(0);
		int last_Col = row.getLastCellNum();
		int isScript = 0,iter = 0; String sVal = null;
		for (iter = 0; iter<=last_Col-1; iter++)
		{
			if (row.getCell(iter).getStringCellValue().trim().equals(rSript))
				isScript = iter;
		}
		   row = work_sheet.getRow(iRow);
		   try {
			   sVal = row.getCell(isScript).getStringCellValue();
		   } catch (Exception e) {
			   sVal = "";
		   }
		return sVal;
	}
	
	public static String fngetconfigvalue(String file_name, String col_name)throws IOException, InterruptedException {
	/*----------------------------------------------------------------------
	Method Name : fngetconfigvalue
	Purpose     : to get the credentials of the particular environment
	Designer    : 
	Created on  : 
	Input       : String file_name, String col_name
	Output      : sVal3
	 ----------------------------------------------------------------------*/
	
	String sResult = null;
	
	for (int i = 0; i<10; i++) {
		try {
			FileInputStream file_stream = new FileInputStream(file_name);
			@SuppressWarnings("resource")
			XSSFWorkbook work_book = new XSSFWorkbook(file_stream);
			XSSFSheet work_sheet = work_book.getSheet("Config_Sheet");
			XSSFRow row = work_sheet.getRow(0);
			int last_row = work_sheet.getLastRowNum();
			int last_Col = row.getLastCellNum();
			int isScript = 0,iter = 0,isSubScript = 0,act_row = 0,act_col = 0,env_col = 0;
			String sVal3 = "";
			for (iter = 0; iter<=last_Col-1; iter++) {
				if (row.getCell(iter).getStringCellValue().trim().equals("Pick_Env")) {
					env_col = iter;
				} 
				if (row.getCell(iter).getStringCellValue().trim().equals(col_name)) {
					act_col = iter;
				} 
				
			}
			
			for (int iRow = 1; iRow <= last_row; iRow++) {
				   row = work_sheet.getRow(iRow);
				   Cell cell = row.getCell(isScript);
				   cell.setCellType(CellType.STRING);
				   String sVal1 = row.getCell(env_col).getStringCellValue();
				   if (sVal1.equalsIgnoreCase("Yes")) {
					   act_row = iRow;
					   //System.out.println("actual row value "+act_row);
					   row = work_sheet.getRow(act_row);
					   try {
						   sVal3 = row.getCell(act_col).getStringCellValue().trim();
						   sResult = sVal3;
						   break;
					   } catch (Exception e) {
							return "";
						}
				   }
			   }
			  return sVal3;
		} catch (Exception e) {
			Thread.sleep(2500);
			continue;
		}
	}
		return sResult;
	}
	
	
}
