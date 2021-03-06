package com_api_auto.Util;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil_v3 {

    public static Object[][] readExcel(String excelPath, int sheetIndex) {
        Object[][] datas = null;

        try {
            InputStream inp = ExcelUtil_v3.class.getResourceAsStream(excelPath);
            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(sheetIndex - 1);

            int lastRowNum = sheet.getLastRowNum();
            //长度不+1，是去掉表头第一行
            datas = new Object[lastRowNum][];
            //去除表头第一行，i=1
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                int lastCellNum = row.getLastCellNum();
                Object[] cellValueArray = new Object[lastCellNum];
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    cellValueArray[j] = cellValue;
                }
                datas[i - 1] = cellValueArray;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return datas;
    }

    public static void main(String[] args) {
        Object[][] datas = readExcel("/ApiInfo.xlsx", 1);
        for (Object[] objects : datas) {
            for (Object object : objects) {
                System.out.print(object + ",");

            }
            System.out.println();
        }
    }
}
