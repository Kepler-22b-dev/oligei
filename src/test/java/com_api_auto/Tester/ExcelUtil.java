package com_api_auto.Tester;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelUtil {
    public  static Object[][] readExcel(String excelPath,int sheetIndex,int startRow,int endRow,int startCell,int endCell){
        //创建一个二维数组 承载数据的容器
        Object[][] datas=new Object[endRow-startRow+1][endCell-startCell+1];

        try {
            //获得输入流getResourceAsStream
            InputStream inp=ExcelUtil.class.getResourceAsStream(excelPath);
            //一个excel对应一个工作簿
            Workbook workbook=WorkbookFactory.create(inp);//接口  面向接口编程workbook
//  Workbook workbook=WorkbookFactory.create(new File("src/test/resources/testcase/register.xlsx"));
            Sheet sheet=workbook.getSheetAt(sheetIndex-1);

            for(int i=startRow;i<=endRow;i++){
                //之所以减一  是因为索引是从0开始的
                Row row=sheet.getRow(i-1);
                for(int j=startCell;j<=endCell;j++){
//     Cell cell=row.getCell(j-1);
                    //CREATE_NULL_AS_BLANK  遇到空格时，为空策略，否则会报空指针异常
                    Cell cell=row.getCell(j-1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    //将所有列数据设置 枚举为字符串格式
                    cell.setCellType(CellType.STRING);
                    String cellValue=cell.getStringCellValue();
//     System.out.print(cellValue+",");

                    datas[i-startRow][j-startCell]=cellValue;
                }
//    System.out.println("----------");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return datas;

    }

    public static void main(String[] args) {
        Object[][] datas = readExcel("/DataProvides.xlsx",
                1,
                1,
                8,
                2,
                5);
        for (Object[] data : datas) {
            for (Object datum : data) {
                System.out.print(datum+"  ");
            }
            System.out.println();
        }
    }
}
