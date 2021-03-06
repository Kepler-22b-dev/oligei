package com_api_auto.Util;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil_v2 {

    public static Object[][] readExcel(String excelPath, int sheetIndex) {
        Object[][] datas = null;

        try {
            InputStream inp = ExcelUtil_v2.class.getResourceAsStream(excelPath);
            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
            int lastRowNum = sheet.getLastRowNum();
            datas = new Object[lastRowNum + 1][];

            for (int i = 0; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                int lastCellNum = row.getLastCellNum();
                Object[] cellValueArray = new Object[lastCellNum];
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    cellValueArray[j] = cellValue;
                }
                datas[i] = cellValueArray;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return datas;
    }

    public static void main(String[] args) {
        Object[][] datas = readExcel("/DataProvides.xlsx", 1);
        for (Object[] objects : datas) {
            for (Object object : objects) {
                System.out.print(object + ",");

            }
            System.out.println("-----");
        }
    }
}
