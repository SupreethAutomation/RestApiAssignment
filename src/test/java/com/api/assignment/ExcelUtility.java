package com.api.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility 
{
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	public void createExcelSheet(List<Object> objListMovieNames) throws IOException
	{
		workbook =  new XSSFWorkbook();
		
		sheet = workbook.createSheet("Movie Names");
		
		Path resourceDirectory = Paths.get("src","test","resources","MovieNames.xlsx");
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		System.out.println(absolutePath);
		//File f = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\MovieNames.xlsx");
		 File f = new File(absolutePath);
		
		CellStyle objCellStyle = workbook.createCellStyle();
		XSSFFont objFont = workbook.createFont();
		objFont.setBold(true);
		objCellStyle.setFont(objFont);
		XSSFCell objCell = sheet.createRow(0).createCell(0);
		objCell.setCellStyle(objCellStyle);
		objCell.setCellValue("Movie Names");
		
		for(int i=1; i <= objListMovieNames.size()-1; i++)
		{
			XSSFRow objRow = sheet.createRow(i);
			XSSFCell objCell2 = objRow.createCell(0);
			objCell2.setCellValue((String)objListMovieNames.get(i));
		}
		FileOutputStream objFileOutputStream = new FileOutputStream(f);
		workbook.write(objFileOutputStream);
		objFileOutputStream.close();
		workbook.close();
		System.out.println("Excel sheet is Created...");
	}
	
	public void getPath()
	{
		Path resourceDirectory = Paths.get("src","test","resources","MovieNames.xlsx");
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		System.out.println(absolutePath);
	}
	
	
	public static void main(String[] args) 
	{
		ExcelUtility objExcelUtility = new ExcelUtility();
			objExcelUtility.getPath();
	}
	
	
	
}
 