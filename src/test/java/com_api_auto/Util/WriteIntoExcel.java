package com_api_auto.Util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 将接口执行的结果，写回excel中，作为“实际执行结果”。
 */
public class WriteIntoExcel {



    /**
     * 写入excel文件
     * @param excelURI 测试用例地址
     * @param sheetName sheet页名称
     * @param rownum 行数
     * @param resultMap 写到excel中的结果
     */
        public static void writeExcel(String excelURI, String sheetName,int rownum, Map<Integer,String> resultMap){
            try{
                //获取excel文件信息。
                Workbook workbook = WorkbookFactory.create(new FileInputStream(excelURI));
                //获取到excel内的sheet页面
                Sheet sheet = workbook.getSheet(sheetName);
//                Sheet sheet2 = workbook.getSheetAt(0);//用int获取sheet页
                //定位到行数
                Row row = sheet.getRow(rownum);

                if(row != null){
                    //定位列数，创建单元格并写入内容。
                    for(Integer number : resultMap.keySet()){
                        row.createCell(number).setCellValue(resultMap.get(number));
                    }
                }else{
                    System.out.println("当前行未进行编辑，请先编辑好并保存" );
                }
                FileOutputStream os = new FileOutputStream(excelURI);
                workbook.write(os);//将数据写入excel文档中
                os.close(); //关闭流
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    @Test
    public void  f(){
        HashMap<Integer,String> resultMap = new HashMap<Integer, String>();
        resultMap.put(0,"{'name':'tanxian111'}");
        resultMap.put(2,"{'name':'tanxian'}");
        resultMap.put(1,"test");
        writeExcel("C:\\Users\\30449\\IdeaProjects\\com_api_auto\\target\\12334.xls","tester",10,resultMap);
    }
    }

