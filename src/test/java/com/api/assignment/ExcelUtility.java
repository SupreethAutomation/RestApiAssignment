package com.api.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
		File f = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\MovieNames.xlsx");
		
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
	
	
	
	
	
}
 