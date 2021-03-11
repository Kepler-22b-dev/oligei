package com_api_auto.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com_api_auto.CellData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil_v3 {

    public static List<CellData> dataToWriteList = new ArrayList<CellData>();

    /**
     * @param excelPath  传入Excel文件的目录
     * @param sheetIndex 传入sheet，以1开始
     * @return
     */
    public static Object[][] readExcel(String excelPath, int sheetIndex) {
        Object[][] datas = null;
        InputStream inp = null;
        Workbook workbook = null;
        try {
            inp = ExcelUtil_v3.class.getResourceAsStream(excelPath);
            workbook = WorkbookFactory.create(inp);
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
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inp != null) {
                try {
                    inp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }

    /**
     * 单独写入数据的方法
     *
     * @param excelPath 读取文件的目录
     * @param SheetNum  读取文件的sheet
     * @param CaseId    需要写入的CaseId编号
     * @param CellNum   需要写入的列
     * @param result    需要写入的结果
     */
    @Deprecated
    public static void WriteData(String excelPath, int SheetNum, String CaseId, int CellNum, String result) {
        InputStream inp = null;
        Workbook workbook = null;
        OutputStream outputStream = null;
        try {
            inp = new FileInputStream(new File(excelPath));
            workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(SheetNum - 1);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell firstCell = row.getCell(0, MissingCellPolicy.RETURN_BLANK_AS_NULL);
                firstCell.setCellType(CellType.STRING);
                String cellValue = firstCell.getStringCellValue();
                if (CaseId.equals(cellValue)) {
                    Cell cellToBeWrite = row.getCell(CellNum - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cellToBeWrite.setCellType(CellType.STRING);
                    cellToBeWrite.setCellValue(result);
                }
            }
            outputStream = new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inp != null) {
            try {
                inp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 批量回写数据的方法
     *
     * @param sourceExcelPath 读取文件路径
     * @param targetExcelPath 目标路径
     * @param SheetNum        写入目标文件的sheet页
     */
    public static void batchWriteData(String sourceExcelPath, String targetExcelPath, int SheetNum) {
        InputStream inp = null;
        Workbook workbook = null;
        OutputStream outputStream = null;
        try {
            inp = new FileInputStream(new File(sourceExcelPath));
            workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(SheetNum - 1);
            int lastRowNum = sheet.getLastRowNum();
            for (CellData cellData : dataToWriteList) {
                String CaseId = cellData.getCaseId();
                String DataStr = cellData.getDataStr();
                int CellNum = cellData.getCellNum();
                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    Cell firstCell = row.getCell(0, MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    firstCell.setCellType(CellType.STRING);
                    String cellValue = firstCell.getStringCellValue();
                    if (CaseId.equals(cellValue)) {
                        Cell cellToBeWrite = row.getCell(CellNum - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        cellToBeWrite.setCellType(CellType.STRING);
                        cellToBeWrite.setCellValue(DataStr);
                        break;//匹配上之后就结束当前循环；
                    }
                }
            }

            outputStream = new FileOutputStream(new File(targetExcelPath));
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inp != null) {
            try {
                inp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        Object[][] datas = readExcel("/testcase/ApiInfo.xlsx", 1);
//        for (Object[] objects : datas) {
//            for (Object object : objects) {
//                System.out.print(object + ",");
//
//            }
//            System.out.println();
//    }
//    WriteData("/Users/tanxian/IdeaProjects/oligei/target/test-classes/testcase/ApiInfo.xlsx",2,"3",4,"hello world!!!!!");
}


}
