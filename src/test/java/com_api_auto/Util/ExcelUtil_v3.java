package com_api_auto.Util;

import java.io.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil_v3 {

    /**
     *
     * @param excelPath  传入Excel文件的目录
     * @param sheetIndex 传入sheet，以1开始
     * @return
     */
    public static Object[][] readExcel(String excelPath, int sheetIndex) {
        Object[][] datas = null;

        try {
            InputStream inp = ExcelUtil_v3.class.getResourceAsStream(excelPath);
            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(sheetIndex - 1);

            int lastRowNum = sheet.getLastRowNum();
            datas = new Object[lastRowNum][];
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

    public static void WriteData(String excelPath, int SheetNum, String CaseId, int CellNum, String result){

        try {
            InputStream inp = ExcelUtil_v3.class.getResourceAsStream(excelPath);
            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(SheetNum - 1);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1;i<= lastRowNum;i++){
                Row row = sheet.getRow(i);
                Cell firstCell = row.getCell(0,MissingCellPolicy.RETURN_BLANK_AS_NULL);
                firstCell.setCellType(CellType.STRING);
                String cellValue = firstCell.getStringCellValue();
                if (CaseId.equals(cellValue)){
                    Cell cellToBeWrite = row.getCell(CellNum-1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cellToBeWrite.setCellType(CellType.STRING);
                    cellToBeWrite.setCellValue(result);
                }
            }
            OutputStream OutputStream = new FileOutputStream(new File("target/ApiInfo.xlsx"));
            workbook.write(OutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Object[][] datas = readExcel("/testcase/ApiInfo.xlsx", 1);
        for (Object[] objects : datas) {
            for (Object object : objects) {
                System.out.print(object + ",");

            }
            System.out.println();
        }

        WriteData("/testcase/ApiInfo.xlsx",2,"3",4,"hello world!!!!");
    }

}
