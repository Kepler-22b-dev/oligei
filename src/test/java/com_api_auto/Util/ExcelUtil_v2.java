package com_api_auto.Util;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/*
 * 调用表格工具类，未去除表头第一行
 */
public class ExcelUtil_v2 {
	
public  static Object[][] readExcel(String excelPath,int sheetIndex){
	//获得输入流getResourceAsStream
	Object[][] datas=null;

		try {
			InputStream inp=ExcelUtil_v2.class.getResourceAsStream(excelPath);
			//一个excel对应一个工作簿
			Workbook workbook=WorkbookFactory.create(inp);//接口  面向接口编程workbook
//		Workbook workbook=WorkbookFactory.create(new File("src/test/resources/testcase/register.xlsx"));
			Sheet sheet=workbook.getSheetAt(sheetIndex-1);
			//获取最后一行的行号
			int lastRowNum=sheet.getLastRowNum();
			//创建一个二维数组
			datas=new Object[lastRowNum+1][];
			
			for(int i=0;i<=lastRowNum;i++){
				Row row=sheet.getRow(i);
				int lastCellNum=row.getLastCellNum();
				
				Object[] cellValueArray=new Object[lastCellNum];
				for(int j=0;j<lastCellNum;j++){
//					Cell cell=row.getCell(j-1);
					Cell cell=row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					String cellValue=cell.getStringCellValue();
//					System.out.print(cellValue+",");
					cellValueArray[j]=cellValue;
				}
//				System.out.println("----------");
				datas[i]=cellValueArray;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return datas;
	}

public static void main(String[] args) {
	Object[][] datas=readExcel("/DataProvides.xlsx", 1);
	for (Object[] objects : datas) {
		for (Object object : objects) {
			System.out.print(object+",");
			
		}
		System.out.println("-----");
	}
}
}
