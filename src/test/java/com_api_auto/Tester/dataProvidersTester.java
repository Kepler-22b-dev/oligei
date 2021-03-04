package com_api_auto.Tester;

import groovyjarjarantlr4.v4.misc.EscapeSequenceParsing;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.sql.SQLOutput;

import static com_api_auto.Tester.ExcelUtil.readExcel;

/**
 * 数据提供者测试类
 */
public class dataProvidersTester {
    @Test(dataProvider = "getdatas")
    public void f(String apiID,String apiname,String methon,String requestData,String expectData) {
        System.out.println(apiID+" "+apiname+" "+methon+" "+requestData+" "+ expectData);
    }


    @DataProvider
    public static Object[][] getdatas() {
        Object[][] datas = readExcel("/DataProvides.xlsx",
                1,
                2,
                8,
                1,
                5);
        for (Object[] data : datas) {
            for (Object datum : data) {
                System.out.print(datum + "  ");
            }
        }
        return datas;
    }
    }
